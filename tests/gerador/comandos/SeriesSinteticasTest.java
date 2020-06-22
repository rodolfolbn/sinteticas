package gerador.comandos;

import gerador.util.DadosHistoricos;
import gerador.util.GeradorSerieSinteticaDePrecipitacao;
import gerador.util.ObtentorDadosPMH;
import gerador.util.Ponto;

import java.io.File;
import java.io.IOException;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import junit.framework.TestCase;

public class SeriesSinteticasTest extends TestCase {
	
	String fileSeparator = System.getProperty("file.separator");
	Ponto ponto;
	ObtentorDadosPMH obtentor;
	DadosHistoricos dados;
	GeradorSerieSinteticaDePrecipitacao gsp;
	
	public void setUp(){
		ponto = new Ponto(-7.4300, -36.3100, 1000);
//		System.out.println((new File(".")).getAbsolutePath());
		obtentor = new ObtentorDadosPMH("filesForTest" + fileSeparator + "4" 
				+ fileSeparator + "Abr_PMH.txt");
		try {
			obtentor.extractDataFromFile();
		} catch (IOException e) {
			fail(e.getMessage());
		}
		dados = obtentor.getDataFromAPoint(ponto);
		gsp = new GeradorSerieSinteticaDePrecipitacao(dados.getDataFromMonth(4));
	}
	
	private String formatDouble(double num, int digits){
		NumberFormat nf = NumberFormat.getInstance(Locale.US);
		nf.setMaximumFractionDigits(digits);
		nf.setRoundingMode(RoundingMode.HALF_EVEN);
		return nf.format(num);
	}
	
	public void testExtractData() {
		assertEquals( 12, gsp.getNumeroDeDiasChuvosos());
		assertEquals( 1, gsp.getNumeroDeDiasChuvososComAnteriorChuvoso());
		assertEquals( 11, gsp.getNumeroDeDiasChuvososComAnteriorSeco());
		assertEquals( 138, gsp.getNumeroDeDiasSecos());
		assertEquals( "6.43", formatDouble(gsp.desvioPadraoDaPrecipitacao(), 2));
		assertEquals( "11.1", formatDouble(gsp.mediaDaPrecipitacaoDiariaMensalComAnomalia(), 2));
		assertEquals( "0.083", formatDouble(gsp.probabilidadeDeChuvaComDiaAnteriorChuvoso(), 3));
		assertEquals( "0.08", formatDouble(gsp.probabilidadeDeChuvaComDiaAnteriorSeco(), 2));
		assertEquals( "0.917", formatDouble(gsp.probabilidadeDeDiaSecoComDiaAnteriorChuvoso(), 3));
		assertEquals( "0.92", formatDouble(gsp.probabilidadeDeDiaSecoComDiaAnteriorSeco(), 2));
		assertEquals("-0.065", formatDouble(gsp.coeficienteDeAssimetriaDaPrecipitacaoDiariaMensal(), 3));
	}
	
