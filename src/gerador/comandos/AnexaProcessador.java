package gerador.comandos;

/*
 * Universidade Federal de Campina Grande - UFCG
 * Centro de Engenharia Eletrica e Informatica - CEEI
 * Departamento de Sistemas e Computacao - DSC
 * Projeto Syternas
 */

/**
 * Classe responsavel por converter varios arquivos PMH em um unico arquivo PMH que tem
 * informações a respeito do ponto de localizacao, precipitacao, data de analise, hora e temperatura.
 * @author Lorena Maia, lorenafm@lcc.ufcg.edu.br
 * @version 1.3  Data: 19 de outubro de 2006
 */

import gerador.util.GravadorDeDados;
import gerador.util.LeitorDeDados;
import gerador.util.LinhaDeComando;
import gerador.util.SintaxeException;

import java.io.File;
import java.io.IOException;

public class AnexaProcessador implements Command{

	private GravadorDeDados gravador;
	
	private final String SEPARADOR = System.getProperty("file.separator");

	/**
	 * Executa o comando de anexar varios arquivos PMH num unico arquivo, tambem PMH.
	 * Este metodo sera executado sempre que a seguinte linha de comando for colocada:
	 * 
	 * 		anexa <arqPMH1.txt> <arqPMH2.txt> ... <nomeDoArquivoDeSaida.extensao> ou
	 * 		anexa <caminhoDoDiretorioQContemOsArquivos> <nomeArqSaida.extensao>
	 * 
	 * Onde os PMHS serao anexados no arquivo com o nome dado (este pode conter ou nao
	 * a extensao.Se nao tiver serah tratado como .txt).
	 * 
	 * @see gerador.comandos.Command#execute(gerador.util.LinhaDeComando)
	 */
	public void execute(LinhaDeComando line) throws SintaxeException, IOException {
		if (line.numerosDeParametros() == 2){
			indexaDir(line.getParametroAsString(line.numerosDeParametros()-1), line.getParametroAsString(line.numerosDeParametros()-2));
		}else if (line.numerosDeParametros() < 3){
			throw new SintaxeException(" Falta de parametros necessarios. " +
			"Ex: anexa <PMH1> <PMH2> ... <nomeDoArquivoDeSaida>");
		}else {
			indexa(line.getParametroAsString(line.numerosDeParametros()-1),line.subListToArray(0, line.numerosDeParametros()-1));			
		}
	}
	
	/**
	 * Metodo responsavel por converter os varios arquivos num só
	 * @param arquivos o array de arquivos a serem unificados
	 * @param saida o nome do arquivo de saida
	 * @throws IOException - Caso a leitura ou a gravacao nao forem possiveis
	 */
	public void indexa(String saida,Object... arquivos) throws IOException{
		criaArquivoSaida(nomeDoArquivo(saida));
		for (int i = 0; i < arquivos.length;i++){
			LeitorDeDados leitor = new LeitorDeDados(arquivos[i].toString());
			String linha = leitor.lerLinha();
			while (linha != null){
				gravaLinha(linha);
				linha = leitor.lerLinha();
			}
			leitor.close();
		}
		gravador.close();
	}
	
	private void indexaDir(String saida, String diretorio) throws IOException{
		criaArquivoSaida(nomeDoArquivo(saida));
		File diretorioAtual = new File("."+SEPARADOR+diretorio);
		File[] files = diretorioAtual.listFiles();
		for (int j = 0; j < files.length; j++){
			if (files[j].isFile()){
				if (files[j].toString().contains(diretorio)){
					LeitorDeDados leitor = new LeitorDeDados(files[j].toString());
					String linha = leitor.lerLinha();
					while (linha != null){
						gravaLinha(linha);
						linha = leitor.lerLinha();
					}
					leitor.close();				
				}				
			}
		}
		gravador.close();
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
	 * Grava a linha de um arquivo para o arquivo de saida.
	 * @param linha a linha a ser gravada
	 */
	private void gravaLinha(String linha) {
		gravador.gravaLinha(linha);
	}

	/**
	 * Cria arquivo com o nome dado
	 * @param nome o nome do arquivo dado
	 * @throws IOException 
	 */
	private void criaArquivoSaida(String nome) throws IOException {
		gravador = new GravadorDeDados(nome,false);	
	}
}