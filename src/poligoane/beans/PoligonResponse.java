package poligoane.beans;

public class PoligonResponse {

	private double latitude;
	private double longitude;
	private String zone;
	private String plant;
	private boolean hasRestriction;
	

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

	public String getZone() {
		return zone;
	}

	public void setZone(String zone) {
		this.zone = zone;
	}

	public boolean isHasRestriction() {
		return hasRestriction;
	}

	public void setHasRestriction(boolean hasRestriction) {
		this.hasRestriction = hasRestriction;
	}

	public String getPlant() {
		return plant;
	}

	public void setPlant(String plant) {
		this.plant = plant;
	}

	@Override
	public String toString() {
		return "PoligonResponse [latitude=" + latitude + ", longitude=" + longitude + ", zone=" + zone + ", plant="
				+ plant + ", hasRestriction=" + hasRestriction + "]";
	}



}
