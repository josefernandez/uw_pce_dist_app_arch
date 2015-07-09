package com.nozama.tests.assignment3;

import static org.junit.Assert.assertEquals;

import java.net.MalformedURLException;
import java.util.List;

import javax.xml.ws.Endpoint;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.nozama.assignment3.impl.ProductServiceImpl;
import com.nozama.products.Product;
import com.nozama.products.Products;
import com.nozama.tests.assignment3.ProductServiceClient;

public class ProductsIntegrationTest {
	private static Endpoint ep;

	@BeforeClass
	public static void setUp() {
		ep = Endpoint.publish(Products.ENDPOINT_LOCATION, new ProductServiceImpl());
	}
	
	@AfterClass
	public static void tearDown() {
		ep.stop();
	}

	@Test
	public void testProductsCanBeRetrieved() throws MalformedURLException {
		ProductServiceClient client = new ProductServiceClient();
		final long ID = 3L;
		Product p = client.getProduct(ID);
		assertEquals(ID, p.getId());
	}

	@Test
	public void testAllProductsCanBeRetrieved() throws MalformedURLException {
		ProductServiceClient client = new ProductServiceClient();
		List<Product> products = client.getProducts();
		assertEquals(9, products.size());
	}

	@Test
	public void testProductsCanBeAdded() throws MalformedURLException {
		final long ID = 1324L;
		Product newProduct = new Product(ID, "Test Product");

		ProductServiceClient client = new ProductServiceClient();
		client.addProduct(newProduct);

		Product p = client.getProduct(ID);
		assertEquals(ID, p.getId());

		List<Product> products = client.getProducts();
		assertEquals(10, products.size());
	}

}
