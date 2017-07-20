package de.csmp.dsmclient.webapi;

import java.io.IOException;
import java.net.URISyntaxException;

import javax.json.JsonObject;

public class DownloadStation {
	public static final String SYNO_DOWNLOAD_STATION_TASK = "SYNO.DownloadStation.Task";

	WebApi webApi;
	
	public DownloadStation(WebApi webApi) {
		this.webApi = webApi;	
	}

	public JsonObject getInfo() throws IOException, URISyntaxException {
		return webApi.callApiMethod("SYNO.DownloadStation.Info", 1, "getinfo");
	}
	
	public JsonObject list() throws IOException, URISyntaxException {
		return webApi.callApiMethod(SYNO_DOWNLOAD_STATION_TASK, 1, "list", null);
	}
	
	
	// FIXME implement me
	// SYNO.DownloadStation.Info
	// SYNO.DownloadStation.Schedule
	// SYNO.DownloadStation.Task
	// SYNO.DownloadStation.Statistic
	// SYNO.DownloadStation.RSS.Site
	// SYNO.DownloadStation.RSS.Feed

}
