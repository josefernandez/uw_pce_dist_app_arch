package com.nozama.test.orders;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.nozama.core.impl.IdGeneratorImpl;
import com.nozama.orders.*;
import com.nozama.orders.impl.OrderServiceImpl;

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
		
		assertNotEquals(o1.getId(), o2.getId());
		assertNotEquals(o1.getId(), o3.getId());
		assertNotEquals(o2.getId(), o3.getId());
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
