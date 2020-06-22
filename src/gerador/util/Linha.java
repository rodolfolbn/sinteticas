package gerador.util;
import java.util.StringTokenizer;


public class Linha {
	
	private Ponto point;
	private int year;
	private int month;
	private int day;
	private double precipitation;
	
	public Linha(double latitude, double longitude, double altitude, int year, int month, int day,
			double precipitation) {
		super();
		this.point = new Ponto(longitude, latitude, altitude);
		this.year = year;
		this.month = month;
		this.day = day;
		this.precipitation = precipitation;
	}
	
	public Linha(String line) {
		StringTokenizer st = new StringTokenizer(line);
		double longitude = Double.parseDouble(st.nextToken());
		double latitude = Double.parseDouble(st.nextToken());
		double altitude = Double.parseDouble(st.nextToken());
		this.point = new Ponto(longitude, latitude, altitude);
		String data = st.nextToken();
		String[] dataSeparada = data.split("-");
		try{
			year = Integer.parseInt(dataSeparada[0]);
		}catch(NumberFormatException e){
			year = 0;
		}
		month = Integer.parseInt(dataSeparada[1]);
		day = Integer.parseInt(dataSeparada[2]);
		st.nextToken();
		try {
		precipitation = Double.parseDouble(st.nextToken());
		} catch (NumberFormatException e) {
			precipitation = Integer.MIN_VALUE;
		}

	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public int getMonth() {
		return month;
	}

	public void setMonth(int month) {
		this.month = month;
	}

	public int getDay() {
		return day;
	}

	public void setDay(int day) {
		this.day = day;
	}

	public double getPrecipitation() {
		return precipitation;
	}

	public void setPrecipitation(double precipitation) {
		this.precipitation = precipitation;
	}
	
	public Ponto getPonto() {
		return point;
	}

	public boolean isPreviousDay(Linha otherLine) {
		if (getYear() == otherLine.getYear()) {
			if (getMonth() == otherLine.getMonth()) {
				if (getDay() + 1 == otherLine.getDay()){
					return true;
				}
			} else if ( (getMonth() + 1 == otherLine.getMonth()) &&
					(otherLine.getDay() == 1)){
				return true;
			}
		} else {
			if ( (getYear() + 1 == otherLine.getYear()) &&
					(getMonth() - otherLine.getMonth() == 11) &&
					(getDay() - otherLine.getDay() == 30)) {
				return true;
			}
		}
		return false;
	}

	public String toString() {
		return point.getLongitude() + " " + point.getLatitude() + " " + year + "-" + month + "-" + day + " " + precipitation;
	}
}
