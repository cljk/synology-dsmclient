package de.csmp.dsmclient.webapi;

public class Core {
	public static final String SYNO_DOWNLOAD_STATION_TASK = "SYNO.DownloadStation.Task";

	WebApi webApi;
	
	public Core(WebApi webApi) {
		this.webApi = webApi;
	}

	public CoreUser getUser() {
		return new CoreUser(webApi);
	}
	
	public CorePolling getPolling() {
		return new CorePolling(webApi);
	}
	
	public CoreSystem getSystem() {
		return new CoreSystem(webApi);
	}
}
