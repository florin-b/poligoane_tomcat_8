package poligoane.beans;

public class CoordonateGps {

	private double latitude;
	private double longitude;
	private String error;

	public CoordonateGps() {

	}

	public CoordonateGps(double latitude, double longitude, String error) {
		super();
		this.latitude = latitude;
		this.longitude = longitude;
		this.error = error;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	@Override
	public String toString() {
		return "CoordonateGps [latitude=" + latitude + ", longitude=" + longitude + ", error=" + error + "]";
	}

}
