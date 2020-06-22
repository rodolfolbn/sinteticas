package gerador.comandos;
/*
 * Universidade Federal de Campina Grande - UFCG
 * Centro de Engenharia Eletrica e Informatica - CEEI
 * Departamento de Sistemas e Computacao - DSC
 * Projeto Syternas
 */

import gerador.util.LinhaDeComando;

/**
 * Interface que possibilitarah a execucao de varios comandos, os quais a devem
 * implementar. 
 * @author Lorena Maia - lorenafm@lcc.ufcg.edu.br
 * @version 1.0 19 de Outubro de 2006
 */
public interface Command {

	/**
	 * Metodo que executar um comando permitido nesse software.
	 * @param line a linha de comando com seus parametros.
	 * @throws Exception Problemas com a execucao do comando
	 */
	public void execute(LinhaDeComando line) throws Exception;
	
}
