package gerador.util;
/*
 * Universidade Federal de Campina Grande - UFCG
 * Centro de Engenharia Eletrica e Informatica - CEEI
 * Departamento de Sistemas e Computacao - DSC
 * Projeto Syternas
 */

/**
 * Excecao lancada nos problemas com a sintaxe do programa
 * @author Lorena Maia - lorenafm@lcc.ufcg.edu.br
 */
public class SintaxeException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Construtor passando a mensagem do erro.
	 * @param msg a mensagem.
	 */
	public SintaxeException(String msg){
		super(msg);
	}
}
