package de.csmp.dsmclient.webapi;

import de.csmp.dsmclient.DsmConnection;

public class Core {
	public static final String SYNO_DOWNLOAD_STATION_TASK = "SYNO.DownloadStation.Task";

	DsmConnection conn;
	
	public Core(DsmConnection conn) {
		this.conn = conn;
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
