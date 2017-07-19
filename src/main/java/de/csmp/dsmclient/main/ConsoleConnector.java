package de.csmp.dsmclient.main;

import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonWriter;
import javax.json.JsonWriterFactory;
import javax.json.stream.JsonGenerator;

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
		
		
		//JsonWriter jsonWriter = getPrettyJsonWriter(System.out);
		
		DsmConnection conn = new DsmConnection(host, Integer.parseInt(port), Boolean.parseBoolean(useSsl), true);
		
		JsonObject apiInfo = conn.queryApis();
		System.out.print("apiQuery:");
		getPrettyJsonWriter(System.out).writeObject(apiInfo);
		
		String sid = conn.login(user, password);
		System.out.println("sid: " + sid);
		
		JsonObject utilize = conn.getCore().getSystem().getCurrentUtilization();
		System.out.print("utilize:");
		getPrettyJsonWriter(System.out).writeObject(utilize);
		
		
		conn.logout();
	}

	private static JsonWriter getPrettyJsonWriter(PrintStream writer) {
		Map<String, Object> writerProperties = new HashMap<>(1);
        writerProperties.put(JsonGenerator.PRETTY_PRINTING, true);

		JsonWriterFactory writerFactory = Json.createWriterFactory(writerProperties);
		JsonWriter jsonWriter = writerFactory.createWriter(writer);
		return jsonWriter;
	}

}
