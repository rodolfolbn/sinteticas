package gerador.util;
/*
 * Universidade Federal de Campina Grande - UFCG
 * Centro de Engenharia Eletrica e Informatica - CEEI
 * Departamento de Sistemas e Computacao - DSC
 * Projeto Syternas
 */

import static java.lang.Math.log;
import static java.lang.Math.pow;
import static java.lang.Math.random;
import static java.lang.Math.sqrt;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
/**
 * Gera as series sinteticas.
 * A serie sintetica eh o resultado da associacao de series de dados reais com numeros aleatorios
 * produzidos por algoritmos computacionais a fim de gerar sequencias de numeros aleatorios que se 
 * assemelham aos dados climaticos reais.
 * Uma outra vantagem das series sinteticas eh a possibilidade de sua utilizacao na previsao do numero, 
 * magnitude e distribuicao dos eventos que poderao ocorrer em um determinado espa�o de tempo futuro. Isso
 * permite sua aplicacao em modelos de predicao, que utilizam, ao inves de dados observados, dados que 
 * representam uma projecao futura do comportamento climatico da localidade de interesse. 
 * As series sao geradas diariamente para todos os meses dos anos simulados.
 * @author Lorena Maia - lorenafm@lcc.ufcg.edu.br
 * @version 3.1 15 de Junho de 2007
 */

/*
 * Historico de Modificacoes
 * 
 * 12 de Novembro de 2006: Ultima modifica��o
 * 
 * 15 de Junho de 2007 : Criacao de novas variaveis para guardar os dados das series historicas
 * 						 Correcao do calculo da serie sintetica
 * 
 * 01 de Maio de 2008 : Adicao dos dados de anomalia na m�dia do m�s 
 */
public class GeradorSerieSinteticaDePrecipitacao {
	private List<Double> dadosParaSinteticas;
	
	private int mes;
	private int ano;
	private double precipDiaAnterior;
	
	private int diasChuvosos;
	private int diasChuvososComAnteriorChuvoso;
	private int diasChuvososComAnteriorSeco;
	private int diasSecos;
		
	private double anomalia = 0;
	
	/**
	 * Construtor do gerador.
	 * @param precipitacaoDiaria um array de double realcionado a precipitacao do mes em questao
	 */
	public GeradorSerieSinteticaDePrecipitacao(double... precipitacaoDiaria){
		this.dadosParaSinteticas = new ArrayList<Double>();
		int tamanho = precipitacaoDiaria.length;
		for (int i = 0; i < tamanho - 4;i++) {
			this.dadosParaSinteticas.add(precipitacaoDiaria[i]);
		}
//		diasSecos = precipitacaoDiaria[tamanho - 4];
//		diasChuvosos = precipitacaoDiaria[tamanho - 3];
//		diasChuvososComAnteriorSeco = precipitacaoDiaria[tamanho - 2];
//		diasChuvososComAnteriorChuvoso = precipitacaoDiaria[tamanho - 1];
	}
	
	/**
	 * @return uma List com as precipitacoes do mes
	 */
	public List<Double> getPrecipitacoes(){
		return dadosParaSinteticas;
	}
	
	/**
	 * Construtor do gerador. 
	 * @param precipitacaoDiaria uma List com os dados de precipitacao
	 */
//	public GeradorSerieSinteticaDePrecipitacao(List precipitacaoDiaria){
//		this.dadosParaSinteticas = precipitacaoDiaria;
//		int tamanho = precipitacaoDiaria.size();
//		precipDiaAnterior = 0;
//		diasSecos = (Integer)precipitacaoDiaria.get(tamanho - 4);
//		diasChuvosos = (Integer)precipitacaoDiaria.get(tamanho - 3);
//		diasChuvososComAnteriorSeco = (Integer)precipitacaoDiaria.get(tamanho - 2);
//		diasChuvososComAnteriorChuvoso = (Integer)precipitacaoDiaria.get(tamanho - 1);
//		dadosParaSinteticas.remove(dadosParaSinteticas.size() - 1);
//		dadosParaSinteticas.remove(dadosParaSinteticas.size() - 1);
//		dadosParaSinteticas.remove(dadosParaSinteticas.size() - 1);
//		dadosParaSinteticas.remove(dadosParaSinteticas.size() - 1);
//	}

