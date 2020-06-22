package gerador.util;
/*
 * Universidade Federal de Campina Grande - UFCG
 * Centro de Engenharia Eletrica e Informatica - CEEI
 * Departamento de Sistemas e Computacao - DSC
 * Projeto Syternas
 */
/**
 * Lanca erro em problemas com geracao das series sinteticas
 * @author Lorena Maia - lorenafm@lcc.ufcg.edu.br
 */
public class SeriesSinteticasException extends Exception {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Construtor passando a mensagem do erro.
	 * @param msg a mensagem.
	 */
	public SeriesSinteticasException(String msg){
		super(msg);
	}
}
