package gerador.comandos;
/*
 * Universidade Federal de Campina Grande - UFCG
 * Centro de Engenharia Eletrica e Informatica - CEEI
 * Departamento de Sistemas e Computacao - DSC
 * Projeto Syternas
 */

import gerador.util.LinhaDeComando;

public class HelpProcessador implements Command {

	/**
	 * Mostra o help com os comandos e seus respectivos parametros.
	 * @see gerador.comandos.Command#execute(gerador.util.LinhaDeComando)
	 */
	public void execute(LinhaDeComando line) throws Exception {
		String enter = System.getProperty("line.separator");
		System.out.print("Gera s�ries sint�ticas de precipita��o."
				+enter+ ""
				+enter+ "Comandos v�lidos e seus respectivos p�rametros:"
				+enter+ ""
				+enter+ "java -jar sinteticas.jar <comando> <p�rametros>"
				+enter+ ""
				+enter+ "  converte <longitude> <latitude> <arquivoChuvas.txt> <nomeArqSaida.extensao>" 
				+enter+ "	A partir do <arquivoChuvas.txt> gera-se um arquivo PMH cuja sa�da - "
				+enter+ "	<nomeArqSaida.extensao> com a extens�o de sua prefer�ncia, de um ponto"
				+enter+ "	dado - <longitude> <latitude> (com ponto, nao virgula);"
				+enter+ ""
				+enter+ "  anexa <arqPMH1.txt> <arqPMH2.txt> ... <nomeArqSaida.extensao> ou "
				+enter+	"  anexa <caminhoDoDiretorioQContemOsArquivos> <nomeArqSaida.extensao>"
				+enter+ "	Anexa os n arquivos PMH recebidos, que podem ser passados um a um ou "
				+enter+ "	o caminho do diret�rio onde est�o os mesmos, num s� arquivo cuja sa�da"
				+enter+ "	<nomeArqSaida> com extens�o de sua prefer�ncia;"				
				+enter+ ""
				+enter+ "  percentil <PMH.txt> <inicio>-<fim>:<incremento> <nomeArqSaida.extensao>"
				+enter+ "	Calcula os percentis dado no intervalo; come�ando do primeiro valor "				
				+enter+ "	informado e variando de acordo com o incremento dado, at� o limite, "		
				+enter+ "  	tamb�m informado no comando; com base no arquivo PMH fornecido."
				+enter+ ""
				+enter+ "  sinteticas <prefixoDosPercentis> <anoInicial> <quantidadeDeAnos> ou "
				+enter+ "  sinteticas <prefixoDaSerieHistorica> <anoInicial> <quantidadeDeAnos>"
				+enter+ "	Calcula as s�ries sint�ticas a partir de arquivos em formato PMH "				
				+enter+ "	contendo os valores dos percentis j� calculados ou a partir de um"				
				+enter+ "	arquivo contendo a s�rie hist�rica a ser estudada. Come�ando do ano"				
				+enter+ "	inicial informado, at� a quantidade final de anos."
				+enter+ ""
				+enter+ "  sinteticas -a <arquivoAnomalia> <arquivoSerieHistorica> <anoInicial>"
				+enter+ "<quantidadeDeAnos>"			
				+enter+ "	Calcula as s�ries sint�ticas a partir de arquivos em formato PMH"				
				+enter+ "	contendo a s�rie historica de precipita��o para a localidade(s) em"		
				+enter+ "	quest�o. Na gera��o da s�rie sint�tica, dados de anomalias para cada" 
				+enter+	"	m�s s�o considerados, os mesmos s�o fornecidos atrav�s de um arquivo" 
				+enter+	"	texto. Este arquivo deve conter apenas uma linha com uma tupla para" 
				+enter+	"	cada m�s mono espa�ados, esta tupla segue o padr�o: "
				+enter+	"	'm�s:anomalia(com ponto,e n�o v�rgula)', por exemplo, 1:-0.7, onde 1"				
				+enter+ "	corresponde ao m�s de Janeiro e -0.7 a anomalia."
				+enter+ "	A gera��o come�a a partir do ano inicial informado e prosegue de acordo"
				+enter+ "	com a quantidade de anos requerida."
				+enter+ ""
				+enter+ "  -help"
				+enter+ "	Ajuda para manipula��o do programa."				
				+enter+ ""
				+enter+ "  -versao"
				+enter+ "	Informa a vers�o do software."			
				+enter);
	}
}
