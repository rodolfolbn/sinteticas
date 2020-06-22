package gerador.util;
/*
 * Universidade Federal de Campina Grande - UFCG
 * Centro de Engenharia Eletrica e Informatica - CEEI
 * Departamento de Sistemas e Computacao - DSC
 * Projeto Syternas
 */

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
/**
 * Classe responsavel pela manipulacao com a gravacao de arquivos.
 * @author Lorena Maia - lorenafm@lcc.ufcg.edu.br
 * @version 1.5 25 de Setembro de 2006
 */
public class GravadorDeDados {
	private File arquivoSaida;

	private BufferedWriter out;
	private PrintWriter gravador;
	
	public static String DIRETORIO_SINTETICAS = "SeriesSinteticas";
	
	/**
	 * Construtor da classe
	 * @param arquivo o arquivo a ser gravado
	 * @param append um boolean indicando se eh para fazer um append no arquivo ou nao
	 * @throws IOException caso nao seja possivel gravr no arquivo
	 */
	public GravadorDeDados(File arquivo, boolean append) throws IOException{
		this.arquivoSaida = arquivo;
		criaBuffered(append);
	}
	
	/**
	 * Construtor da classe
	 * @param nomeArquivo o nome do arquivo a ser gravado
	 * @param append um boolean indicando se eh para fazer um append no arquivo ou nao
	 * @throws IOException caso nao seja possivel gravr no arquivo
	 */
	public GravadorDeDados(String nomeArquivo, boolean append) throws IOException{
		this.arquivoSaida = new File(nomeArquivo);
		criaBuffered(append);
	}
	
	private void criaBuffered(boolean append) throws IOException{
		out = new BufferedWriter(new FileWriter(arquivoSaida,append));	
		gravador = new PrintWriter(out);
	}
	
	/**
	 * Grava uma linha no arquivo.
	 * @param linha a linha a ser gravada
	 */
	public void gravaLinha(String linha){
		gravador.println(linha);
	}
		
	/**
	 * Fecha o arquivo.
	 * @throws IOException caso nao seja possivel fechar o arquivo.
	 */
	public void close() throws IOException{
		out.close();
	}

	/**
	 * Cria o diretorio que contera todos os arquivo de saida
	 * das series sinteticas geradas.
	 */
	public static void criaDiretorioSinteticas(){
		File dir = new File(DIRETORIO_SINTETICAS);
		dir.mkdir();
	}
}