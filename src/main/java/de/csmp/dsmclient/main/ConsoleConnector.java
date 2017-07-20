package de.csmp.dsmclient.main;

import java.util.HashMap;
import java.util.Map;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonWriterFactory;
import javax.json.stream.JsonGenerator;

import de.csmp.dsmclient.DsmConnection;
import de.csmp.dsmclient.webapi.WebApi;

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
		
		// init JSON writer (for pretty output)
		Map<String, Object> writerProperties = new HashMap<>(1);
        writerProperties.put(JsonGenerator.PRETTY_PRINTING, true);
		JsonWriterFactory jsonWriterFactory = Json.createWriterFactory(writerProperties);
		
		
		
		// init DSM connection
		DsmConnection conn = new DsmConnection(host, Integer.parseInt(port), Boolean.parseBoolean(useSsl), true);
		WebApi webApi = conn.getWebApi();
		
		// invoke some API calls
		JsonObject apiInfo = webApi.queryApis();
		System.out.print("apiQuery:");
		jsonWriterFactory.createWriter(System.out).writeObject(apiInfo);
		
		String sid = webApi.getAuth().login(user, password);
		System.out.println("sid: " + sid);
		
		JsonObject utilize = webApi.getCore().getSystem().getCurrentUtilization();
		System.out.print("utilize:");
		jsonWriterFactory.createWriter(System.out).writeObject(utilize);
		
		JsonObject userList = webApi.getCore().getUser().listLocalBasic();
		System.out.print("userList:");
		jsonWriterFactory.createWriter(System.out).writeObject(userList);
		
		JsonObject groupList = webApi.getCore().getGroup().listLocal();
		System.out.print("groupList:");
		jsonWriterFactory.createWriter(System.out).writeObject(groupList);
		
		
		
		webApi.getAuth().logout();
	}
}
