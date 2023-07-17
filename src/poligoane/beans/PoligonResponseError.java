package poligoane.beans;

public class PoligonResponseError extends PoligonResponse {


	private String error;

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	@Override
	public String toString() {
		return "PoligonResponseError [latitude=" + getLatitude() + ", longitude="
				+ getLongitude() + ", zone=" + getZone() + ", hasRestriction=" + isHasRestriction()
				+ ", plant=" + getPlant() + ", error=" + error + "]";
	}



}
