package com.fastaccounting.controller;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

class CodingTestCase {

	@Test
	void test() {
		fail("Not yet implemented");
	}

	@Test
	void checkAPIConnect() {
		final String apiUrl = "https://api-gt01-sandbox.fastaccounting.jp/";
		final String apiKey = "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJmYXN0YWNjb3VudGluZy5hcGkudG9rZW4iLCJhdWQiOiJmYXN0YWNjb3VudGluZy5hcGkudG9rZW4iLCJqdGkiOiIwOTFhMzhhODc3OTZiY2FhYmRhZDI3MDA4YzMxMzE5NWRkOWU1ODZjZTU3ODZkMTdmYzgwN2RiOTYyMDI4YWRlIiwiaWF0IjoxNjY4MDY5MTAxLCJpZGVudGlmaWVyIjoyMjI2fQ.DQL42w01IwveWouxQINN-Dz-OGsyi2raGRUFyVu-lvI";
		
		try {
			Unirest.setTimeouts(0, 0);
			HttpResponse<String> response = Unirest.get(apiUrl)
				.header("Authorization", apiKey)
				.asString();
			System.out.println(response.getStatus());
		} catch (UnirestException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
