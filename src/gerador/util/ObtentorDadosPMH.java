package gerador.util;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


public class ObtentorDadosPMH {

	private File originalFile;

	private BufferedReader in;

	private Map< Ponto, DadosHistoricos > historicData;

	public ObtentorDadosPMH( String fileName ) {
		historicData = new HashMap<Ponto, DadosHistoricos>();
		originalFile = new File( fileName );
		try {
			in = new BufferedReader(new FileReader(originalFile) );
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}

	}

	public void extractDataFromFile() throws IOException {
		String stringLine = "";
		stringLine = in.readLine();
		while ( stringLine != null ) {
			Linha line = new Linha(stringLine);
			DadosHistoricos dh = historicData.get(line.getPonto());
			if (dh == null) {
				dh = new DadosHistoricos();
				historicData.put(line.getPonto(), dh);
			}
			dh.addingLineInformation(line);
			stringLine = in.readLine();
		}
	}
	
	public void extractDataToCalculatePercentil() throws IOException{
		String stringLine = "";
		stringLine = in.readLine();
		while ( stringLine != null ) {
			Linha line = new Linha(stringLine);
			DadosHistoricos dh = historicData.get(line.getPonto());
			if (dh == null) {
				dh = new DadosHistoricos();
				historicData.put(line.getPonto(), dh);
			}
			dh.addingDayInformation(line);
			stringLine = in.readLine();
		}
	}

	
	public DadosHistoricos getDataFromAPoint(Ponto point) {
		return historicData.get(point);
	}
	
	public Map< Ponto, DadosHistoricos > getdata() {
		return historicData;
	}

}
