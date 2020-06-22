package gerador.comandos;
/*
 * Universidade Federal de Campina Grande - UFCG
 * Centro de Engenharia Eletrica e Informatica - CEEI
 * Departamento de Sistemas e Computacao - DSC
 * Projeto Syternas
 */

import gerador.util.DadosHistoricos;
import gerador.util.LinhaDeComando;
import gerador.util.ObtentorDadosPMH;
import gerador.util.PMH;
import gerador.util.Percentil;
import gerador.util.PercentilException;
import gerador.util.Ponto;
import gerador.util.SintaxeException;
import gerador.util.ZipDiretorio;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * Classe que calcula os percentis para os pontos dados no arquivo PMH de entrada.
 * 
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
 * Gera como saida de sua execucao, arquivos, tambem em arquivos pmh, contendo os percentis calculados.
 * @author Lorena Maia - lorenafm@lcc.ufcg.edu.br
 * @version 2.0 26 de Setembro de 2006
 */
public class PercentilProcessador implements Command{
	private int inicio;
	private int limite;
	private int incremento;
	private String prefixo;
	private String extensao;
	private String nomeDiretorio;
	
	/**
	 * Calcula os percentis das precipitacoes do um dia para todos os anos, gerando um arquivo de saida
	 * no formato PMH, onde o ano sera XXXX.
	 * Este sera executado sempre que encontrar a seguinte linha de comando
	 * 
	 * 		percentil <arquivoPMH> <intervaloDePercentis - Ex: 30-60:10> <prefixoEExtensaoDoArquivoDeSaida>
	 * 
	 * @see gerador.comandos.Command#execute(gerador.util.LinhaDeComando)
	 */
	public void execute(LinhaDeComando line) throws SintaxeException, IOException, PercentilException {
		if (line.numerosDeParametros() < 3){
			throw new SintaxeException(" Falta de parametros necessarios. " +
			"Ex: percentil <arquivoPMH> <intervaloDePercentis - Ex: 30-60:10> <prefixoEExtensaoDoArquivoDeSaida>");
		}
		obtemInformacoesDaFaixa(line.getParametroAsString(1));
		criaDiretorio();
		calculaPercentil(line.getParametroAsString(0), line.getParametroAsString(2));		
		new ZipDiretorio().zipFolder(nomeDiretorio, nomeDiretorio + ".zip");
	}

	/**
	 * Extrai as informacaoes com relacao a faixa do percentil. Obtem o inicio, o limite
	 * e o incremento.
	 * @param parametroAsString string contendo a entrada do usuario.
	 * @throws SintaxeException 
	 */
	private void obtemInformacoesDaFaixa(String parametroAsString) throws SintaxeException {
		try {
			String[] faixa = parametroAsString.split("[-]");
			inicio = Integer.parseInt(faixa[0]);
			try{
				String[] i = faixa[1].split(":");
				limite = Integer.parseInt(i[0]);
				incremento = Integer.parseInt(i[1]);			
			}catch (NumberFormatException e){
				limite = Integer.parseInt(faixa[0]);
				incremento = 1;
			}
		} catch (Exception e) {
			throw new SintaxeException("Os intervalos devem ser escritos da seguinte forma: ValorInicial-ValorFinal:Variação. Ex: 30-60:10");
		}
	}

	/**
	 * Separa o prefixo da extensao do nome do arquivo de saida.
	 * @param saida o nome do arquivo de saida.
	 */
	private void separaNomeDoArquivoSaida(String saida){
		String[] nome = saida.split("[.]");
		this.prefixo = nome[0];
		try{
			this.extensao = "."+nome[1];
		}catch (ArrayIndexOutOfBoundsException e){
			this.extensao = ".txt";
		}
	}

	/**
	 * Obtem os dados do dia a calcular o percentil, a partir da Classe ObtentorDeDadosDoPMH,
	 * calcula o percentil e o grava num arquivo referente a saida deste percentil. Ex: se o percentil
	 * for 30, entao a saida serah "prefixo"-per30.txt. 
	 * @param arquivoEntrada arquivo PMH, o qual contem as informacaoes de precipitacao
	 * @param arquivoSaida o nome do arquivo de saida, do qual o prefixo serah acrescido da referencia ao 
	 * percentil
	 * @throws IOException Caso nao seja possivel ler ou escrever no arquivo
	 * @throws PercentilException Caso o calculo nao seja possivel
	 */
	private void calculaPercentil(String arquivoEntrada, String arquivoSaida) throws IOException, PercentilException{
		String separador = System.getProperty("file.separator");
		ObtentorDadosPMH o = new ObtentorDadosPMH(arquivoEntrada);
		separaNomeDoArquivoSaida(arquivoSaida);
		o.extractDataToCalculatePercentil();
		Map<Ponto, DadosHistoricos> map = o.getdata();
		for (Ponto ponto : map.keySet()) {
			DadosHistoricos dados = map.get(ponto);
			for (int month = 1; month <= 12; month++) {
				Map<Integer, List<Double>> dataMonth = dados.getPrecipitationByDay(month);
				for (Integer day : asSortedList(dataMonth.keySet())) {
					List<Double> precipDias = dataMonth.get(day);
					if (precipDias != null){
						for (int j = inicio; j <= limite; j = j+incremento){
							String nomeArquivoSaida = nomeDiretorio+separador+prefixo+"-per"+j+extensao;
							PMH pmh = new PMH(ponto.getLongitude(),ponto.getLatitude(),nomeArquivoSaida,true);
							List<Object> percentis = new ArrayList<Object>();
							percentis.add("XXXX");
							percentis.add(month);
							percentis.add(day);
							percentis.add(calculaPercentilParaUmDia(precipDias,j));
							pmh.gravaLinha(percentis);
							pmh.fechaArquivo();
						}
					}
				}
			}
		}
	}

	public static <T extends Comparable<? super T>> List<T> asSortedList(Collection<T> c) {
	  List<T> list = new ArrayList<T>(c);
	  java.util.Collections.sort(list);
	  return list;
	}


	/**
	 * Cria o diretorio que contera todos os arquivos de saida.
	 */
	private void criaDiretorio(){
		nomeDiretorio = "Percentis";
		File diretorio = new File(nomeDiretorio);
		diretorio.mkdir();
	}
	
	/**
	 * Calcula o percentil para uma lista.
	 * @param precipitacao a lista contendo todos os dados de precipitacao o qual sera calculado
	 * o percentil.
	 * @param percentil o percentil a ser calculado
	 * @return o resultado do  percentil calculado
	 * @throws PercentilException se nao for possivel calcular o percentil
	 */
	private double calculaPercentilParaUmDia(List<Double> precipitacao,double percentil) throws PercentilException {
		Percentil p = new Percentil(precipitacao);
		return p.percentil(percentil);
	}
}