	public void testGeracaoSeriesSinteticas() {
		List<Double> precipitacoes = new ArrayList<Double>();
		precipitacoes.add(gsp.precipitacaoDiaria(0.93, 0.62));
		precipitacoes.add(gsp.precipitacaoDiaria(0.47, 0.66));
		precipitacoes.add(gsp.precipitacaoDiaria(0.27, 0.74));
		precipitacoes.add(gsp.precipitacaoDiaria(0.0157620594415535, 0.844662856581252));
		precipitacoes.add(gsp.precipitacaoDiaria(0.49, 0.39));
		precipitacoes.add(gsp.precipitacaoDiaria(0.48, 0.04));
		precipitacoes.add(gsp.precipitacaoDiaria(0.80, 0.98));
		precipitacoes.add(gsp.precipitacaoDiaria(0.63, 0.54));
		precipitacoes.add(gsp.precipitacaoDiaria(0.50, 0.67));
		precipitacoes.add(gsp.precipitacaoDiaria(0.85, 0.80));
		precipitacoes.add(gsp.precipitacaoDiaria(0.91, 0.60));
		precipitacoes.add(gsp.precipitacaoDiaria(0.0341490492949392, 0.457736840583259));
		precipitacoes.add(gsp.precipitacaoDiaria(0.46, 0.91));
		precipitacoes.add(gsp.precipitacaoDiaria(0.61, 0.74));
		precipitacoes.add(gsp.precipitacaoDiaria(0.90, 0.14));
		precipitacoes.add(gsp.precipitacaoDiaria(0.0490936449743815, 0.112151772009221));
		precipitacoes.add(gsp.precipitacaoDiaria(0.76, 0.89));
		precipitacoes.add(gsp.precipitacaoDiaria(0.98, 0.33));
		precipitacoes.add(gsp.precipitacaoDiaria(0.36, 0.94));
		precipitacoes.add(gsp.precipitacaoDiaria(0.95, 0.35));
		precipitacoes.add(gsp.precipitacaoDiaria(0.034042351590716, 0.769867715642783));
		precipitacoes.add(gsp.precipitacaoDiaria(0.89, 0.70));
		precipitacoes.add(gsp.precipitacaoDiaria(0.91, 0.61));
		precipitacoes.add(gsp.precipitacaoDiaria(0.87, 0.29));
		precipitacoes.add(gsp.precipitacaoDiaria(0.87, 0.11));
		precipitacoes.add(gsp.precipitacaoDiaria(0.0159868328124215, 0.414935014797443));
		precipitacoes.add(gsp.precipitacaoDiaria(0.92, 0.35));
		precipitacoes.add(gsp.precipitacaoDiaria(0.16, 0.50));
		precipitacoes.add(gsp.precipitacaoDiaria(0.19, 0.82));
		precipitacoes.add(gsp.precipitacaoDiaria(0.19, 0.08));
		precipitacoes.add(gsp.precipitacaoDiaria(0.98, 0.15));
		
		assertEquals("9.29", formatDouble(precipitacoes.get(3), 2));
		assertEquals("15.03", formatDouble(precipitacoes.get(11), 2));
		assertEquals("12.7", formatDouble(precipitacoes.get(15), 2));
		assertEquals("8.76", formatDouble(precipitacoes.get(20), 2));
		assertEquals("14.74", formatDouble(precipitacoes.get(25), 2));		
		assertEquals("12.1", formatDouble(gsp.mediaDaPrecipitacaoTotalDiariaMensal(precipitacoes), 2));

		precipitacoes = gsp.precipitacoesCorrigidas(precipitacoes);
		assertEquals("8.52", formatDouble(precipitacoes.get(3), 2));
		assertEquals("13.78", formatDouble(precipitacoes.get(11), 2));
		assertEquals("11.65", formatDouble(precipitacoes.get(15), 2));
		assertEquals("8.04", formatDouble(precipitacoes.get(20), 2));
		assertEquals("13.52", formatDouble(precipitacoes.get(25), 2));
		assertEquals("11.1", formatDouble(gsp.mediaDaPrecipitacaoTotalDiariaMensal(precipitacoes), 2));
		assertEquals("2.71", formatDouble(gsp.desvioPadraoDaPrecipitacao(precipitacoes), 2));

		precipitacoes = gsp.precipitacoesTotaisCorrigidas(precipitacoes);
		assertEquals("4.98", formatDouble(precipitacoes.get(3), 2));
		assertEquals("17.45", formatDouble(precipitacoes.get(11), 2));
		assertEquals("12.4", formatDouble(precipitacoes.get(15), 2));
		assertEquals("3.84", formatDouble(precipitacoes.get(20), 2));
		assertEquals("16.84", formatDouble(precipitacoes.get(25), 2));
	}
	
