package de.csmp.dsmclient.webapi;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Map;

import javax.json.JsonObject;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.csmp.dsmclient.DsmConnection;
import de.csmp.dsmclient.ProcessingUtils;

public class WebApi {
	private static final Logger log = LoggerFactory.getLogger(WebApi.class);
    
	public static final String SYNO_API_INFO = "SYNO.API.Info";

	
	DsmConnection conn;
	private JsonObject apiInfo = null;
	
	
	public WebApi(DsmConnection conn) {
		this.conn = conn;
	}
	
	
	public Auth getAuth() {
		return new Auth(conn);
	}
	public Core getCore() {
		return new Core(conn);
	}
	public DownloadStation getDownloadStation() {
		return new DownloadStation(conn);
	}
	
	
	
	
	public JsonObject queryApis() throws IOException, URISyntaxException {
		return callApiMethod(SYNO_API_INFO, 1, "query", null);
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
		URIBuilder builder = conn.getUriBuilderForApi(apiName, version, method);
		if ((parameters != null) && (! parameters.isEmpty())) {
			for(String key : parameters.keySet()) {
				builder.addParameter(key, parameters.get(key));
			}
		}
		
		// TODO implement POST as default and GET as option
		HttpGet get = new HttpGet(builder.build());
		HttpResponse response = conn.getHttpClient().execute(get);
		
		JsonObject result = ProcessingUtils.processSuccessfulJsonResponse(response);
		JsonObject data = result.getJsonObject("data");
		log.debug("callApiMethod << {}, {}, {}", apiName, version, method);
		return data;
	}

	
	public String getWebApiPath(String apiName) throws IOException {
		if (apiName.equals(WebApi.SYNO_API_INFO)) {
			// fixed API url for SYNO_API_INFO
			// have to query API for paths first
			return "webapi/query.cgi";
		}
		
		
		// FIXME implementMe - query api and use path of JSON
		
		if (apiName.equals(Auth.SYNO_API_AUTH)) {
			return "webapi/auth.cgi";	
		}  else if (apiName.equals(DownloadStation.SYNO_DOWNLOAD_STATION_TASK)) {
			return "webapi/DownloadStation/task.cgi";
		} 
		log.warn("no mapped api path for " + apiName + " - use default entry.cgi");
		return "webapi/entry.cgi";
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
}
