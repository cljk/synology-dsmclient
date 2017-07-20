package de.csmp.dsmclient.webapi;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

import javax.json.JsonObject;

import de.csmp.dsmclient.DsmConnection;

public class CoreUser {
	public static final String SYNO_DOWNLOAD_STATION_TASK = "SYNO.DownloadStation.Task";

	DsmConnection conn;
	
	public CoreUser(DsmConnection conn) {
		this.conn = conn;	
	}

	public void create(String name, String password, String desc, String email, boolean cannotChgPasswd, boolean notifyByEmail, boolean sendPassword) throws IOException, URISyntaxException {
		Map<String, String> param = new HashMap<String, String>();
		param.put("name",  name);
		param.put("password",  password);
		param.put("description",  desc);
		param.put("email",  email);
		param.put("cannot_chg_passwd", "" + cannotChgPasswd);
		param.put("expired", "normal");
		param.put("password",  password);
		param.put("notify_by_email", "" + notifyByEmail);
		param.put("send_password", "" + sendPassword);
		
		JsonObject r = conn.getWebApi().callApiMethod("SYNO.Core.User", 1, "create", param);
		int x = 1;
	}
	public void set(String name, String newName, String password, String desc, String email, boolean cannotChgPasswd, boolean notifyByEmail, boolean sendPassword) throws IOException, URISyntaxException {
		Map<String, String> param = new HashMap<String, String>();
		param.put("name",  name);
		param.put("new_name",  newName);
		param.put("password",  password);
		param.put("description",  desc);
		param.put("email",  email);
		param.put("cannot_chg_passwd", "" + cannotChgPasswd);
		param.put("expired", "normal");
		if (password != null) {
			param.put("password",  password);
		}
		param.put("notify_by_email", "" + notifyByEmail);
		param.put("send_password", "" + sendPassword);
		
		JsonObject r = conn.getWebApi().callApiMethod("SYNO.Core.User", 1, "set", param);
		int x = 1;
	}
	
	public void addGroupMember(String group, String userName) throws IOException, URISyntaxException {
		Map<String, String> param = new HashMap<String, String>();
		param.put("group",  group);
		param.put("name",  userName);
	
		JsonObject r = conn.getWebApi().callApiMethod("SYNO.Core.Group.Member", 1, "add", param);
		int x = 1;
	}
	
	public void removeGroupMember(String group, String userName) throws IOException, URISyntaxException {
		Map<String, String> param = new HashMap<String, String>();
		param.put("group",  group);
		param.put("name",  userName);
	
		JsonObject r = conn.getWebApi().callApiMethod("SYNO.Core.Group.Member", 1, "remove", param);
		int x = 1;
	}
}
