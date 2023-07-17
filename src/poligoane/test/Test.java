package poligoane.test;

import poligoane.beans.StandardAddress;
import poligoane.google.GoogleMapsServices;
import poligoane.model.OperatiiPoligoane;
import poligoane.utils.UtilsAdrese;

public class Test {

	 public static void main(String[] args) {
	       // OperatiiPoligoane op = new OperatiiPoligoane();
	       // System.out.println(op.getDatePoligonLivrare("44.33702119325218, 23.783761000975034"));
		 
		 
		 	GoogleMapsServices serv = new GoogleMapsServices();
		 	
		 	StandardAddress address = new StandardAddress();
		 	address.setCity("Craiewrwerova");
		 	address.setSector(UtilsAdrese.getNumeJudet("16"));
		 	
		 //	System.out.println(serv.geocodeAddress(address));
		 	
		 	
		 	System.out.println(new OperatiiPoligoane().getDatePoligon("Craiova", "16"));
		 
	    }
}
