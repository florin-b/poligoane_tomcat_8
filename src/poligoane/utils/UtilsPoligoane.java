package poligoane.utils;

public class UtilsPoligoane {

	public static String getNumeZona(String numeZona) {

		String zonaNoua = numeZona;

		if (numeZona.toUpperCase().equals("ZM"))
			zonaNoua = "METRO";
		else if (numeZona.toUpperCase().equals("ZMA") || numeZona.toUpperCase().equals("ZEMA"))
			zonaNoua = "EXTRA_A";
		else if (numeZona.toUpperCase().equals("ZMB") || numeZona.toUpperCase().equals("ZEMB"))
			zonaNoua = "EXTRA_B";

		return zonaNoua;
	}
}
