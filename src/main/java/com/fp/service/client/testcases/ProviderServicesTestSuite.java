package com.fp.service.client.testcases;


import org.testng.Assert;
import org.testng.annotations.Test;

import com.fp.service.client.base.FpBase;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

public class ProviderServicesTestSuite extends FpBase {
	
	public static final String GET_FP_ENDPOINT_URL="/fponabse/v1";
	
	@Test
	public void ProviderDetails() throws Exception{

		logger.info("######### Starting ProviderDetails service test #########");
		RestAssured.baseURI = "https://gateway-stage.optum.com/app/stage";
		httpRequest = RestAssured.given();
		httpRequest.contentType(ContentType.JSON);
		//httpRequest.header("Content-Type", "application/json");	
		httpRequest.header("Authorization", token);
		
		httpRequest.queryParam("PolicyId", "G512345");
		httpRequest.queryParam("LastName", "1939-01-02");
		httpRequest.queryParam("SSNLast4", "2229");

		response = httpRequest.get(GET_FP_ENDPOINT_URL);
		
		String responseBody = response.getBody().asString();
		int httpResponseCode = response.getStatusCode();
		
		logger.info("Response body: "+responseBody);
		logger.info("Http response code: "+httpResponseCode);
		
		Assert.assertEquals(httpResponseCode, 200);
		
	}
}