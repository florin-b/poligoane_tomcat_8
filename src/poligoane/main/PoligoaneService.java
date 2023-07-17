package poligoane.main;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import poligoane.beans.Adresa;
import poligoane.beans.CoordonateGps;
import poligoane.beans.DatePoligon;
import poligoane.beans.PoligonResponse;
import poligoane.model.OperatiiPoligoane;

@Path("/")
public class PoligoaneService {

	@Path("test")
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String testService() {
		return "Evrika!";
	}

	@Path("datePoligon")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getDatePoligon(@QueryParam("coordonate") String coordonate) {

		DatePoligon datePoligon = new OperatiiPoligoane().getDatePoligonLivrare(coordonate);

		return Response.status(200).header("Access-Control-Allow-Origin", "*")
				.header("Access-Control-Allow-Headers", "origin, content-type, accept, authorization")
				.header("Access-Control-Allow-Credentials", "true")
				.header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD")
				.header("Access-Control-Max-Age", "1209600").entity(datePoligon).build();
	}
	
	
	@Path("dateAdresa")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response getDateAdresa2(Adresa adresa){
		
		PoligonResponse poligonResponse = new OperatiiPoligoane().getDatePoligon(adresa.getLocalitate(), adresa.getCodJudet());
		return Response.status(200).header("Access-Control-Allow-Origin", "*")
				.header("Access-Control-Allow-Headers", "origin, content-type, accept, authorization")
				.header("Access-Control-Allow-Credentials", "true")
				.header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD")
				.header("Access-Control-Max-Age", "1209600").entity(poligonResponse).build();
	}
	
	@Path("dateCoordonate")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response getDateAdresa2(CoordonateGps coordonateGps){
		
		PoligonResponse poligonResponse = new OperatiiPoligoane().getDatePoligon(coordonateGps);
		return Response.status(200).header("Access-Control-Allow-Origin", "*")
				.header("Access-Control-Allow-Headers", "origin, content-type, accept, authorization")
				.header("Access-Control-Allow-Credentials", "true")
				.header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD")
				.header("Access-Control-Max-Age", "1209600").entity(poligonResponse).build();
	}

}
