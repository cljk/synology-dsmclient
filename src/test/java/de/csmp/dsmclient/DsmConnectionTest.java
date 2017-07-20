package de.csmp.dsmclient;

import org.apache.http.client.utils.URIBuilder;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


public class DsmConnectionTest {

	DsmConnection conn;
	
	@Before
	public void setup() {
		conn = new DsmConnection("127.0.0.1", 5001);
	}
	
	@Test
	public void test_getUriBuilder() throws Exception {
		URIBuilder b = conn.getUriBuilder();
		String url = b.build().toString();
		
		Assert.assertEquals("https://127.0.0.1:5001", url);
	}
}