	public GeradorSerieSinteticaDePrecipitacao(List<Double> precipitacaoDiaria){
		this.dadosParaSinteticas = precipitacaoDiaria;
		precipDiaAnterior = 0;
		diasSecos = precipitacaoDiaria.get(DadosHistoricos.DS).intValue();
		diasChuvosos = precipitacaoDiaria.get(DadosHistoricos.DC).intValue();
		diasChuvososComAnteriorSeco = precipitacaoDiaria.get(DadosHistoricos.DCS).intValue();
		diasChuvososComAnteriorChuvoso = precipitacaoDiaria.get(DadosHistoricos.DCC).intValue();
		dadosParaSinteticas.remove(0);
		dadosParaSinteticas.remove(0);
		dadosParaSinteticas.remove(0);
		dadosParaSinteticas.remove(0);
	}

	public void setAnomalia(double anomalia){
		this.anomalia = anomalia;
	}
	
	public void setMes(int mes){
		this.mes = mes;
		this.anomalia = 0;
	}
	
	public void setAno(int ano){
		this.ano = ano;
	}
	
	public void setDiaAnterior(double precipitacao){
		this.precipDiaAnterior = precipitacao;
	}
	/**
	 * @return O numero de dias chuvosos do mes
	 */
	public int getNumeroDeDiasChuvosos() {
		return diasChuvosos;
	}
	
	/**
	 * @return O numero de dias secos do mes
	 */
	public int getNumeroDeDiasSecos() {
		return diasSecos;		
	}
	
	/**
	 * @return O numero de dias chuvosos com anterior tambem chuvoso.
	 */
	public int getNumeroDeDiasChuvososComAnteriorChuvoso() {
		return diasChuvososComAnteriorChuvoso;		
	}
	
	/**
	 * @return O numero de dias chuvosos com anterior seco.
	 */
	public int getNumeroDeDiasChuvososComAnteriorSeco() {
		return diasChuvososComAnteriorSeco;		
	}
	
	/**
	 * Calcula a probabilidade de ocorrer chuva com o dia anterior tambem chuvoso 
	 * @return a probabilidade de chover jah que o dia anterior chuveu
	 */
	public double probabilidadeDeChuvaComDiaAnteriorChuvoso(){
		return ((double)getNumeroDeDiasChuvososComAnteriorChuvoso())/getNumeroDeDiasChuvosos();
	}
	
	/**
	 * Calcula a probabilidade de ocorrer chuva com o dia anterior seco
	 * @return a probabilidade de chover com dia anterior seco
	 */
	public double probabilidadeDeChuvaComDiaAnteriorSeco(){
		return ((double)getNumeroDeDiasChuvososComAnteriorSeco())/getNumeroDeDiasSecos();
	}
	
	/**
	 * Calcula a probabilidade de nao ocorrer chuva com o dia anterior chuvoso 
	 * @return a probabilidade de nao chover com dia anterior chuvoso
	 */
	public double probabilidadeDeDiaSecoComDiaAnteriorChuvoso(){
		return 1 - probabilidadeDeChuvaComDiaAnteriorChuvoso();
	}
	
	/**
	 * Calcula a probabilidade de nao ocorrer chuva com o dia anterior seco
	 * @return a probabilidade de nao chiver tendo o dia anterior sem chuva
	 */
	public double probabilidadeDeDiaSecoComDiaAnteriorSeco(){
		return 1 - probabilidadeDeChuvaComDiaAnteriorSeco();
	}
	
	/**
	 * Calcula a media de precipitacao total diaria mensal
	 * @return a media de precipitacao total diaria mensal
	 */
	public double mediaDaPrecipitacaoTotalDiariaMensal(){
		return mediaDaPrecipitacaoTotalDiariaMensal(dadosParaSinteticas);
	}
	
	public double mediaDaPrecipitacaoTotalDiariaMensal(List<Double> precipitacoes){
		double mediaPrecipitacao = 0;
		int diasChuvosos = 0;
		for (double precipitacao : precipitacoes) {
			if (precipitacao > 0)
				diasChuvosos++;
			mediaPrecipitacao += precipitacao;
		}
		mediaPrecipitacao /= (double) diasChuvosos;
		return mediaPrecipitacao;// + anomalia;
	}
	
