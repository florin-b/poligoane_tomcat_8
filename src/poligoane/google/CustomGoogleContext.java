package poligoane.google;

import java.util.concurrent.TimeUnit;

import com.google.maps.GeoApiContext;

import poligoane.utils.Utils;


public class CustomGoogleContext {
	
	private static GeoApiContext.Builder instanceKey;
	private CustomGoogleContext() {

	}

	public static GeoApiContext getGeoApiContext() {
		
		instanceKey = new GeoApiContext.Builder();
		instanceKey.apiKey(Utils.GOOGLE_PAYED_KEY);
		instanceKey.queryRateLimit(2);
		instanceKey.retryTimeout(0, TimeUnit.SECONDS);
		instanceKey.connectTimeout(1, TimeUnit.SECONDS);
		
		return instanceKey.build();
		
	}
	
	
}
