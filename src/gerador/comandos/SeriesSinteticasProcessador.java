package gerador.comandos;
/*
 * Universidade Federal de Campina Grande - UFCG
 * Centro de Engenharia Eletrica e Informatica - CEEI
 * Departamento de Sistemas e Computacao - DSC
 * Projeto Syternas
 */

import gerador.util.DadosHistoricos;
import gerador.util.GeradorSerieSinteticaDePrecipitacao;
import gerador.util.GravadorDeDados;
import gerador.util.LinhaDeComando;
import gerador.util.ObtentorDadosPMH;
import gerador.util.PMH;
import gerador.util.Ponto;
import gerador.util.SeriesSinteticasException;
import gerador.util.SintaxeException;
import gerador.util.ZipDiretorio;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Classe que gera series sinteticas para varios pontos de um arquivo de PMH. Ela rastreia os
 * arquivos de percentis com o prefixo dado e a partir deles gera series sinteticas para varios anos.
 * @see gerador.util.GeradorSerieSinteticaDePrecipitacao
 * @author Lorena Maia - lorenafm@lcc.ufcg.edu.br
 * @version 3.1 16 de Junho de 2007
 */

/*
 * Historico de modificacoes
 * 
 * 06 de Novembro : Cria�ao da classe.
 * 
 * 16 de Junho de 2007: Fim das alteracoes q permitiram a geracao de series sinteticas a partir de series historicas
 *  					Criacao do metodo temArquivosDeEntrada()
 *  
 * 01 de Maio de 2008 : Pequeno refatoramento ==> Transferencia da responsabilidade
 * 						de criar o diretorio das series sinteticas para o GravadorDeDados.
 * 						Necessidade percebida pelo fato do AnomaliaProcessador tambem
 * 						criar esse diretorio. 
 */
public class SeriesSinteticasProcessador implements Command{

	private int anoInicio;
	private int ultimoAno;
	private String prefixo;
	private String extensao;
	private String nomeSaida = GravadorDeDados.DIRETORIO_SINTETICAS;
	private final String SEPARADOR = System.getProperty("file.separator");
	private final File DIRETORIOPERCENTIS = new File("Percentis");

	/**
	 * Gera series sinteticas a partir de percentis calculados, para isso se eh passado como 
	 * parametro o prefixo dos arquivos de percentil a serem usados para o calculo, assim
	 * como o ano de inicio e a quantidade de anos a serem gerados.
	 * Sera executado qdo a linha de comando for:
	 * 
	 * 		 sintetica <prefixoDosArquivosDePercentil> <anoInicial> <quantDeAnos> ou
	 *		 sintetica <prefixoDaSerieHistorica> <anoInicial> <quantDeAnos> 
	 * As series geradas sao gravadas num arquivo com referencia ao ano e ao percentil utilizado
	 * para a geracao.
	 * @see gerador.comandos.Command#execute(gerador.util.LinhaDeComando)
	 */
	public void execute(LinhaDeComando line) throws SintaxeException, IOException, SeriesSinteticasException {
		if (line.numerosDeParametros() < 3){
			throw new SintaxeException(" Falta de parametros necessarios. " +
			"Ex: sintetica <prefixoDosArquivosDePercentil> <anoInicial> <quantDeAnos>");
		}
		separaNomeDoArquivoSaida(line.getParametroAsString(0));
		anoInicio = Integer.parseInt(line.getParametroAsString(1));
		ultimoAno = Integer.parseInt(line.getParametroAsString(2)) + anoInicio;
		GravadorDeDados.criaDiretorioSinteticas();
		geraSeries();
	}

	/**
	 * Pega todos os arquivos do diretorio Percentis o qual possuem o prefixo dado e com o auxilio
	 * da classe ObtentorDeDadosDoPMH obtem os dados de um mes e calcula a serie para o primeiro ano 
	 * e para todos os anos seguintes, ateh o ano inicial + quantidade de anos.  
	 * @throws IOException se nao for possivel ler ou gravar no arquivo
	 * @throws SeriesSinteticasException se nao exitir nenhum arquivo com o prefixo dado.
	 */
	private void geraSeries() throws IOException, SeriesSinteticasException {
		boolean temArquivo = false;
		if (DIRETORIOPERCENTIS.exists()){
			File[] files = DIRETORIOPERCENTIS.listFiles();
			temArquivo = temArquivosDeEntrada(files,DIRETORIOPERCENTIS.toString());
		}
		if (!temArquivo){
			String pathAtual = ".";
			File diretorioAtual = new File(pathAtual);
			File[] arquivos = diretorioAtual.listFiles();
			temArquivo = temArquivosDeEntrada(arquivos,pathAtual);
			if (!temArquivo)
				throw new SeriesSinteticasException("Nao existe(m) arquivo(s) com este prefixo.");
		}
		new ZipDiretorio().zipFolder(nomeSaida, nomeSaida + ".zip");
	}
	
	private boolean temArquivosDeEntrada(File[] files, String path) throws IOException{
		boolean temArquivo = false;
		for (int j = 0; j < files.length; j++){
			if (files[j].isFile()){
				if (files[j].toString().contains(prefixo)){
					temArquivo = true;
					ObtentorDadosPMH o = new ObtentorDadosPMH(path+ SEPARADOR + files[j].toString());
					organizaNomeDoArquivo(files[j].toString());
					gravaDados(o);
				}				
			}
		}
		return temArquivo;
	}
	
	private void gravaDados(ObtentorDadosPMH a) throws IOException{
		a.extractDataFromFile();
		Map<Ponto, DadosHistoricos> map =  a.getdata();
		for (Ponto ponto : map.keySet()) {
			DadosHistoricos dados = map.get(ponto);
			for (int i = 1; i <= 12; i++) {
				GeradorSerieSinteticaDePrecipitacao g = new GeradorSerieSinteticaDePrecipitacao(
						dados.getDataFromMonth(i));
				g.setMes(i);
				for (int j = anoInicio; j < ultimoAno; j++){
					List<Object> sinteticas = new ArrayList<Object>();
					String nomeArquivoSaida = nomeSaida+SEPARADOR+prefixo+"-ano"+j+extensao;
					g.setAno(j);
					// TODO Prevenir erros do tipo espa�o em branco de uma linha no arquivo, sem ser null
					
					List<Double> sintetica = g.precipitacoesTotaisCorrigidas();
					g.setDiaAnterior(sintetica.get(sintetica.size()-1));
					PMH pmh = new PMH(ponto.getLongitude(),ponto.getLatitude(),nomeArquivoSaida,true);
					sinteticas.add(sintetica.toArray());
					sinteticas.add(j);
					sinteticas.add(i);
					pmh.gravaDados(sinteticas);
					pmh.fechaArquivo();
				}
				
			}
		}		
	}

	/**
	 * Separa o nome do arquivo para que poder encaixar a informacao com
	 * relacao ao ano da serie calculada.
	 * @param arquivo o nome do arquivo.
	 */
	private void organizaNomeDoArquivo(String arquivo){
		if (arquivo.contains("-")){
			String[] aux = arquivo.split("[-]");
			this.extensao = "-"+aux[1];
		} else 
			this.extensao = ".txt";
	}

	/**
	 * Separa o pefixo da extensao do arquivo
	 * @param saida o nome do arquivo
	 */
	private void separaNomeDoArquivoSaida(String saida){
		String[] nome = saida.split("[.]");
		this.prefixo = nome[0];
	}
}