	public double mediaDaPrecipitacaoDiariaMensalComAnomalia(){
		return mediaDaPrecipitacaoTotalDiariaMensal(dadosParaSinteticas) + anomalia;
	}
	
	/**
	 * Calcula o desvio padrao da precipitacao mensal. Leva em consideracao apenas
	 * os dias chuvosos.
	 * @return o desvio padrao da precipitacao
	 */
	public double desvioPadraoDaPrecipitacao(){
		return desvioPadraoDaPrecipitacao(dadosParaSinteticas);
	}
	
	public double desvioPadraoDaPrecipitacao(List<Double> precipitacoes){
		double desvioPadrao = 0;
		double mediaPrecipitacaoMensal = mediaDaPrecipitacaoTotalDiariaMensal(precipitacoes);
		int diasChuvosos = 0;
		for (double precipitacao : precipitacoes) {
			if (precipitacao > 0) {
				desvioPadrao += pow((precipitacao-mediaPrecipitacaoMensal),2);
				diasChuvosos++;
			}
		}
		desvioPadrao /= ((double) diasChuvosos - 1);
		return sqrt(desvioPadrao);
	}
	
	/**
	 * Calcula o coeficiente de assimetria.
	 * @return o coeficiente de assimetria
	 */
	public double coeficienteDeAssimetriaDaPrecipitacaoDiariaMensal(){
		double coeficiente = 0;
		double mediaPrecipitacao = mediaDaPrecipitacaoTotalDiariaMensal();
		double desvioPadrao = desvioPadraoDaPrecipitacao();
		for (double precipitacao : dadosParaSinteticas) {
			if (precipitacao > 0){
				double auxiliar = (precipitacao - mediaPrecipitacao)/desvioPadrao;
				coeficiente += pow(auxiliar,3);
			}
		}
		double diasChuvosos = (double) getNumeroDeDiasChuvosos();
		double operando = diasChuvosos/((diasChuvosos -1)*(diasChuvosos - 2));
		coeficiente *= operando;
		return coeficiente;
	}

	/**
	 * Calcula a variavel padronizada.
	 * @param numeroAleatorio o numero aleatorio usadao no calculo
	 * @return a variavel padronizada
	 */
	private double variavelAleatoriaPadronizada(double numeroAleatorio){
		double hp = numeroAleatorio;
		if (numeroAleatorio >= 0.5)
			hp = 1 - numeroAleatorio;
		double z = sqrt(log(1/pow(hp,2)));
		double dividendo = z - (2.30753 + 0.27061*z);
		double divisor = 1 + 0.99229*z + 0.04481*pow(z,2);
		return dividendo/divisor;
	}
	
	/**
	 * Calcula a precipitacao total diaria
	 * @param numeroAleatorio o numero aleatorio que ser usado para gerar a variavel padronizada.
	 * @return a precipitacao.
	 */
	public double precipitacaoTotalDiaria(double numeroAleatorio){
		double precipitacao = 0;
		double desvio = desvioPadraoDaPrecipitacao();
		double mediaPrecipitacao = mediaDaPrecipitacaoDiariaMensalComAnomalia();
		double coeficienteAssimetria = coeficienteDeAssimetriaDaPrecipitacaoDiariaMensal();
		double variavelPadronizada = variavelAleatoriaPadronizada(numeroAleatorio);
		double auxiliar = (coeficienteAssimetria/6);
		int sinal = 1; 
		if (numeroAleatorio < 0.5)
			sinal = -1;
		precipitacao = (sinal*variavelPadronizada) - auxiliar;
		precipitacao = pow(auxiliar * precipitacao + 1,3);
		precipitacao -= 1;
		precipitacao *= (2*desvio)/coeficienteAssimetria;
		precipitacao += mediaPrecipitacao;
		return precipitacao;
	}
	
	private double numeroAleatorio(){
		return random();
	}
	
