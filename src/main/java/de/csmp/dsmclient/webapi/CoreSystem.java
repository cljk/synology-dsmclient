package de.csmp.dsmclient.webapi;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

import javax.json.JsonObject;

import de.csmp.dsmclient.DsmConnection;

public class CoreSystem extends WebApi {
	public static final String SYNO_DOWNLOAD_STATION_TASK = "SYNO.DownloadStation.Task";

	public CoreSystem(DsmConnection conn) {
		super(conn);		
	}

	public JsonObject getCurrentUtilization() throws IOException, URISyntaxException {
		Map<String, String> parm = new HashMap<String, String>();
		parm.put("type", "current");
		
		return conn.callApiMethod("SYNO.Core.System.Utilization", 1, "get", parm);
	}
}
