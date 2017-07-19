package de.csmp.dsmclient;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

import javax.json.JsonObject;
import javax.net.ssl.SSLContext;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContexts;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.csmp.dsmclient.webapi.Core;
import de.csmp.dsmclient.webapi.DownloadStation;


public class DsmConnection {
	private static final String SYNO_API_INFO = "SYNO.API.Info";

	
	private static final String SYNO_API_AUTH = "SYNO.API.Auth";

	private static final Logger log = LoggerFactory.getLogger(DsmConnection.class);
    
	
	
	//private String username;
	//private String password;
	
	private String host;
	private int port = 5001;
	
	private boolean useSsl = true;
	private boolean acceptAnyCert = true;
	
	String baseUrl = null;
	
	
	// state
	private String sid = null;
	private JsonObject apiInfo = null;
	private CloseableHttpClient httpClient = null;
	
	public DsmConnection(String host, int port, boolean useSsl,
			boolean acceptAnyCert) {
		this.host = host;
		this.port = port;
		
		this.useSsl = useSsl;
		this.acceptAnyCert = acceptAnyCert;
		
		setup();
	}

	public DsmConnection(String host, int port) {
		this.host = host;
		this.port = port;

		setup();
	}

	private void setup() {
		this.baseUrl = (useSsl ? "https://" : "http://") + host + ":" + port + "/";
		log.debug("baseUrl: {}", baseUrl);
	}


	
	
	/**
	 * generic API call implementation
	 * 
	 * @see DsmConnection#callApiMethod(String, int, String, Map)
	 */
	public JsonObject callApiMethod(String apiName, int version, String method) throws IOException, URISyntaxException {
		return callApiMethod(apiName, version, method, null);
	}
	
	/**
	 * generic API call implementation
	 * will lookup path to requested api and setup the request and parse the json-response
	 * 
	 * @return data-element if successful - throws Exception else
	 */
	public JsonObject callApiMethod(String apiName, int version, String method, Map<String, String> parameters) throws IOException, URISyntaxException {
		log.debug("callApiMethod >> {}, v{}, {}", apiName, version, method);
		URIBuilder builder = getUriBuilderForApi(apiName, version, method);
		if ((parameters != null) && (! parameters.isEmpty())) {
			for(String key : parameters.keySet()) {
				builder.addParameter(key, parameters.get(key));
			}
		}
		
		// TODO implement POST as default and GET as option
		HttpGet get = new HttpGet(builder.build());
		HttpResponse response = getHttpClient().execute(get);
		
		JsonObject result = ProcessingUtils.processSuccessfulJsonResponse(response);
		JsonObject data = result.getJsonObject("data");
		log.debug("callApiMethod << {}, {}, {}", apiName, version, method);
		return data;
	}




	/*
	 * implementing some key api calls
	 */
	
	public String login(String username, String password) throws IOException, URISyntaxException {
		Map<String, String> params = new HashMap<String, String>();
		params.put("account",  username);
		params.put("passwd",  password);
		params.put("format", "cookie");
		
		JsonObject data = callApiMethod(SYNO_API_AUTH, 2, "login", params);
		sid = data.getString("sid");
		return sid;
	}
	public void logout() throws IOException, URISyntaxException {
		Map<String, String> params = new HashMap<String, String>();
//		params.put("session",  username);
		
		callApiMethod(SYNO_API_AUTH, 2, "logout", params);
	}

	public JsonObject queryApis() throws IOException, URISyntaxException {
		return callApiMethod(SYNO_API_INFO, 1, "query", null);
	}
	
	

	
	public DownloadStation getDownloadStation() {
		return new DownloadStation(this);
	}
	public Core getCore() {
		return new Core(this);
	}

	
	

	
	
	public URIBuilder getUriBuilderForApi(String apiName, int version, String method) throws IOException {
		URIBuilder b = getUriBuilder()
				.setPath(getWebApiPath(apiName))
				.setParameter("api", apiName)
				.setParameter("version", "" + version)
				.setParameter("method", method)
			    ;
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
	
	protected HttpClient getHttpClient() throws IOException {
		if (httpClient == null) {
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
		}
		return httpClient;
	}


	public String getWebApiPath(String apiName) throws IOException {
		// FIXME implementMe
		if (apiName.equals(SYNO_API_AUTH)) {
			return "webapi/auth.cgi";	// cannot know first api URL
		} else if (apiName.equals(SYNO_API_INFO)) {
			return "webapi/query.cgi";
		} else if (apiName.equals(DownloadStation.SYNO_DOWNLOAD_STATION_TASK)) {
			return "webapi/DownloadStation/task.cgi";
		} 
		log.warn("no mapped api path for " + apiName + " - use default entry.cgi");
		return "webapi/entry.cgi";
	}
	
	public String getBaseUrl() {
		return baseUrl;
	}
	
	public String getWebApiBaseUrl() {
		return baseUrl + "webapi/";
	}
	
	public JsonObject getApiInfo() throws IOException, URISyntaxException {
		if (apiInfo == null) {
			apiInfo = queryApis();
		}
		return apiInfo;
	}


	public void setApiInfo(JsonObject apiInfo) {
		this.apiInfo = apiInfo;
	}

	

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

	public void setBaseUrl(String baseUrl) {
		this.baseUrl = baseUrl;
	}

}