	/**
	 * Calcula as precipitacoes diarias, com base na probabilidade e um numero
	 * aleatorio gerado.
	 * @return as precipitacoes
	 */
	public List<Double> precipitacaoDiaria(){
		List<Double> precipitacoes = new ArrayList<Double>();
		Calendar gc = new GregorianCalendar(ano,mes-1,1);
		for (int i = 0; i < gc.getActualMaximum(Calendar.DAY_OF_MONTH); i++) {
			double precip = 0;
			double probabilidade = 0;
			if (precipDiaAnterior == 0)
				probabilidade = probabilidadeDeChuvaComDiaAnteriorSeco();
			else
				probabilidade = probabilidadeDeChuvaComDiaAnteriorChuvoso();				
			double num = numeroAleatorio();
			if (num <= probabilidade)
				precip = precipitacaoTotalDiaria(numeroAleatorio());
			precipitacoes.add(precip);
			precipDiaAnterior = precip;
		}
		return precipitacoes;
	}

	/**
	 * Usado apenas para testes
	 * @param numeroAleatorio1
	 * @param numeroAleatorio2
	 * @return
	 */
	public double precipitacaoDiaria(double numeroAleatorio1, double numeroAleatorio2){
		double precip = 0;
		double probabilidade = 0;
		if (precipDiaAnterior == 0)
			probabilidade = probabilidadeDeChuvaComDiaAnteriorSeco();
		else
			probabilidade = probabilidadeDeChuvaComDiaAnteriorChuvoso();				
		if (numeroAleatorio1 <= probabilidade)
			precip = precipitacaoTotalDiaria(numeroAleatorio2);
		precipDiaAnterior = precip;
		return precip;
	}

	/**
	 * Corrige as precipita��es baseado na media total diaria mensal.
	 * @return as precipitacoes corrigidas.
	 */
	public List<Double> precipitacoesCorrigidas(){
		List<Double> precipitacoes = precipitacaoDiaria();
		double mediaTotal = mediaDaPrecipitacaoDiariaMensalComAnomalia();
		double mediaMensal = mediaDaPrecipitacaoTotalDiariaMensal(precipitacoes);
		if (mediaMensal > 0){
			for (double precip : precipitacoes) {
				if (precip > 0.2)
					precip = ((mediaTotal/mediaMensal)*precip);
			}
		}
		return precipitacoes;
	}

	/**
	 * Usada apenas para testes
	 * @param precipitacoes
	 * @return
	 */
	public List<Double> precipitacoesCorrigidas(List<Double> precipitacoes){
		List<Double> result = new ArrayList<Double>();
		double mediaTotal = mediaDaPrecipitacaoDiariaMensalComAnomalia();
		double mediaMensal = mediaDaPrecipitacaoTotalDiariaMensal(precipitacoes);
		if (mediaMensal > 0){
			for (double precip : precipitacoes) {
				if (precip >= 0.2)
					precip = ((mediaTotal/mediaMensal)*precip);
				result.add(precip);
			}
		}
		return result;
	}

	/**
	 * Gera a serie sintetica, corrigindo as precipitacoes com base no desvio
	 * padrao das precipitacoes. 
	 * @return as precipitacoes corrigidas
	 */
	public List<Double> precipitacoesTotaisCorrigidas(){
		List<Double> precipitacoes = precipitacoesCorrigidas();
		double mediaTotal = mediaDaPrecipitacaoDiariaMensalComAnomalia();
		double desvioTotal = desvioPadraoDaPrecipitacao();
		double desvioCorrigido = desvioPadraoDaPrecipitacao(precipitacoes);
		for (double precip : precipitacoes) {
			if (precip > 0)
				precip = (precip - mediaTotal)*(desvioTotal/desvioCorrigido) + mediaTotal;
		}
		return precipitacoes;
	}

	/**
	 * Usada apenas para testes
	 * @param precipitacoes
	 * @return
	 */
	public List<Double> precipitacoesTotaisCorrigidas(List<Double> precipitacoes) {
		List<Double> result = new ArrayList<Double>();
		double mediaTotal = mediaDaPrecipitacaoDiariaMensalComAnomalia();
		double desvioTotal = desvioPadraoDaPrecipitacao();
		double desvioCorrigido = desvioPadraoDaPrecipitacao(precipitacoes);
		for (double precip : precipitacoes) {
			if (precip > 0)
				precip = (precip - mediaTotal)*(desvioTotal/desvioCorrigido) + mediaTotal;
			result.add(precip);
		}
		return result;
	}
}