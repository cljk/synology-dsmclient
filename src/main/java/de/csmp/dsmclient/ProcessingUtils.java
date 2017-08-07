package de.csmp.dsmclient;

import java.io.IOException;
import java.io.InputStream;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.JsonValue;
import javax.net.ssl.SSLContext;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContexts;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ProcessingUtils {
	private static final Logger log = LoggerFactory.getLogger(ProcessingUtils.class);
    
	public static void assertSuccessfulHttpResponse(HttpResponse response) throws IOException {
		if (response.getStatusLine().getStatusCode() != 200) {
			throw new IOException("HttpStatus code not successful: " + response.getStatusLine().getStatusCode());
		}
	}
	
	public static void assertSuccessfulJsonResponse(JsonObject result) throws IOException {
		JsonValue successVal = result.get("success");
	    if (! "true".equals(successVal.toString())) {
	    	throw new IOException("request not successful - " + result.toString());
	    }
	}
	
	public static JsonObject processJsonResponse(HttpResponse response) throws IOException {
		HttpEntity entity = response.getEntity();
	    
	    //String entityContents = EntityUtils.toString(entity);
	    //System.out.println(entityContents);
	    InputStream is = entity.getContent();
	    JsonReader jsonReader = Json.createReader(is);
	    JsonObject result = jsonReader.readObject();
	    is.close();
	    
	    return result;
	}


	
	public static CloseableHttpClient initializeHttpClient(boolean acceptAnyCert) throws IOException {
		CloseableHttpClient httpClient;
		if (acceptAnyCert) {
			log.warn("ACCEPTING ANY CERTIFICATE");
			try {
				SSLContext sslContext = SSLContexts.custom()
		                .loadTrustMaterial(null, 
		                		(x509CertChain, authType) -> true)
		                .build();
			    SSLConnectionSocketFactory sslsf = 
			    		new SSLConnectionSocketFactory( 
			    				sslContext, 
			    				(hostname, session) -> {
			    					log.warn("certificate hostname: {}", hostname);
			    					return true;
			    					}
			    				);
			    httpClient = HttpClients.custom()
			    		.setSSLSocketFactory(sslsf)
			    		.build();
			} catch (Exception ex) {
				throw new IOException(ex.getMessage(), ex);
			}
		} else {
			httpClient = HttpClients.createDefault();
		}
		return httpClient;
	}
}
