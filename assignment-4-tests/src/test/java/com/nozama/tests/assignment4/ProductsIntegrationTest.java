package com.nozama.tests.assignment4;

import static org.junit.Assert.assertEquals;

import java.net.MalformedURLException;
import java.util.List;

import org.junit.Test;

import com.nozama.products.Product;
import com.nozama.products.ProductServiceClient;

public class ProductsIntegrationTest {
	
	// CHANGE THIS TO MATCH WHAT YOU SET IN THE RESOURCE 'app/Products/maxProducts'
	
	private final int maxProducts = 4;

	@Test
	public void testSomeProductsCanBeRetrieved() throws MalformedURLException {
		ProductServiceClient client = new ProductServiceClient();
		List<Product> products = client.getSomeProducts();
		assertEquals(maxProducts, products.size());
	}

}
