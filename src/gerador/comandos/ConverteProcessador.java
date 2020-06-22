package gerador.comandos;
/*
 * Universidade Federal de Campina Grande - UFCG
 * Centro de Engenharia Eletrica e Informatica - CEEI
 * Departamento de Sistemas e Computacao - DSC
 * Projeto Syternas
 */

import gerador.util.LeitorDeDados;
import gerador.util.LinhaDeComando;
import gerador.util.PMH;
import gerador.util.SintaxeException;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Classe que recebe um arquivo .txt contendo dados de chuvas de uma determinada localidade
 * transformando-o num arquivo PMH. Este arquivo PMH pode ser encontrado na base de dados da
 * Agencia Nacional de Aguas (http://hidroweb.ana.gov.br/).
 * O formato PMH eh da seguinte forma:
 * 
 *            -35.0639             -8.2786    1000 1971-01-01 00:00:00         20.2         25.1
 *            -35.0639             -8.2786    1000 1971-01-02 00:00:00         40.9         25.0
 *            -35.0639             -8.2786    1000 1971-01-03 00:00:00         32.1         24.8
 *            -35.0639             -8.2786    1000 1971-01-04 00:00:00         19.8         24.8
 * 
 * Onde o primeiro dado eh a longitude do ponto, o segundo a latitude, o terceiro a altitude, seguindo
 * a data e hora da coleta da precipitacao do local, logo apos, a precipitacao coletada e por ultimo a
 * temperatura do local.            
 * @author Lorena Maia - lorenafm@lcc.ufcg.edu.br
 * @version 2.3 19 de Outubro de 2006
 */
public class ConverteProcessador implements Command {

	private File arquivo;
	private double latitude;
	private double longitude;
	private String saida;

	/**
	 * Converte um arquivo txt da base de dados da ANA em um arquivo PMH com as precipitacoes dadas 
	 * neste.   
	 * Sera executado sempre que uma linha de comando da forma abaixo for lida.
	 * 
	 * 		converte <longitudeDaRegiao> <latitudeDaRegiao> <arquivoDeChuvasDaANA> <nomeDoArquivoDeSaida>
	 * Cujo arquivo de saida serah em formato PMH.
	 * @see gerador.comandos.Command#execute(gerador.util.LinhaDeComando)
	 */
	public void execute(LinhaDeComando line) throws IOException, SintaxeException {
		if (line.numerosDeParametros() < 4){
			throw new SintaxeException(" Falta de parametros necessarios. " +
					"Ex: converte <longitudeDaRegiao> <latitudeDaRegiao> <arquivoDeChuvasDaANA> <nomeDoArquivoDeSaida>");
		}
		if (parametrosOK(line))
			convertePMH();			
	}

	/**
	 * Metodo responsavel por criar e converter um arquivo para PMH 
	 * Ler o arquivo de chuvas da regiao, linha por linha, identificando os dados
	 * necessarios
	 * @throws IOException 
	 */
	private void convertePMH() throws IOException {
		LeitorDeDados leitor = new LeitorDeDados(arquivo);
		PMH pmh = new PMH(longitude,latitude,saida,false);
		String linha = leitor.lerLinha();
		while (linha != null){
			if ((!linha.equals("")) && (linha.charAt(0) != '/')){
				pmh.gravaDados(organizaDados(linha));
			}
			linha = leitor.lerLinha();
		}
		leitor.close();
		pmh.fechaArquivo();
	}
	
	/**
	 * Verifica se os parametros passados estao corretos.
	 * @param line a linha contendo os comandos.
	 * @return true, se todos os comandos estiverem ok, ou false no caso contrario.
	 * @throws SintaxeException Lancada quando os parametros nao estam de acordo com a sintaxe
	 */
	private boolean parametrosOK(LinhaDeComando line) throws SintaxeException{
		boolean ok = false;
		try{
			longitude = Double.parseDouble(line.getParametroAsString(0));
			latitude = Double.parseDouble(line.getParametroAsString(1));
			arquivo = new File(line.getParametroAsString(2));
			saida = nomeDoArquivo(line.getParametroAsString(3));
			ok = true;
		}catch (NumberFormatException e){
			throw new SintaxeException(" Entrada invalida! Digite -help para ajuda.");
		}
		return ok;
	}
	
	/**
	 * Verifica se o nome de arquivo passado na linha de comando possui ou nao uma extensao.
	 * @param nome o nome do arquivo.
	 * @return o nome do arquivo estruturado com a devida extensao.
	 */
	private String nomeDoArquivo(String nome) {
		String[] split = nome.split("[.]");
		String nomeArquivo = "";
		try{
			nomeArquivo = split[0]+"." + split[1];
		}catch (ArrayIndexOutOfBoundsException e){
			nomeArquivo = split[0]+".txt";
		}
		return nomeArquivo;
	}
	
	/**
	 * Formata precipitacao lida no local. Se nao houver dados de precipitacao para aquele dia
	 * se eh colocado um "-" para indicar que nao se tem informacao a respeito da chuva ocorrida.  
	 * @param precip a precipitaca a ser formatada.
	 * @return a precipitacao formatada.
	 */
	private String formatPrecip(String precip) {
		if (precip.equals(""))
			return "-";
		else if (precip.contains(",")){
			StringTokenizer st = new StringTokenizer(precip,",");
			precip = st.nextToken() + ".";
			while (st.hasMoreTokens()){
				precip += st.nextToken();
			}
			return precip; 
		}
		return precip;
	}

	/**
	 * Obtem dados do arquivo da ANA. Pegando as precipitacoes de um determinado mes 
	 * de um determinado ano.
	 * @param linha a linha do arquivo contendo as informa��es.
	 * @return os dados obtidos - as precipitacoes, o ano e o mes.
	 */
	private List<Object> organizaDados(String linha) {
		List<Object> dadosEncontrados = new ArrayList<Object>();
		String[] dados = linha.split(";");
		String[] precipitacoes = new String[31];
		for (int i = 0; i < 31; i++){
			precipitacoes[i] = formatPrecip(dados[13+i]);
		}
		dadosEncontrados.add(precipitacoes);
		String[] data = dados[2].split("/");		
		int ano = Integer.parseInt(data[2]);
		dadosEncontrados.add(ano);
		int mes = Integer.parseInt(data[1]);
		dadosEncontrados.add(mes);
		return dadosEncontrados;
	}
}