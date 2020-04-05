package com.fp.service.client.testcases;


import org.testng.Assert;
import org.testng.annotations.Test;

import com.fp.service.client.base.FpBase;
import com.fp.service.client.constants.FunctionNames;

import io.restassured.RestAssured;

public class MemberServicesTestSuite extends FpBase {
	
	public static final String GET_OIL_LAYER7_ENDPOINT_URL="/member/routing/v1.0/";
	


	@Test
	public void MemberDetails() throws Exception{

		logger.info("######### Starting ProviderDetails service test #########");
		
		httpRequest = RestAssured.given();
		
		httpRequest.header("Content-Type", "application/json");		
		httpRequest.header("Authorization", token);
		httpRequest.header("actor", "test");
		httpRequest.header("systemId", "OMMS");
		httpRequest.header("functionName", FunctionNames.MEMBER_DETAILS);
		httpRequest.header("scope", "read");
		httpRequest.header("tenantId", "c3281c8f-8731-4b01-9f68-5e0daaeca328");
		httpRequest.header("timestamp", "12345");
		
		httpRequest.body(testDataMap.get(FunctionNames.MEMBER_DETAILS));

		response = httpRequest.post(properties.getProperty("fp.rest.url")+GET_OIL_LAYER7_ENDPOINT_URL);
		
		String responseBody = response.getBody().asString();
		int httpResponseCode = response.getStatusCode();
		
		logger.info("Response body: "+responseBody);
		logger.info("Http response code: "+httpResponseCode);
		
		Assert.assertEquals(httpResponseCode, 200);
		
	}
}