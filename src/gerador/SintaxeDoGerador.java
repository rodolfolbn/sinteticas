package gerador;
/*
 * Universidade Federal de Campina Grande - UFCG
 * Centro de Engenharia Eletrica e Informatica - CEEI
 * Departamento de Sistemas e Computacao - DSC
 * Projeto Systernas
 */

/**
 * Classe que guarda a sintaxe do software.
 * @author Lorena Maia - lorenafm@lcc.ufcg.edu.br
 * @version 1.2 19 de Outubro de 2006 
 */

/*
 * Historico de Modificações
 * 
 * 19 de Outubro de 2006 : Criação da classe
 * 
 * 18 de Junho de 2007 : Adiçao de dois comandos: -help e -versao.
 * 
 * 28 de Abril de 2008 : Adicao do comando sinteticas -a, que calcula a serie
 * 						sintetica considerando anomalias.
 */
public class SintaxeDoGerador {

	/**
	 * Armazena todos os comandos com seus respectivos processadores, ou
	 * seja com as classes que os processa.
	 */
	public static final String[][] comandosInternos = {
		{ "converte", "gerador.comandos.ConverteProcessador" },
		{ "anexa", "gerador.comandos.AnexaProcessador" },
		{ "percentil", "gerador.comandos.PercentilProcessador" },
		{ "sinteticas -a", "gerador.comandos.AnomaliaProcessador" },
		{ "sinteticas", "gerador.comandos.SeriesSinteticasProcessador" },
		{ "-help", "gerador.comandos.HelpProcessador" },
		{ "-versao", "gerador.comandos.VersaoProcessador" },
		};

}
