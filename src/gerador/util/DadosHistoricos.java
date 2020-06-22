package gerador.util;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class DadosHistoricos {

	private Map< Integer, List<Double> > data;

	private Map<Integer, Map<Integer, List<Double>>> dataDay;

	private Linha previousLine = new Linha(0, 0, 0, 0, 0, 0, 0);

	public static final int DS = 0;

	public static final int DC = 1;

	public static final int DCS = 2;

	public static final int DCC = 3;
	
	public DadosHistoricos() {
		initMap();
	}

	private void initMap(){
		data = new HashMap<Integer, List<Double>>(12);
		dataDay = new HashMap<Integer, Map<Integer,List<Double>>>(12);
		for (int i = 1; i <= 12; i++) {
			//TODO - change this name
			List<Double> aux = new ArrayList<Double>();
			aux.add(0.0);
			aux.add(0.0);
			aux.add(0.0);
			aux.add(0.0);
			data.put(new Integer(i), aux);
			dataDay.put(new Integer(i), new HashMap<Integer, List<Double>>());
		}
	}

	public void addingLineInformation(Linha line) {
		List<Double> dataMonth = data.get(line.getMonth());
		double precipitation = line.getPrecipitation();
		if (precipitation > 0) {
			dataMonth.add(precipitation);
			dataMonth.set(DC, dataMonth.get(DC) + 1);
			if (previousLine.isPreviousDay(line)) {
				if (previousLine.getPrecipitation() > 0){
					dataMonth.set(DCC, dataMonth.get(DCC) + 1);
				} else {
					dataMonth.set(DCS, dataMonth.get(DCS) + 1);
				}
			}
		} else if (precipitation == 0) {
			dataMonth.set(DS, dataMonth.get(DS) + 1);
		}
		previousLine = line;
	}

	public void addingDayInformation(Linha line) {
		Map<Integer, List<Double>> dataMonth = dataDay.get(line.getMonth());
		double precipitation = line.getPrecipitation();
		List<Double> day = dataMonth.get(line.getDay());
		if (day == null) {
			day = new ArrayList<Double>();
			dataMonth.put(line.getDay(), day);
		}
		if (precipitation >= 0) {
			//checamos pois quando nao existe dado de precipitacao (ex: -), o objeto linha
			// seta a precipitacao para Integer.MIN_VALUE o que causa problema nas contas
			// quando contabilizado.
			day.add(precipitation);
		}
	}

	public Map<Integer, List<Double>> getPrecipitationByDay(int month) {
		return dataDay.get(month);
	}

	public List<Double> getDataFromMonth(int month) {
		return (month > 0 && month <= 12) ? data.get(new Integer(month)) : null;
	}
	
	public String toString() {
		return data.toString();
	}
}