	public void testSerieComAnimalia(){
		List<Double> precipitacoes = new ArrayList<Double>();
		gsp.setAnomalia(-0.255);
		assertEquals( "6.43", formatDouble(gsp.desvioPadraoDaPrecipitacao(), 2));
		assertEquals( "10.845", formatDouble(gsp.mediaDaPrecipitacaoDiariaMensalComAnomalia(), 3));
		assertEquals( "0.083", formatDouble(gsp.probabilidadeDeChuvaComDiaAnteriorChuvoso(), 3));
		assertEquals( "0.08", formatDouble(gsp.probabilidadeDeChuvaComDiaAnteriorSeco(), 2));
		assertEquals( "0.917", formatDouble(gsp.probabilidadeDeDiaSecoComDiaAnteriorChuvoso(), 3));
		assertEquals( "0.92", formatDouble(gsp.probabilidadeDeDiaSecoComDiaAnteriorSeco(), 2));
		assertEquals("-0.065", formatDouble(gsp.coeficienteDeAssimetriaDaPrecipitacaoDiariaMensal(), 3));
		
		precipitacoes.add(gsp.precipitacaoDiaria(0.93, 0.62));
		precipitacoes.add(gsp.precipitacaoDiaria(0.47, 0.66));
		precipitacoes.add(gsp.precipitacaoDiaria(0.27, 0.74));
		precipitacoes.add(gsp.precipitacaoDiaria(0.0157620594415535, 0.844662856581252));
		precipitacoes.add(gsp.precipitacaoDiaria(0.49, 0.39));
		precipitacoes.add(gsp.precipitacaoDiaria(0.48, 0.04));
		precipitacoes.add(gsp.precipitacaoDiaria(0.80, 0.98));
		precipitacoes.add(gsp.precipitacaoDiaria(0.63, 0.54));
		precipitacoes.add(gsp.precipitacaoDiaria(0.50, 0.67));
		precipitacoes.add(gsp.precipitacaoDiaria(0.85, 0.80));
		precipitacoes.add(gsp.precipitacaoDiaria(0.91, 0.60));
		precipitacoes.add(gsp.precipitacaoDiaria(0.0341490492949392, 0.457736840583259));
		precipitacoes.add(gsp.precipitacaoDiaria(0.46, 0.91));
		precipitacoes.add(gsp.precipitacaoDiaria(0.61, 0.74));
		precipitacoes.add(gsp.precipitacaoDiaria(0.90, 0.14));
		precipitacoes.add(gsp.precipitacaoDiaria(0.0490936449743815, 0.112151772009221));
		precipitacoes.add(gsp.precipitacaoDiaria(0.76, 0.89));
		precipitacoes.add(gsp.precipitacaoDiaria(0.98, 0.33));
		precipitacoes.add(gsp.precipitacaoDiaria(0.36, 0.94));
		precipitacoes.add(gsp.precipitacaoDiaria(0.95, 0.35));
		precipitacoes.add(gsp.precipitacaoDiaria(0.034042351590716, 0.769867715642783));
		precipitacoes.add(gsp.precipitacaoDiaria(0.89, 0.70));
		precipitacoes.add(gsp.precipitacaoDiaria(0.91, 0.61));
		precipitacoes.add(gsp.precipitacaoDiaria(0.87, 0.29));
		precipitacoes.add(gsp.precipitacaoDiaria(0.87, 0.11));
		precipitacoes.add(gsp.precipitacaoDiaria(0.0159868328124215, 0.414935014797443));
		precipitacoes.add(gsp.precipitacaoDiaria(0.92, 0.35));
		precipitacoes.add(gsp.precipitacaoDiaria(0.16, 0.50));
		precipitacoes.add(gsp.precipitacaoDiaria(0.19, 0.82));
		precipitacoes.add(gsp.precipitacaoDiaria(0.19, 0.08));
		precipitacoes.add(gsp.precipitacaoDiaria(0.98, 0.15));
		
		assertEquals("9.03", formatDouble(precipitacoes.get(3), 2));
		assertEquals("14.77", formatDouble(precipitacoes.get(11), 2));
		assertEquals("12.45", formatDouble(precipitacoes.get(15), 2));
		assertEquals("8.51", formatDouble(precipitacoes.get(20), 2));
		assertEquals("14.49", formatDouble(precipitacoes.get(25), 2));		
		assertEquals("11.85", formatDouble(gsp.mediaDaPrecipitacaoTotalDiariaMensal(precipitacoes), 2));

		precipitacoes = gsp.precipitacoesCorrigidas(precipitacoes);
		assertEquals("8.27", formatDouble(precipitacoes.get(3), 2));
		assertEquals("13.52", formatDouble(precipitacoes.get(11), 2));
		assertEquals("11.39", formatDouble(precipitacoes.get(15), 2));
		assertEquals("7.79", formatDouble(precipitacoes.get(20), 2));
		assertEquals("13.26", formatDouble(precipitacoes.get(25), 2));
		assertEquals("10.845", formatDouble(gsp.mediaDaPrecipitacaoTotalDiariaMensal(precipitacoes), 3));
		assertEquals("2.71", formatDouble(gsp.desvioPadraoDaPrecipitacao(precipitacoes), 2));

		precipitacoes = gsp.precipitacoesTotaisCorrigidas(precipitacoes);
		assertEquals("4.72", formatDouble(precipitacoes.get(3), 2));
		assertEquals("17.2", formatDouble(precipitacoes.get(11), 2));
		assertEquals("12.14", formatDouble(precipitacoes.get(15), 2));
		assertEquals("3.58", formatDouble(precipitacoes.get(20), 2));
		assertEquals("16.58", formatDouble(precipitacoes.get(25), 2));
	}

}
