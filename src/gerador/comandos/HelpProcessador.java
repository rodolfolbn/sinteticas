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
		System.out.print("Gera séries sintéticas de precipitação."
				+enter+ ""
				+enter+ "Comandos válidos e seus respectivos pârametros:"
				+enter+ ""
				+enter+ "java -jar sinteticas.jar <comando> <pârametros>"
				+enter+ ""
				+enter+ "  converte <longitude> <latitude> <arquivoChuvas.txt> <nomeArqSaida.extensao>" 
				+enter+ "	A partir do <arquivoChuvas.txt> gera-se um arquivo PMH cuja saída - "
				+enter+ "	<nomeArqSaida.extensao> com a extensão de sua preferência, de um ponto"
				+enter+ "	dado - <longitude> <latitude> (com ponto, nao virgula);"
				+enter+ ""
				+enter+ "  anexa <arqPMH1.txt> <arqPMH2.txt> ... <nomeArqSaida.extensao> ou "
				+enter+	"  anexa <caminhoDoDiretorioQContemOsArquivos> <nomeArqSaida.extensao>"
				+enter+ "	Anexa os n arquivos PMH recebidos, que podem ser passados um a um ou "
				+enter+ "	o caminho do diretório onde estão os mesmos, num só arquivo cuja saída"
				+enter+ "	<nomeArqSaida> com extensão de sua preferência;"				
				+enter+ ""
				+enter+ "  percentil <PMH.txt> <inicio>-<fim>:<incremento> <nomeArqSaida.extensao>"
				+enter+ "	Calcula os percentis dado no intervalo; começando do primeiro valor "				
				+enter+ "	informado e variando de acordo com o incremento dado, até o limite, "		
				+enter+ "  	também informado no comando; com base no arquivo PMH fornecido."
				+enter+ ""
				+enter+ "  sinteticas <prefixoDosPercentis> <anoInicial> <quantidadeDeAnos> ou "
				+enter+ "  sinteticas <prefixoDaSerieHistorica> <anoInicial> <quantidadeDeAnos>"
				+enter+ "	Calcula as séries sintéticas a partir de arquivos em formato PMH "				
				+enter+ "	contendo os valores dos percentis já calculados ou a partir de um"				
				+enter+ "	arquivo contendo a série histórica a ser estudada. Começando do ano"				
				+enter+ "	inicial informado, até a quantidade final de anos."
				+enter+ ""
				+enter+ "  sinteticas -a <arquivoAnomalia> <arquivoSerieHistorica> <anoInicial>"
				+enter+ "<quantidadeDeAnos>"			
				+enter+ "	Calcula as séries sintéticas a partir de arquivos em formato PMH"				
				+enter+ "	contendo a série historica de precipitação para a localidade(s) em"		
				+enter+ "	questão. Na geração da série sintética, dados de anomalias para cada" 
				+enter+	"	mês são considerados, os mesmos são fornecidos através de um arquivo" 
				+enter+	"	texto. Este arquivo deve conter apenas uma linha com uma tupla para" 
				+enter+	"	cada mês mono espaçados, esta tupla segue o padrão: "
				+enter+	"	'mês:anomalia(com ponto,e não vírgula)', por exemplo, 1:-0.7, onde 1"				
				+enter+ "	corresponde ao mês de Janeiro e -0.7 a anomalia."
				+enter+ "	A geração começa a partir do ano inicial informado e prosegue de acordo"
				+enter+ "	com a quantidade de anos requerida."
				+enter+ ""
				+enter+ "  -help"
				+enter+ "	Ajuda para manipulação do programa."				
				+enter+ ""
				+enter+ "  -versao"
				+enter+ "	Informa a versão do software."			
				+enter);
	}
}
