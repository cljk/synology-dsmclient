package de.csmp.dsmclient.webapi;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

import javax.json.JsonObject;

public class CoreGroup {
	public static final String SYNO_DOWNLOAD_STATION_TASK = "SYNO.DownloadStation.Task";

	WebApi webApi;
	
	public CoreGroup(WebApi webApi) {
		this.webApi = webApi;	
	}

	public JsonObject listLocal() throws IOException, URISyntaxException {
		Map<String, String> param = new HashMap<String, String>();
		param.put("additional",  "[\"email\",\"description\",\"expired\"]");
		param.put("type",  "local");
		param.put("offset",  "0");
		param.put("limit",  "-1");
		param.put("name_only",  "false");
		
		JsonObject r = webApi.callApiMethod("SYNO.Core.Group", 1, "list", param);
		return r;
	}
}
