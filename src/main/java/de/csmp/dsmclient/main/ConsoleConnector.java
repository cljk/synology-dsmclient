package de.csmp.dsmclient.main;

import javax.json.JsonObject;

import de.csmp.dsmclient.DsmConnection;

public class ConsoleConnector {

	public static void main(String[] args) throws Exception {
		if (args.length < 5) {
			System.err.println("usage:");
			System.err.println("	mvn exec:java -Dexec.args=\"{dsmHostIp/dsmHostName} {dsmPort} {useSsl:true/false} {userName} {password}\"");
			System.err.println("just logs in and retrieves some stat info");
			System.exit(1);
		}
		
		String host = args[0];
		String port = args[1];
		String useSsl = args[2];
		
		String user = args[3];
		String password = args[4];
		
		DsmConnection conn = new DsmConnection(host, Integer.parseInt(port), Boolean.parseBoolean(useSsl), true);
		
		JsonObject apiInfo = conn.queryApis();
		System.out.println("apiQuery: " + apiInfo);
		
		String sid = conn.login(user, password);
		System.out.println("sid: " + sid);
		
		JsonObject utilize = conn.getCore().getSystem().getCurrentUtilization();
		System.out.println("util: " + utilize);
		
		
		conn.logout();
	}

}
