package com.nozama.test.assignment2;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.nozama.assignment2.IdGeneratorImpl;
import com.nozama.assignment2.OrderServiceImpl;
import com.nozama.orders.Order;
import com.nozama.orders.OrderDoesNotExistException;
import com.nozama.orders.OrderService;

public class OrderServiceImplTest {
	private OrderService serv;
	
	@Before
	public void doBefore() {
		serv = new OrderServiceImpl(new IdGeneratorImpl());
	}

	@Test
	public void testOrderIdsAreUnique() {
		Order o1 = serv.create();
		Order o2 = serv.create();
		Order o3 = serv.create();
		
		assert(o1.getId() != o2.getId());
		assert(o1.getId() != o3.getId());
		assert(o2.getId() != o3.getId());
	}

	@Test
	public void testAllOrdersAreRetrievable() {
		Order o1 = serv.create();
		Order o2 = serv.create();
		Order o3 = serv.create();
		
		assertNotNull(serv.get(o1.getId()));
		assertNotNull(serv.get(o2.getId()));
		assertNotNull(serv.get(o3.getId()));
		assertNull(serv.get("bad id"));
	}

	@Test
	public void testOrdersCannotBeRetrievedAfterTheyAreSubmitted() {
		Order o = serv.create();
		serv.submit(o);
		assertNull(serv.get(o.getId()));
	}

	@Test(expected = OrderDoesNotExistException.class)
	public void testExceptionThrownWhenOrderSubmittedButDoesNotExist() {
		Order o = new Order("my id");
		serv.submit(o);
	}

	@Test
	public void testOrdersCanBeDeleted() {
		Order o = serv.create();
		assertTrue(serv.delete(o.getId()));
		assertNull(serv.get(o.getId()));
	}

	@Test
	public void testOrdersThatDoNotExistReturnFalseWhenDeleted() {
		assertFalse(serv.delete("my id"));
	}

}
