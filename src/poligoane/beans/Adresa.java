package poligoane.beans;

public class Adresa {
	private String codJudet;
	private String localitate;

	public String getCodJudet() {
		return codJudet;
	}

	public void setCodJudet(String codJudet) {
		this.codJudet = codJudet;
	}

	public String getLocalitate() {
		return localitate;
	}

	public void setLocalitate(String localitate) {
		this.localitate = localitate;
	}

	@Override
	public String toString() {
		return "Adresa [codJudet=" + codJudet + ", localitate=" + localitate + "]";
	}
	
	

}
