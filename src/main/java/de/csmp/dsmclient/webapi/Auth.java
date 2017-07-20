package de.csmp.dsmclient.webapi;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

import javax.json.JsonObject;

public class Auth {
	public static final String SYNO_API_AUTH = "SYNO.API.Auth";

	WebApi webApi;
	
	public Auth(WebApi webApi) {
		this.webApi = webApi;
	}

	
	
	public String login(String username, String password) throws IOException, URISyntaxException {
		Map<String, String> params = new HashMap<String, String>();
		params.put("account",  username);
		params.put("passwd",  password);
		params.put("format", "cookie");
		
		JsonObject data = webApi.callApiMethod(SYNO_API_AUTH, 2, "login", params);
		webApi.getConn().setSid(data.getString("sid"));
		return webApi.getConn().getSid();
	}
	
	public void logout() throws IOException, URISyntaxException {
		Map<String, String> params = new HashMap<String, String>();
//		params.put("session",  username);
		
		webApi.callApiMethod(SYNO_API_AUTH, 2, "logout", params);
		webApi.getConn().setSid(null);
	}

}
