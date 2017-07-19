package de.csmp.dsmclient.webapi;

public class WebapiQueryResult {
	private String apiName;
	private String path;
	private int minVersion;
	private int maxVersion;
	
	public String getApiName() {
		return apiName;
	}
	public void setApiName(String apiName) {
		this.apiName = apiName;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public int getMinVersion() {
		return minVersion;
	}
	public void setMinVersion(int minVersion) {
		this.minVersion = minVersion;
	}
	public int getMaxVersion() {
		return maxVersion;
	}
	public void setMaxVersion(int maxVersion) {
		this.maxVersion = maxVersion;
	}
}
