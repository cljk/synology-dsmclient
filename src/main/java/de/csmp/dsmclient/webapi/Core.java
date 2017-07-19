package de.csmp.dsmclient.webapi;

import de.csmp.dsmclient.DsmConnection;

public class Core extends WebApi {
	public static final String SYNO_DOWNLOAD_STATION_TASK = "SYNO.DownloadStation.Task";

	public Core(DsmConnection conn) {
		super(conn);		
	}

	public CoreUser getUser() {
		return new CoreUser(conn);
	}
	
	public CorePolling getPolling() {
		return new CorePolling(conn);
	}
	
	public CoreSystem getSystem() {
		return new CoreSystem(conn);
	}
}
