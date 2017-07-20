package de.csmp.dsmclient;

import java.io.IOException;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.HttpClient;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.csmp.dsmclient.webapi.Auth;
import de.csmp.dsmclient.webapi.DownloadStation;
import de.csmp.dsmclient.webapi.WebApi;


public class DsmConnection {
	private static final Logger log = LoggerFactory.getLogger(DsmConnection.class);
 	
	// connection config
	private String host;
	private int port = 5001;
	
	private boolean useSsl = true;
	private boolean acceptAnyCert = true;
	
	
	
	// inner state
	private String sid = null;
	private CloseableHttpClient httpClient = null;
	
	private WebApi webApi = new WebApi(this);
	
	
	public DsmConnection(String host, int port) {
		this.host = host;
		this.port = port;

		setup();
	}
	
	public DsmConnection(String host, int port, boolean useSsl,
			boolean acceptAnyCert) {
		this.host = host;
		this.port = port;
		
		this.useSsl = useSsl;
		this.acceptAnyCert = acceptAnyCert;
		
		setup();
	}

	/**
	 * to be called by any constructor
	 */
	private void setup() {
		String baseUrl = (useSsl ? "https://" : "http://") + host + ":" + port + "/";
		log.debug("baseUrl: {}", baseUrl);
	}


	
	
	




	
	
	



	
	

	
	
	public URIBuilder getUriBuilderForApi(String apiName, int version, String method) throws IOException {
		String path = getWebApi().getWebApiPath(apiName);
		URIBuilder b = getUriBuilder()
				.setPath(path)
				.setParameter("api", apiName)
				.setParameter("version", "" + version)
				.setParameter("method", method)
			    ;
		// if session-id is set add it as parameter "_sid"
		if (! StringUtils.isEmpty(sid)) {
			b.setParameter("_sid", sid);
		}
		return b;
	}
	public URIBuilder getUriBuilder() {
		URIBuilder builder = new URIBuilder();
		builder.setScheme((useSsl ? "https" : "http")).setHost(host).setPort(port);
		return builder;
	}
	
	public HttpClient getHttpClient() throws IOException {
		if (httpClient == null) {
			httpClient = ProcessingUtils.initializeHttpClient(acceptAnyCert);
		}
		return httpClient;
	}

	
	
	
	public WebApi getWebApi() {
		return webApi;
	}
	
	

	
	
	/*
	 * bean getter/setter
	 */
	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public boolean isUseSsl() {
		return useSsl;
	}

	public void setUseSsl(boolean useSsl) {
		this.useSsl = useSsl;
	}

	public boolean isAcceptAnyCert() {
		return acceptAnyCert;
	}

	public void setAcceptAnyCert(boolean acceptAnyCert) {
		this.acceptAnyCert = acceptAnyCert;
	}

	public String getSid() {
		return sid;
	}

	public void setSid(String sid) {
		this.sid = sid;
	}
}
