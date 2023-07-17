package poligoane.google;

import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.errors.OverQueryLimitException;
import com.google.maps.model.GeocodingResult;

import poligoane.beans.CoordonateGps;
import poligoane.beans.StandardAddress;

public class GoogleMapsServices {

	public  CoordonateGps geocodeAddress(StandardAddress address)  {
		CoordonateGps coordonateGps;

		double latitude = 0;
		double longitude = 0;
		String error = "";

		try {

			StringBuilder strAddress = new StringBuilder();

			if (address.getStreet() != null && !address.getStreet().equals("")) {
				strAddress.append(address.getStreet());
				strAddress.append(",");
			}

			if (address.getNumber() != null && !address.getStreet().equals("")) {
				strAddress.append(address.getNumber());
				strAddress.append(",");
			}

			if (address.getSector() != null && !address.getSector().equals("")) {
				strAddress.append(address.getSector());
				strAddress.append(",");
			}

			if (address.getCity() != null && !address.getCity().equals("")) {
				strAddress.append(address.getCity());
				strAddress.append(",");
				strAddress.append(address.getCountry());
			}

			GeoApiContext context = CustomGoogleContext.getGeoApiContext();

			GeocodingResult[] results = GeocodingApi.geocode(context, strAddress.toString()).await();

			if (results.length > 0) {
				latitude = results[0].geometry.location.lat;
				longitude = results[0].geometry.location.lng;
			}
			
			context.close();

		} catch (OverQueryLimitException q) {
			latitude = 0;
			longitude = 0;
			error = q.toString();

		} catch (Exception e) {
			latitude = 0;
			longitude = 0;
			error = e.toString();
		}

		coordonateGps = new CoordonateGps(latitude, longitude, error);

		return coordonateGps;
	}

}
