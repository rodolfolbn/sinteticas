package gerador.util;
/*
 * Universidade Federal de Campina Grande - UFCG
 * Centro de Engenharia Eletrica e Informatica - CEEI
 * Departamento de Sistemas e Computacao - DSC
 * Projeto Syternas
 */

/**
 * Excecao lancada no tratamento com percentil
 * @author Lorena Maia - lorenafm@lcc.ufcg.edu.br
 */
public class PercentilException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Construtor passando a mensagem do erro.
	 * @param msg a mensagem.
	 */
	public PercentilException(String msg){
		super(msg);
	}
}
