package gerador.util;


/**
 * Um ponto do espa�o, possuindo, para permitir sua identifica��o, longitude, latitude
 * e altitude.
 * @author Lorena F. Maia
 */
public class Ponto {

	private double longitude;
	private double latitude;
	private double altitude;
	/**
	 * Construtor de um Ponto
	 * @param longitude - a longitude do ponto
	 * @param latitude - a latitude do ponto
	 * @param altitude - a altitude do ponto
	 */
	public Ponto(double longitude,double latitude,double altitude){
		this.longitude = longitude;
		this.latitude = latitude;
		this.altitude = altitude;
	}

	/**
	 * Construtor de um Ponto. Considera a altitude igual a zero
	 * @param longitude - a longitude do ponto
	 * @param latitude - a latitude do ponto
	 */
	public Ponto(double longitude,double latitude){
		this.longitude = longitude;
		this.latitude = latitude;
		this.altitude = 0;
	}
	/**
	 * Retorna a altitude do Ponto em quest�o. 
	 * @return a altitude
	 */
	public double getAltitude() {
		return altitude;
	}
	/**
	 * Modifica a altitude do Ponto.
	 * @param altitude - a nova altitude
	 */
	public void setAltitude(double altitude) {
		this.altitude = altitude;
	}
	/**
	 * Retorna a latitude do Ponto em quest�o. 
	 * @return a latitude
	 */
	public double getLatitude() {
		return latitude;
	}
	/**
	 * Modifica a latitude do Ponto
	 * @param latitude - a nova latitude
	 */
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	/**
	 * Retorna a longitude do Ponto em quest�o. 
	 * @return a longitude
	 */
	public double getLongitude() {
		return longitude;
	}
	/**
	 * Modifica a longitude do Ponto
	 * @param longitude - a nova longitude
	 */
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	
	/**
	 * Verifica se dois pontos sao iguais
	 * @param outro o outro ponto, a ser comparado.
	 * @return true, se forem iguais, false no caso contrario.
	 */
	public boolean equals(Object outro){
		if (outro instanceof Ponto) {
			Ponto ponto = (Ponto) outro;
			return ((this.longitude == ponto.getLongitude()) & 
					(this.latitude == ponto.getLatitude()) &
					(this.altitude == ponto.getAltitude())) ? true: false;
		}
		return false;
	}
	
	public int hashCode() {
		int hash = 1;
		hash = hash * 31 + (new Double(longitude + latitude + altitude)).hashCode();
		return hash;
	}
	
	public String toString(){
		return longitude + "-" + latitude;
	}
}