package gerador.util;
/*
 * Universidade Federal de Campina Grande - UFCG
 * Centro de Engenharia Eletrica e Informatica - CEEI
 * Departamento de Sistemas e Computacao - DSC
 * Projeto Syternas
 */

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * Classe que manipula com a leitura de arquivos.
 * @author Lorena Maia - lorenafm@lcc.ufcg.edu.br 
 * @version 1.2 12 de Setembro de 2006
 */
public class LeitorDeDados implements Cloneable {
	private File arquivo;
	private String nomeArquivo;
	
	private BufferedReader in;
	
	private String linha;

	/**
	 * Construtor da classe.
	 * @param arquivo o arquivo a ser lido
	 * @throws FileNotFoundException caso o arquivo nao seja encontrado
	 */
	public LeitorDeDados(File arquivo) throws FileNotFoundException{
		this.arquivo = arquivo;
		this.nomeArquivo = arquivo.getName();
		criaBuffered();
		linha = "";
	}
	
	/**
	 * Construtor da classe.
	 * @param nomeArquivo o nome do arquivo a ser lido
	 * @throws FileNotFoundException caso o arquivo nao seja encontrado
	 */
	public LeitorDeDados(String nomeArquivo) throws FileNotFoundException{
		this.nomeArquivo = nomeArquivo;
		this.arquivo = new File(nomeArquivo);
		criaBuffered();
		linha = "";
	}
	
	/**
	 * Construtor de um leitor comecando em uma determinada possicao desse arquivo.
	 * @param nomeDoArquivo o nome do arquivo
	 * @param inicio a linha que vc deseja comecar a leitura do arquivo
	 * @throws IOException caso nao seja possivel a leitura do arquivo
	 */
	public LeitorDeDados(String nomeDoArquivo, String inicio) throws IOException{
		this.nomeArquivo = nomeDoArquivo;
		this.arquivo = new File(nomeDoArquivo);
		criaBuffered();
		String aux = lerLinha();
		while (!aux.equals(inicio) && !fimDoArquivo()){
			aux = lerLinha();
		}
		linha = "";	
	}
	
	/**
	 * Gera um clone desse leitor
	 * @param linha a linha de inicio da leitura
	 * @return uma nova instancia de leitor 
	 */
	public Object clone(String linha){
		LeitorDeDados clone = null;
		try{
			clone = (LeitorDeDados) super.clone();
		}catch (CloneNotSupportedException e){
			throw new InternalError();
		}
		try{
			clone = new LeitorDeDados(this.nomeArquivo,linha);			
		}catch (IOException e){
			throw new InternalError();
		}
		return clone;
	}
	
	private void criaBuffered() throws FileNotFoundException{
		in = new BufferedReader(new FileReader(arquivo));	
	}
	
	/**
	 * Ler uma linha do arquivo. 
	 * @return a linha lida.
	 * @throws IOException caso nao tenha sido possivel a leitura da linha
	 */
	public String lerLinha() throws IOException{
		linha = in.readLine();
		return linha;
	}
	
	/**
	 * Verifica o fim do arquivo
	 * @return true, se o arquivo estuver acabado ou false noa caso contrario
	 */
	public boolean fimDoArquivo(){
		return linha == null ? true: false;
	}
	
	/**
	 * Fecha o arquivo de leitura
	 * @throws IOException
	 */
	public void close() throws IOException{
		in.close();
	}
}