package poligoane.model;

import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import poligoane.beans.CoordonateGps;
import poligoane.beans.DatePoligon;
import poligoane.beans.LatLng;
import poligoane.beans.Poligon;
import poligoane.beans.PoligonResponse;
import poligoane.beans.PoligonResponseError;
import poligoane.beans.StandardAddress;
import poligoane.google.GoogleMapsServices;
import poligoane.utils.UtilsAdrese;
import poligoane.utils.UtilsPoligoane;

public class OperatiiPoligoane {

	public DatePoligon getDatePoligonLivrare(String coords) {

		DatePoligon datePoligon = new DatePoligon("", "", "", "", "");

		if (coords == null)
			return datePoligon;

		if (coords.trim().isEmpty())
			return datePoligon;

		if (!coords.contains(","))
			return datePoligon;

		if (coords.split(",").length != 2)
			return datePoligon;

		if (coords.split(",").length == 2) {
			if (!isNumeric(coords.split(",")[0]) || !isNumeric(coords.split(",")[1]))
				return datePoligon;
		}

		boolean punctInZona = false;

		LatLng addressPoint = new LatLng();
		addressPoint.setLat(Double.parseDouble(coords.split(",")[0].trim()));
		addressPoint.setLng(Double.parseDouble(coords.split(",")[1].trim()));

		List<Poligon> listPoligoaneZona = getListPoligoane("ZM", "");

		for (Poligon poligon : listPoligoaneZona) {
			if (punctInPoligonZona(addressPoint, poligon, datePoligon)) {
				punctInZona = true;
				break;
			}
		}

		if (!punctInZona) {
			listPoligoaneZona = getListPoligoane("ZEMA", "");

			for (Poligon poligon : listPoligoaneZona) {
				if (punctInPoligonZona(addressPoint, poligon, datePoligon)) {
					punctInZona = true;
					break;
				}
			}
		}

		if (!punctInZona) {
			listPoligoaneZona = getListPoligoane("ZEMB", "");

			for (Poligon poligon : listPoligoaneZona) {
				if (punctInPoligonZona(addressPoint, poligon, datePoligon)) {
					break;
				}
			}
		}

		List<Poligon> listPoligoaneTonaj = getListPoligoane("LT", datePoligon.getFilialaPrincipala());

		for (Poligon poligon : listPoligoaneTonaj) {
			if (punctInPoligonTonaj(addressPoint, poligon, datePoligon)) {
				break;
			}
		}

		return datePoligon;

	}

	public PoligonResponse getDatePoligon(String localitate, String codJudet) {

		GoogleMapsServices serv = new GoogleMapsServices();

		StandardAddress address = new StandardAddress();
		address.setCity(localitate);
		address.setSector(UtilsAdrese.getNumeJudet(codJudet));

		CoordonateGps coordonate = serv.geocodeAddress(address);
		PoligonResponse poligonResponse;
		if (!coordonate.getError().isEmpty()) {
			poligonResponse = new PoligonResponseError();
			((PoligonResponseError) poligonResponse).setError(coordonate.getError());
		}			
		else
			 poligonResponse =  new PoligonResponse();
		
		String strCoords = coordonate.getLatitude() + "," + coordonate.getLongitude();

		DatePoligon datePoligon = getDatePoligonLivrare(strCoords);

		poligonResponse.setLatitude(coordonate.getLatitude());
		poligonResponse.setLongitude(coordonate.getLongitude());

		String filialePol = datePoligon.getFilialaPrincipala();
		if (!datePoligon.getFilialaSecundara().trim().isEmpty())
			filialePol += "," + datePoligon.getFilialaSecundara();

		poligonResponse.setPlant(filialePol);
		poligonResponse.setZone(UtilsPoligoane.getNumeZona(datePoligon.getTipZona()));
		poligonResponse.setHasRestriction(!datePoligon.getLimitareTonaj().trim().isEmpty());

		return  poligonResponse;
	}
	
	public PoligonResponse getDatePoligon(CoordonateGps coordonateGps) {

		PoligonResponse poligonResponse = new PoligonResponse();

		String strCoords = coordonateGps.getLatitude() + "," + coordonateGps.getLongitude();

		DatePoligon datePoligon = getDatePoligonLivrare(strCoords);

		poligonResponse.setLatitude(coordonateGps.getLatitude());
		poligonResponse.setLongitude(coordonateGps.getLongitude());

		String filialePol = datePoligon.getFilialaPrincipala();
		if (!datePoligon.getFilialaSecundara().trim().isEmpty())
			filialePol += "," + datePoligon.getFilialaSecundara();

		poligonResponse.setPlant(filialePol);
		
		poligonResponse.setZone(UtilsPoligoane.getNumeZona(datePoligon.getTipZona()));
		
		poligonResponse.setHasRestriction(!datePoligon.getLimitareTonaj().trim().isEmpty());

		return poligonResponse;
	}

