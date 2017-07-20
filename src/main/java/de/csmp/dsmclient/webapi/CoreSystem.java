package de.csmp.dsmclient.webapi;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

import javax.json.JsonObject;

public class CoreSystem {
	public static final String SYNO_DOWNLOAD_STATION_TASK = "SYNO.DownloadStation.Task";

	WebApi webApi;
	
	public CoreSystem(WebApi webApi) {
		this.webApi = webApi;		
	}

	public JsonObject getCurrentUtilization() throws IOException, URISyntaxException {
		Map<String, String> parm = new HashMap<String, String>();
		parm.put("type", "current");
		
		return webApi.callApiMethod("SYNO.Core.System.Utilization", 1, "get", parm);
	}
}
