package de.csmp.dsmclient.webapi;

import java.io.IOException;
import java.net.URISyntaxException;

import javax.json.JsonObject;

import de.csmp.dsmclient.DsmConnection;

public class DownloadStation extends WebApi {
	public static final String SYNO_DOWNLOAD_STATION_TASK = "SYNO.DownloadStation.Task";

	public DownloadStation(DsmConnection conn) {
		super(conn);		
	}

	public JsonObject getInfo() throws IOException, URISyntaxException {
		return conn.callApiMethod("SYNO.DownloadStation.Info", 1, "getinfo");
	}
	
	public JsonObject list() throws IOException, URISyntaxException {
		return conn.callApiMethod(SYNO_DOWNLOAD_STATION_TASK, 1, "list", null);
	}
	
	public void nofunction() {
		// FIXME implement me
		// SYNO.DownloadStation.Info
		// SYNO.DownloadStation.Schedule
		// SYNO.DownloadStation.Task
		// SYNO.DownloadStation.Statistic
		// SYNO.DownloadStation.RSS.Site
		// SYNO.DownloadStation.RSS.Feed
	}
}
