package gerador.util;
/*
 * Universidade Federal de Campina Grande - UFCG
 * Centro de Engenharia Eletrica e Informatica - CEEI
 * Departamento de Sistemas e Computacao - DSC
 * Projeto Syternas
 */

import gerador.comandos.PercentilProcessador;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Um percentil � uma medida da posi��o relativa de uma unidade obsservacional em rela��o 
 * a todas as outras. O p-�simo porcentil tem no m�nimo p% dos valores abaixo daquele ponto 
 * e no m�nimo (100 - p)% dos valores acima.
 * Ex:
 * 		se uma altura de 1,80m � o 90o. percentil de uma turma de estudantes, ent�o 90% da turma 
 * 		tem alturas menores que 1,80m e 10% t�m altura superior a 1,80m.
 * 
 * Os percentis calculados os sao a partir dos dados de precipitacoes do arquivo dado.
 * Pega os dados de chuvas de um dia de todos os anos dados no ponto do PMH, e calcula o percentil
 * para este dia.
 * @author Lorena F. Maia - lorenafm@lcc.ufcg.edu.br
 * @version 2.3 23 de Outubro de 2006
 */
public class Percentil {
	
	private List<Double> lista; 
	
	/**
	 * Construtor da Classe.
	 * @param array um double[] no qual ser� calculado o percentil.
	 */
	public Percentil(double... array){
		this.lista = new ArrayList<Double>();
		for (double d : array) {
			this.lista.add(d);
		}
	}
	/**
	 * Construtor da classe
	 * @param lista uma List no qual ser� calculado o percentil.
	 */
	public Percentil(List<Double> lista){
		this.lista = lista;
	}
	
	/**
	 * Calcula o percentil da lista.
	 * @param percentil o percentil a ser calculado
	 * @return o calculo do percentil
	 * @throws PercentilException se o percentil estiver fora do limite ( 0 <= percentil <= 100 )
	 */
	public double percentil(double percentil) throws PercentilException{
		if ((percentil < 0) || (percentil > 100))
			throw new PercentilException("Percentil fora do intervalo. | 0 <= percentil <= 100 |");
		List<Double> copia = PercentilProcessador.asSortedList(lista);
		percentil /= 100;
		double termo = (copia.size() * percentil);
		int pos = (int) termo;
		pos = (pos - 1 > 0) ? pos -1 : 0;
		double retorno = 0;
		if (ehInteiro(termo)){
			NumberFormat nb = NumberFormat.getInstance(Locale.US);
			nb.setMaximumFractionDigits(2);
			double num1 = (pos > 0) ? copia.get(pos-1) : 0;
			retorno = Double.parseDouble(nb.format((num1 + copia.get(pos))/2));
		}else{
			retorno = copia.get(pos);
		}
		return retorno;
	}
	/**
	 * Verifica se um numero � ou n�o um inteiro.
	 * @param numero o n�mero a ser avaliado.
	 * @return true - se o n�mero for um inteiro, ou false - em caso contr�rio.
	 */
	private boolean ehInteiro(Number numero){
		String numString = numero + "";
		boolean ehInteiro = false;
		if (numString.contains(".")){
			String[] numSeparado = numString.split("[.]");
			if (Double.parseDouble(numSeparado[1]) == 0)
				ehInteiro = true;
		}
		return ehInteiro;
	}
}