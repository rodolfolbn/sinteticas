package gerador.util;

/*
 * Universidade Federal de Campina Grande - UFCG
 * Centro de Engenharia Eletrica e Informatica - CEEI
 * Departamento de Sistemas e Computacao - DSC
 * Projeto Cisternas Inteligentes
 */

/**
 * Classe responsavel por gerar um arquivo em formato PMH a qual possui informa��es a respeito do ponto de 
 * localizacao, precipitacao, data de analise, hora e temperatura.
 * @author Lorena Maia, lorenafm@lcc.ufcg.edu.br
 * @version 2.0  Data: 05 de Julho de 2006
 */
import java.io.IOException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

public class PMH {
	
	private double latitude;
	private double longitude;
	
	private GravadorDeDados gravador;
	private GregorianCalendar gc;
	
	private int[] quantDias = {31,29,31,30,31,30,31,31,30,31,30,31};
	/**
	 * Construtor da classe
	 * @param longi A longitude da regiao analisada
	 * @param lati A latitude da regiao amnalisada
	 * @param arquivo o arquivo de chuvas da regiao
	 * @param saida o nome do arquivo PMH de saida
	 * @throws IOException 
	 */
	public PMH(double longi, double lati, String saida, boolean append) throws IOException{
		this.longitude = (longi);
		this.latitude = (lati);
		
		gravador = new GravadorDeDados(saida,append);
	}
	
	/**
	 * Obtem e grava no arquivo PMH os dados recebidos
	 * @param uma lista onde o primeiro dado eh um array de precipitacoes, o segundo eh
	 * o ano e o terceiro o mes.
	 * @throws IOException 
	 */
	public void gravaDados(List dados) throws IOException {
		Object[] precipitacoes = (Object[]) dados.get(0);
		int mes = (Integer) dados.get(2);
		String ano = (String) (dados.get(1) + "");
		int limite = 0;
		try{
			int anoInt = Integer.parseInt((String) (dados.get(1) + ""));
			gc = new GregorianCalendar(anoInt,mes-1,1);
			limite = gc.getActualMaximum(Calendar.DAY_OF_MONTH);
		}catch (NumberFormatException e){ // caso o ano esteja no formato XXXX
			gc = new GregorianCalendar(2004,mes,1); // usei 2004 apenas pq ele � bissexto
			limite = gc.getActualMaximum(Calendar.DAY_OF_MONTH);
		}
//		mudaDiasBissexto(ano);
		for (int i = 0; i < limite; i++){
			List informacoes =  new ArrayList();
			informacoes.add(ano);
			informacoes.add(mes);
			informacoes.add(i+1);
			String precipitacao = "";
			try{
				precipitacao = precipitacoes[i].toString();
			}catch(ArrayIndexOutOfBoundsException e){
				precipitacao = "0";
			}
			informacoes.add(formatPrecipitacao(precipitacao));
			gravaLinha(informacoes);
		}
	}
	
	/**
	 * Grava uma linha no arquivo PMH
	 * @param uma lista onde o primeiro dado eh o ano, o segundo eh o mes e o terceiro o dia
	 * e o ultimo a precipitacao.
	 */
	public void gravaLinha(List dados){
		String dia = formatDia((Integer)dados.get(2));
		String mes1 = formatMes((Integer)dados.get(1));
		Object aux = null;
		try{
			aux = (Double) dados.get(3);
			if (aux.equals(0.0))
				aux = "0";
		}catch (ClassCastException e){
			aux = (String) dados.get(3);
			if (aux.equals("0"))
				aux = "0";
		}
		String espaco = " " + defineEspaco(aux.toString(),12);
		String longi = formatDouble(longitude);
		longi = defineEspaco(formatDouble(longitude),19) + longi;
		String lati = " " + formatDouble(latitude);
		lati = defineEspaco(formatDouble(latitude),19) + lati;
		gravador.gravaLinha(longi + lati + "    " + 1000 + " " + dados.get(0) + "-" + mes1 + "-" + dia + 
				 " " + "00:00:00" + espaco + aux + "            " + "-");
	}

	/**
	 * Fecha o arquivo.
	 * @throws IOException caso nao seja possivel fechar o arquivo
	 */
	public void fechaArquivo() throws IOException{
		gravador.close();		
	}
	
	/**
	 * Altera o nuemro de dias do mes de fevereiro, caso o ano seja bissexto.
	 * @param ano o ano em questao
	 */
//	private void mudaDiasBissexto(String ano){
//		if (!ano.equals("XXXX")){
//			int anoInt = Integer.parseInt(ano);
//			if (new GregorianCalendar().isLeapYear(anoInt))
//				quantDias[1] = 29;
//			else
//				quantDias[1] = 28;	
//		}else{
//			quantDias[1] = 29;
//		}
//	}

	/**
	 * Formata o dia do arquivo PMH
	 * @param dia um inteiro relacionado ao dia
	 * @return uma string representando o dia
	 */
	private String formatDia(int dia) {
		if (dia < 10)
			return "0"+dia;
		return dia+"";
	}

	/**
	 * Formata o mes do Arquivo PMH 
	 * @param mes um inteiro relacionado ao mes
	 * @return uma string representando o mes
	 */
	private String formatMes(int mes) {
		if (mes < 10)
			return "0"+mes;
		return mes + "";
	}

	/**
	 * Define espacamento entre uma coluna e outra
	 * @param aux a string
	 * @param num o numero de espacamento exigido
	 * @return o espacamento necessario
	 */
	private String defineEspaco(String aux,int num) {
		String retorno = "";
		int numEspaco = num - aux.length();
		for (int i = 0; i < numEspaco; i++)
			retorno += " ";
		return retorno;
	}

	/**
	 * Formata a precipitcao do arquivo PMH
	 * @param num o numero a ser formatado
	 * @return uma string do numero formatado
	 */
	private String formatPrecipitacao(String num){
		try{
			double precip = Double.parseDouble(num);
			NumberFormat nf = NumberFormat.getInstance(Locale.US);
			nf.setMaximumFractionDigits(1);
			return nf.format(precip);
		}catch (NumberFormatException e){
			return num;
		}
	}

	/**
	 * Formata a longitude ou a latitude de um ponto.
	 * @param num o numero a ser formatado
	 * @return uma string do numero formatado
	 */
	private String formatDouble(double num) {
		NumberFormat nf = NumberFormat.getInstance(Locale.US);
		nf.setMinimumFractionDigits(4);
		return nf.format(num);
	}
}