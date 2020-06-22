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
import gerador.util.LeitorDeDados;
import gerador.util.LinhaDeComando;
import gerador.util.ObtentorDadosPMH;
import gerador.util.PMH;
import gerador.util.Ponto;
import gerador.util.SeriesSinteticasException;
import gerador.util.SintaxeException;
import gerador.util.ZipDiretorio;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

/**
 * Classe que gera series sinteticas para varios pontos de um arquivo de PMH.
 * Encontra o arquivo contendo a s�rie hist�rica passado e a partir dele 
 * gera series sinteticas para varios anos utilizando o arquivo de anomalias
 * de cada m�s, passado como param�tro.
 * @see gerador.util.GeradorSerieSinteticaDePrecipitacao
 * @author Lorena Maia - lorenafm@lcc.ufcg.edu.br
 * @version 4.0 28 de Abril de 2008
 */

public class AnomaliaProcessador implements Command{
	
	private Map<Integer, Double> anomalias;
	private int anoInicio;
	private int ultimoAno;
	private String prefixoSaida;
	private String extensao = ".txt";
	
	private String diretorio = GravadorDeDados.DIRETORIO_SINTETICAS;
	
	private final String SEPARADOR = System.getProperty("file.separator");

	/**
	 * Gera series sinteticas a partir de percentis calculados, para isso se eh passado como 
	 * parametro o prefixo dos arquivos de percentil a serem usados para o calculo, assim
	 * como o ano de inicio e a quantidade de anos a serem gerados.
	 * Sera executado qdo a linha de comando for:
	 * 
	 * 		 sintetica <prefixoDosArquivosDePercentil> <anoInicial> <quantDeAnos> ou
	 *		 sintetica <prefixoDaSerieHistorica> <anoInicial> <quantDeAnos> ou
	 *		 sintetica -a <arquivoAnomalia> <arquivoSerieHistorica> <anoInicial> <quantDeAnos> 
	 * As series geradas sao gravadas num arquivo com referencia ao ano e ao percentil utilizado
	 * para a geracao.
	 * @see gerador.comandos.Command#execute(gerador.util.LinhaDeComando)
	 */
	public void execute(LinhaDeComando line) throws SintaxeException, IOException,
				SeriesSinteticasException {
		if (line.numerosDeParametros() < 5){
			throw new SintaxeException(" Falta de parametros necessarios. " +
			"Ex: sintetica -a <arquivoAnomalia> <arquivoSerieHistorica> " +
			"<anoInicial> <quantDeAnos>");
		}
		getAnomalias(line.getParametroAsString(1)); 
		getPrefixo(line.getParametroAsString(2));
		anoInicio = Integer.parseInt(line.getParametroAsString(3));
		ultimoAno = Integer.parseInt(line.getParametroAsString(4)) + anoInicio;
		GravadorDeDados.criaDiretorioSinteticas();
		geraSeries(line.getParametroAsString(2));
		new ZipDiretorio().zipFolder(diretorio, diretorio + ".zip");		
	}

	private void geraSeries(String arquivo) throws IOException {
		ObtentorDadosPMH o = new ObtentorDadosPMH(arquivo);
		o.extractDataFromFile();
		Map<Ponto, DadosHistoricos> map =  o.getdata();
		for (Ponto ponto : map.keySet()) {
			DadosHistoricos dados = map.get(ponto);
			for (int i = 1; i <= 12; i++) {
				GeradorSerieSinteticaDePrecipitacao g = new GeradorSerieSinteticaDePrecipitacao(
						dados.getDataFromMonth(i));
				g.setMes(i);
				try{
					g.setAnomalia(anomalias.get(i));				
				} catch (NullPointerException e) {
					g.setAnomalia(0);
				}
				for (int j = anoInicio; j < ultimoAno; j++){
					List<Object> sinteticas = new ArrayList<Object>();
					String nomeArquivoSaida = diretorio+SEPARADOR+prefixoSaida+"-ano"+j+extensao;
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

	private void getPrefixo(String arquivo) {
		String[] nome = arquivo.split("[.]");
		this.prefixoSaida = nome[0];
	}

	private void getAnomalias(String arquivoAnomalias) throws SintaxeException {
		File arquivo = new File(arquivoAnomalias);
		try {
			LeitorDeDados ld = new LeitorDeDados(arquivo);
			StringTokenizer st = new StringTokenizer(ld.lerLinha());
			anomalias = new HashMap<Integer, Double>();
			while(st.hasMoreTokens()){
				String[] dados = st.nextToken().split(":");
				anomalias.put(new Integer(dados[0]), new Double(dados[1]));
			}
			if (anomalias.size() > 12){
				System.out.println("WARNING: O arquivo de anomalias '"+ arquivoAnomalias +
						"' contem mais dados que o necess�rio. � suficiente a " +
						"informa��o dos doze meses.");
			}
		} catch (FileNotFoundException e) {
			throw new SintaxeException("Arquivo com os dados de anomalia ("+ 
					arquivoAnomalias+") n�o encontrado."); 
		} catch (IOException e) {
			throw new SintaxeException("Problemas na leitura do arquivo " + 
					arquivoAnomalias);
		}
	}

}
