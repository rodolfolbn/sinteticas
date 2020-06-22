package gerador.comandos;
/*
 * Universidade Federal de Campina Grande - UFCG
 * Centro de Engenharia Eletrica e Informatica - CEEI
 * Departamento de Sistemas e Computacao - DSC
 * Projeto Syternas
 */
/**
 * Classe que informa a vers�o do software.
 * @author Lorena Maia - lorenafm@lcc.ufcg.edu.br
 * @version 1.0 18 de Junho de 2007
 */
import gerador.util.LinhaDeComando;

public class VersaoProcessador implements Command {
	private String versao = "3.2.3";

	/**
	 * Mostra a versao do software.
	 * @see gerador.comandos.Command#execute(gerador.util.LinhaDeComando)
	 */
	public void execute(LinhaDeComando line) throws Exception {
		System.out.println("Sint�ticas " + versao);
	}

}
