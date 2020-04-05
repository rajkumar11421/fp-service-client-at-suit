package com.fp.service.client.base;

import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Parameters;

import com.fp.service.client.util.XLUtils;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public abstract class FpBase {

	public RequestSpecification httpRequest;
	public Response response;
	
	public static Properties properties;
	public static String env;
	public static String token;
	
	public static Map<String, String> testDataMap;
	
	public Logger logger = Logger.getLogger(getClass());

	/**
	 * This method is responsible for defining common things and loading test data. It runs based on selection of environment 
	 * @param environment
	 * @throws Exception
	 * @author akuma189
	 */
	@BeforeSuite(alwaysRun = true)
	@Parameters({"environment"})
	public void setupSuite(String environment) throws Exception {

		InputStream inputStream = null;

		try {

			env = environment;
			
			//reading service credentials and end point info
			properties = new Properties();
			inputStream = getClass().getResourceAsStream("/client-service.properties");
			properties.load(inputStream);

			List<String> userTypes = Arrays.asList(properties.getProperty("fp.userTypes").split(","));
			
			//testDataMap = XLUtils.readDataFromXLFile("testData/fpTestData-"+env.toUpperCase()+".xlsx", userTypes);
			
			//generating service token
			token = this.getToken();
			logger.info("Token :::::::::::::::::: "+ token);

			
		} catch (Exception e) {
			logger.error("error getting while loading client-service.properites file, env="+env, e);
		}finally{
			if(inputStream != null){
				inputStream.close();
			}
		}
	}
	
	/**
	 * This private method is used to get service token and same is used for entire testing
	 * @return String
	 * @author akuma189
	 */
	private String getToken(){

		String token = null;
		
		httpRequest = RestAssured.given();
		
		httpRequest.auth().preemptive().basic(properties.getProperty("fp.rest.client.id"), properties.getProperty("fp.rest.client.secret"));
		httpRequest.contentType("application/x-www-form-urlencoded");
		httpRequest.formParam("grant_type", "client_credentials");
		response = httpRequest.post(properties.getProperty("fp.token.url")); 
		
		if(response.jsonPath().get("token_type") != null && response.jsonPath().get("access_token") != null){
			token = response.jsonPath().get("token_type")+" "+response.jsonPath().get("access_token");
		}
		
		return token;
		
	}

}
