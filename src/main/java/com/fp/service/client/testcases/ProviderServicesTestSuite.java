package com.fp.service.client.testcases;


import static io.restassured.RestAssured.given;

import org.testng.annotations.Test;

import com.fp.service.client.base.FpBase;

import io.restassured.http.ContentType;

public class ProviderServicesTestSuite extends FpBase {
	
	public static final String GET_FP_ENDPOINT_URL="/fponabse/v1";
	
	@Test
	public void ProviderDetails() throws Exception{

		logger.info("######### Starting ProviderDetails service test #########");
		
		given()
	    .contentType(ContentType.JSON)
		.header("Authorization", token)
		.queryParam("PolicyId", "G512345")
		.queryParam("LastName", "1939-01-02")
		.queryParam("SSNLast4", "2229")
		.when()
			.get("https://gateway-stage.optum.com/app/stage"+GET_FP_ENDPOINT_URL)
		.then()
			.statusCode(200)
		.log()
		.all();
	}
}