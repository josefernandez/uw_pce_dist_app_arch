package com.nozama.tests.assignment2;

import static com.jayway.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

import org.junit.Test;

import com.jayway.restassured.response.Response;

public class OrderResourceIntegrationTest {

	@Test
	public void testOrderCanBeRetrieved() {
		Response r = post("/rest/order");
		
		assertEquals(r.getStatusCode(), 200);
		
		String id = r.getBody().asString();
		
		expect().
			statusCode(200).
			body(
				"id", equalTo(id),
				"items", hasSize(0)).
		when().
			get("/rest/order/" + id);
	}

	@Test
	public void testOrdersThatDoNotExistReturn404() {
		expect().
			statusCode(404).
		when().
			get("/rest/order/fake-id");
	}

	@Test
	public void testOrderCanBeDeleted() {
		Response r = post("/rest/order");
		String id = r.getBody().asString();

		expect().
			statusCode(202).
		when().
			delete("/rest/order/" + id);
	}

	@Test
	public void testOrdersThatDoNotExistReturn404WhenDeleted() {
		expect().
			statusCode(404).
		when().
			delete("/rest/order/fake-id");
	}

	@Test
	public void testItemsCanBeAddedToOrders() {
		Response r = post("/rest/order");
		String id = r.getBody().asString();
		
		long item1 = 21234L;
		long item2 = 7626534L;
		
		expect().
			statusCode(204).
		when().
			put("/rest/order/" + id + "/item/" + item1);
		
		expect().
			statusCode(204).
		when().
			put("/rest/order/" + id + "/item/" + item2);
		
		expect().
			statusCode(200).
			body(
				"id", equalTo(id),
				"items", hasSize(2)).
		when().
			get("/rest/order/" + id);
	}

	@Test
	public void testItemsAddedToOrderThatDoesNotExistReturns404() {
		expect().
			statusCode(404).
		when().
			put("/rest/order/does-not-exist/item/23423");
	}

	@Test
	public void testItemsCanBeRemovedFromOrders() {
		Response r = post("/rest/order");
		String id = r.getBody().asString();
		
		long item = 21234L;
		
		expect().
			statusCode(204).
		when().
			put("/rest/order/" + id + "/item/" + item);
		
		expect().
			statusCode(204).
		when().
			delete("/rest/order/" + id + "/item/" + item);
		
		expect().
			statusCode(200).
			body(
				"id", equalTo(id),
				"items", hasSize(0)).
		when().
			get("/rest/order/" + id);
	}

	@Test
	public void testItemsRemovedFromOrderThatDoesNotExistReturns404() {
		expect().
			statusCode(404).
		when().
			delete("/rest/order/does-not-exist/item/23423");
	}

	@Test
	public void testItemsThatDoNotExistCannotBeRemovedFromOrders() {
		Response r = post("/rest/order");
		String id = r.getBody().asString();
		
		long item = 21234L;
		
		expect().
			statusCode(204).
		when().
			put("/rest/order/" + id + "/item/" + item);
		
		expect().
			statusCode(409).
		when().
			delete("/rest/order/" + id + "/item/666666");
		
		expect().
			statusCode(200).
			body(
				"id", equalTo(id),
				"items", hasSize(1)).
		when().
			get("/rest/order/" + id);
	}

}
