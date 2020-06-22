package gerador.util;
/*
 * Universidade Federal de Campina Grande - UFCG
 * Centro de Engenharia Eletrica e Informatica - CEEI
 * Departamento de Sistemas e Computacao - DSC
 * Projeto Syternas
 */

import java.util.ArrayList;
import java.util.List;

/**
 * Classe que cuidara em guardar os parametros de uma linha de comando
 * passado na execucao.
 * @author Lorena Maia - lorenafm@lcc.ufcg.edu.br
 * @version 1.0 19 de Outubro de 2006
 */

/*
 * Historico de modifica��es
 * 
 * 28 de Abril de 2008 - Implementa��o do metodo contains.
 */
public class LinhaDeComando {

	private List<String> parametros;
	
	/**
	 * Construtor da classe
	 * Cria uma lista de parametros vazia.
	 */
	public LinhaDeComando(){
		this.parametros = new ArrayList<String>();
	}
	
	/**
	 * Adiciona um parametro a lista.
	 * @param parametro o parametro a ser adicionado
	 */
	public void addParametro(Object parametro){
		parametros.add(parametro.toString());
	}
	
	/**
	 * @return O numero de parametros da lista
	 */
	public int numerosDeParametros(){
		return parametros.size();
	}
	
	/**
	 * Retorna um parametro no indice dado
	 * @param index o indice do parametro.
	 * @return o parametro no indice
	 */
	public Object getParametro(int index){
		return parametros.get(index);
	}
	
	/**
	 * Retorna true, caso exista um determinado parametro na linha de comando,
	 * ou false, em caso contr�rio.
	 * @param item Objeto procurado na linha de comando.
	 * @return True, se o objeto procurado existe, ou false, em caso contr�rio.
	 */
	public boolean contains(Object item){
		return parametros.contains(item);
	}
	
	/**
	 * Retorna um parametro no indice dado
	 * @param index o indice do parametro.
	 * @return a string do parametro no indice
	 */
	public String getParametroAsString(int index){
		return parametros.get(index).toString();
	}
	
	/**
	 * Retorna uma sub lista da lista de parametros.
	 * @param from comeco da sub lista
	 * @param to o fim da sub lista
	 * @return a sub lista
	 */
	public List<String> subList(int from, int to){
		return parametros.subList(from, to);
	}

	/**
	 * Retorna uma sub lista de parametros como um array de objetos.
	 * @param from comeco da sub lista
	 * @param to o fim da sub lista
	 * @return a sub lista em formato de array de objetos
	 */
	public Object[] subListToArray(int from, int to) {
		return subList(from,to).toArray();
	}
	
	/**
	 * Retorna uma sub lista da lista de parametros.
	 * @param from comeco da sub lista
	 * @return a sub lista comecando de from ate o final desta
	 */
	public List<String> subList(int from){
		return parametros.subList(from, parametros.size());
	}

	/**
	 * Representacao da lista de parametros
	 */
	public String toString(){
		return parametros.toString();
	}
}
