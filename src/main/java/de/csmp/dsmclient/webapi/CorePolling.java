package de.csmp.dsmclient.webapi;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

import javax.json.JsonObject;

public class CorePolling {
	public static final String SYNO_DOWNLOAD_STATION_TASK = "SYNO.DownloadStation.Task";
	
	WebApi api;
	
	public CorePolling(WebApi api) {
		this.api = api;	
	}

	public JsonObject getLoadData() throws IOException, URISyntaxException {
		return getData("load", false);
	}
	
	public JsonObject getData(String action, boolean loadDisabledPort) throws IOException, URISyntaxException {
		Map<String, String> param = new HashMap<String, String>();
		param.put("action",  action);
		param.put("load_disabled_port",  "" + loadDisabledPort);
	
		return api.callApiMethod("SYNO.Core.Polling.Data", 1, "get", param); 
	}
}
