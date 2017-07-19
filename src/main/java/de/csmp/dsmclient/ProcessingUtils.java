package de.csmp.dsmclient;

import java.io.IOException;
import java.io.InputStream;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.JsonValue;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;

public class ProcessingUtils {
	public static JsonObject processSuccessfulJsonResponse(HttpResponse response) throws IOException {
		if (response.getStatusLine().getStatusCode() != 200) {
			throw new IOException("HttpStatus code not successful: " + response.getStatusLine().getStatusCode());
		}
		
		HttpEntity entity = response.getEntity();
	    
	    //String entityContents = EntityUtils.toString(entity);
	    //System.out.println(entityContents);
	    InputStream is = entity.getContent();
	    JsonReader jsonReader = Json.createReader(is);
	    JsonObject result = jsonReader.readObject();
	    is.close();
	    
	    JsonValue successVal = result.get("success");
	    if (! "true".equals(successVal.toString())) {
	    	throw new IOException("request not successful - " + result.toString());
	    }
		return result;
	}
}
