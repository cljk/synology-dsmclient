package de.csmp.dsmclient.webapi;

public class Office {
	
	WebApi webApi;
	
	public Office(WebApi webApi) {
		this.webApi = webApi;
	}

	public OfficeNode getNode() {
		return new OfficeNode(webApi);
	}
	
}
