package poligoane.beans;

public class DatePoligon {

	private String filialaPrincipala;
	private String filialaSecundara;
	private String tipZona;
	private String limitareTonaj;
	private String numeZona;

	public DatePoligon() {

	}

	public DatePoligon(String filialaPrincipala, String filialaSecundara, String tipZona, String limitareTonaj,
			String nume) {
		this.filialaPrincipala = filialaPrincipala;
		this.filialaSecundara = filialaSecundara;
		this.tipZona = tipZona;
		this.limitareTonaj = limitareTonaj;
		this.numeZona = nume;
	}

	public String getFilialaPrincipala() {
		return filialaPrincipala;
	}

	public void setFilialaPrincipala(String filialaPrincipala) {
		this.filialaPrincipala = filialaPrincipala;
	}

	public String getFilialaSecundara() {
		return filialaSecundara;
	}

	public void setFilialaSecundara(String filialaSecundara) {
		this.filialaSecundara = filialaSecundara;
	}

	public String getTipZona() {
		return tipZona;
	}

	public void setTipZona(String tipZona) {
		this.tipZona = tipZona;
	}

	public String getLimitareTonaj() {
		return limitareTonaj;
	}

	public void setLimitareTonaj(String limitareTonaj) {
		this.limitareTonaj = limitareTonaj;
	}

	public String getNumeZona() {
		return numeZona;
	}

	public void setNumeZona(String nume) {
		this.numeZona = nume;
	}

	@Override
	public String toString() {
		return "DatePoligon [filialaPrincipala=" + filialaPrincipala + ", filialaSecundara=" + filialaSecundara
				+ ", tipZona=" + tipZona + ", limitareTonaj=" + limitareTonaj + ", numeZona=" + numeZona + "]";
	}

}
