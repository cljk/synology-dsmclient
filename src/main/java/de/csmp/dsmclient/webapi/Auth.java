package de.csmp.dsmclient.webapi;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

import javax.json.JsonObject;

import de.csmp.dsmclient.DsmConnection;

public class Auth {
	public static final String SYNO_API_AUTH = "SYNO.API.Auth";

	DsmConnection conn;
	
	public Auth(DsmConnection conn) {
		this.conn = conn;
	}

	
	
	public String login(String username, String password) throws IOException, URISyntaxException {
		Map<String, String> params = new HashMap<String, String>();
		params.put("account",  username);
		params.put("passwd",  password);
		params.put("format", "cookie");
		
		JsonObject data = conn.getWebApi().callApiMethod(SYNO_API_AUTH, 2, "login", params);
		conn.setSid(data.getString("sid"));
		return conn.getSid();
	}
	
	public void logout() throws IOException, URISyntaxException {
		Map<String, String> params = new HashMap<String, String>();
//		params.put("session",  username);
		
		conn.getWebApi().callApiMethod(SYNO_API_AUTH, 2, "logout", params);
		conn.setSid(null);
	}

}
