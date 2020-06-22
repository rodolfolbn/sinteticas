package gerador;

/*
 * Universidade Federal de Campina Grande - UFCG
 * Centro de Engenharia Eletrica e Informatica - CEEI
 * Departamento de Sistemas e Computacao - DSC
 * Projeto Systernas
 */

/**
 * Classe responsavel por receber os comandos a partir da linha de comando
 * e executa-los.
 * @author Lorena Maia, lorenafm@lcc.ufcg.edu.br
 * @version 3.1  Data: 16 de Junho de 2007
 */

/*
 * Historico de Modificacoes
 * 
 * 15 de Novembro de 2006 : Cria��o da classe
 * 
 * 16 de Junho de 2007: Acrescimo de infromacoes no -help
 * 
 * 01 de Maio de 2008 : Acrescimo de possibilidade de sub comandos, como por 
 * 						exemplo -a (anomalia).
 */
import gerador.comandos.Command;
import gerador.util.LinhaDeComando;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Gerador {

	/**
	 * Metodo principal para a execu��o dos comandos.
	 * @param args os parametros contendo tanto o comando como os seus parametros. 
	 */
	public static void main(String args[]) {
		Map<String,String> comandoInternos = initComandos();
		if (comandoInternos.containsKey(args[0]))
			executaComando(comandoInternos,args);
		else
			System.err.println("Comando invalido!!Digite -help para ajuda");
	}

	/**
	 * Indentifica o comando e o executa passando a linha de parametros necessarios 
	 * para a execucao. 
	 * @param comandos Um Map de comandos permitidos no programa. 
	 * @param args os parametros para a execucao do comando
	 */
	private static void executaComando(Map<String,String> comandos,String[] args) {
		Class classe = null;
		Command instancia = null;
		try{
			String comando = args[0].toLowerCase();
			if (args.length > 1)
				if (args[1].startsWith("-"))
					comando += " "+args[1].toLowerCase();
			String aux = (String) comandos.get(comando);
			classe = Class.forName(aux);
			instancia = (Command) classe.newInstance();
		}catch (ClassNotFoundException e){
			System.err.println("Comando inexistente.");
			System.exit(0);
		}catch (InstantiationException e1){
			System.err.println("Comando inexistente.");
			System.exit(0);
		}catch (IllegalAccessException e2){
			System.err.println("Comando inexistente.");			
			System.exit(0);
		}

		LinhaDeComando linha = new LinhaDeComando();
		for (int i = 1; i < args.length; i++){
			linha.addParametro(args[i]);
		}
		try{
			instancia.execute(linha);
			if (linha.numerosDeParametros() > 0)
				System.out.println("########## PROCESSO FINALIZADO COM SUCESSO ##########");
		}catch (IOException e1){
			System.err.println("    Problemas na criacao e/ou leitura do(s) arquivo(s).");
		}catch (Exception e){
			e.printStackTrace();
			System.err.println(e.getMessage());
		}
	}

	/**
	 * Metodo responsavel por armazenar num Map todos os comandos possiveis
	 * no programa, de acordo com a sua aintaxe.
	 * @return o map de comandos.
	 */
	private static Map<String,String> initComandos(){
		Map<String,String> comandosInternos = new HashMap<String,String>();
		for (int i = 0; i < SintaxeDoGerador.comandosInternos.length; i++){
			String comando = SintaxeDoGerador.comandosInternos[i][0];
			String classe = SintaxeDoGerador.comandosInternos[i][1];
			comandosInternos.put(comando, classe);
		}
		return comandosInternos;
	}
}