	private List<LatLng> getCoordonatePoligon(String numeFisier) {

		List<LatLng> listCoords = new ArrayList<LatLng>();

		try {
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();

			InputStream iStream = new OperatiiPoligoane().getFileFromResourceAsStream("gpxfiles/" + numeFisier);
			Document doc = dBuilder.parse(iStream);
			doc.getDocumentElement().normalize();

			NodeList nList = doc.getElementsByTagName("trkpt");

			for (int temp = 0; temp < nList.getLength(); temp++) {

				Node nNode = nList.item(temp);

				if (nNode.getNodeType() == Node.ELEMENT_NODE) {

					Element eElement = (Element) nNode;

					double lat = Double.parseDouble(eElement.getAttribute("lat"));
					double lon = Double.parseDouble(eElement.getAttribute("lon"));

					LatLng coord = new LatLng(lat, lon);
					listCoords.add(coord);

				}

			}
		} catch (Exception ex) {

		}

		return listCoords;

	}

	public List<Poligon> getListPoligoane(String filtruPoligon, String filiala) {

		List<Poligon> listPoligoane = new ArrayList<Poligon>();

		try {

			OperatiiPoligoane opPoligoane = new OperatiiPoligoane();

			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();

			for (File file : getResourceFolderFiles("gpxfiles")) {

				Poligon poligon = new Poligon();

				InputStream iStream = opPoligoane.getFileFromResourceAsStream("gpxfiles/" + file.getName());
				Document doc = dBuilder.parse(iStream);
				doc.getDocumentElement().normalize();

				if (doc.getElementsByTagName("tip").item(0) != null)
					poligon.setTipPoligon(doc.getElementsByTagName("tip").item(0).getTextContent());
				else
					poligon.setTipPoligon("LT");

				poligon.setFiliala(doc.getElementsByTagName("pct").item(0).getTextContent());
				poligon.setNumeFisier(file.getName());
				poligon.setTonaj("");
				poligon.setNume("");

				if (poligon.getTipPoligon().equals("LT")) {
					poligon.setTonaj(doc.getElementsByTagName("LT").item(0).getTextContent());
					poligon.setNume(doc.getElementsByTagName("name").item(0).getTextContent());
				}

				boolean conditiePoligon = filtruPoligon.toLowerCase().equals(poligon.getTipPoligon().toLowerCase());

				if (filiala.trim().length() != 0)
					conditiePoligon = filtruPoligon.toLowerCase().equals(poligon.getTipPoligon().toLowerCase())
							&& poligon.getFiliala().equals(filiala);

				if (conditiePoligon)
					listPoligoane.add(poligon);

			}

		} catch (Exception ex) {
			System.out.println("getListPoligoane: " + ex.toString());
		}

		return listPoligoane;
	}

	private boolean punctInPoligonZona(LatLng punct, Poligon poligon, DatePoligon datePoligon) {
		boolean inPoligon = false;

		List<LatLng> coordPoligon = getCoordonatePoligon(poligon.getNumeFisier());

		boolean contains = ModelPoligoane.containsPoint(punct, coordPoligon, true);

		if (contains) {
			if (poligon.getFiliala().contains(",")) {
				String[] arrayFiliale = poligon.getFiliala().split(",");
				datePoligon.setFilialaPrincipala(arrayFiliale[0].trim());
				datePoligon.setFilialaSecundara(arrayFiliale[1].trim());
			} else {
				datePoligon.setFilialaPrincipala(poligon.getFiliala().trim());
				datePoligon.setFilialaSecundara("");
			}

			datePoligon.setTipZona(poligon.getTipPoligon());
			inPoligon = true;
		}

		return inPoligon;
	}

	private boolean punctInPoligonTonaj(LatLng punct, Poligon poligon, DatePoligon datePoligon) {
		boolean inPoligon = false;

		List<LatLng> coordPoligon = getCoordonatePoligon(poligon.getNumeFisier());
		boolean contains = ModelPoligoane.containsPoint(punct, coordPoligon, true);

		if (contains) {
			datePoligon.setLimitareTonaj(poligon.getTonaj());
			datePoligon.setNumeZona(poligon.getNume());
			inPoligon = true;
		}

		return inPoligon;
	}

	private InputStream getFileFromResourceAsStream(String fileName) {

		ClassLoader classLoader = getClass().getClassLoader();
		InputStream inputStream = classLoader.getResourceAsStream(fileName);

		if (inputStream == null) {
			throw new IllegalArgumentException("file not found! " + fileName);
		} else {
			return inputStream;
		}

	}

	private static File[] getResourceFolderFiles(String folder) {
		ClassLoader loader = Thread.currentThread().getContextClassLoader();
		URL url = loader.getResource(folder);
		String path = url.getPath();
		return new File(path).listFiles();
	}

	private static boolean isNumeric(String strNum) {
		if (strNum == null) {
			return false;
		}
		try {
			Double.parseDouble(strNum);
		} catch (NumberFormatException nfe) {
			return false;
		}
		return true;
	}

}
