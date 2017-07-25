package de.csmp.dsmclient.webapi;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

import javax.json.JsonObject;

public class OfficeNode {
	public static final String API_NAME = "SYNO.Office.Node";

	private static final String LIST_FIELDS = "{\"link_id\":false,\"shortcut\":true,\"tag\":true,\"ctime\":true,\"recycle\":true,\"acl\":true}";
	
	WebApi webApi;
	
	public OfficeNode(WebApi webApi) { 
		this.webApi = webApi;	
	}

	public JsonObject listSelf() throws IOException, URISyntaxException {
		Map<String, String> param = new HashMap<String, String>();
		param.put("mode",  "self");
		param.put("filter",  "{\"parent_id\":\"root\",\"recycle\":false}");
		param.put("field",  LIST_FIELDS);
		
		JsonObject r = webApi.callApiMethod(API_NAME, 1, "list", param);
		return r;
	}
	
	public JsonObject listOther() throws IOException, URISyntaxException {
		Map<String, String> param = new HashMap<String, String>();
		param.put("mode",  "other");
		//param.put("filter",  "{\"parent_id\":\"root\",\"recycle\":false}");
		param.put("field",  LIST_FIELDS);
		
		JsonObject r = webApi.callApiMethod(API_NAME, 1, "list", param);
		return r;
	}
	
	public JsonObject listOtherByParentId(String parentId) throws IOException, URISyntaxException {
		Map<String, String> param = new HashMap<String, String>();
		param.put("mode",  "other");
		param.put("filter",  "{\"parent_id\":\"" + parentId +"\"}");
		param.put("field",  LIST_FIELDS);
		
		JsonObject r = webApi.callApiMethod(API_NAME, 1, "list", param);
		return r;
	}
}
