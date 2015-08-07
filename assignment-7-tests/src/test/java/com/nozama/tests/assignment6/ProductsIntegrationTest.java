package com.nozama.tests.assignment6;

import java.net.MalformedURLException;

import javax.ws.rs.core.Response.Status;

import org.junit.Before;
import org.junit.Test;

import com.nozama.inventory.InventoryItem;
import com.nozama.products.ProductServiceClient;

public class ProductsIntegrationTest extends OrderCalls {
	private ProductServiceClient client = getClient();
	
	private ProductServiceClient getClient() {
		try {
			return new ProductServiceClient();
		} catch (MalformedURLException e) {
			throw new RuntimeException(e);
		}
	}
	
	@Before
	public void setUp() {
		nuke();
	}
	
	@Test
	public void testInventoryCanBeRetrieved() throws MalformedURLException {
		System.out.println("-------------------------------------");
		long item1 = 1L;
		long item2 = 2L;
		long item3 = 3L;
		long item4 = 4L;
		
		String order = createOrder();
		addItemToOrder(order, item1, Status.NO_CONTENT);
		addItemToOrder(order, item2, Status.NO_CONTENT);
		addItemToOrder(order, item3, Status.NO_CONTENT);
		addItemToOrder(order, item4, Status.NO_CONTENT);
		
		boolean item1found = false;
		boolean item2found = false;
		boolean item3found = false;
		boolean item4found = false;

		for (InventoryItem item : client.getInventory()) {
			if (item.getItem() == item1) {
				item1found = true;
			} else if (item.getItem() == item2) {
				item2found = true;
			} else if (item.getItem() == item3) {
				item3found = true;
			} else if (item.getItem() == item4) {
				item4found = true;
			}
		}
		
		assert(item1found);
		assert(item2found);
		assert(item3found);
		assert(item4found);
	}

}
