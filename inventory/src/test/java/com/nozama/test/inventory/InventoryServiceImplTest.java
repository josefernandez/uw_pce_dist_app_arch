package com.nozama.test.inventory;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.nozama.inventory.InventoryService;
import com.nozama.inventory.InventoryServiceImpl;

public class InventoryServiceImplTest {
	private static final String ORDER_1 = "03kmfd-09werf0";
	private static final String ORDER_2 = "adf813jnrf-asdfasd";
	private static final long ITEM_1 = 1L;
	private static final long ITEM_2 = 2L;
	
	private InventoryService serv;
	
	@Before
	public void doBefore() {
		serv = new InventoryServiceImpl();
	}

	@Test
	public void testCannotPlaceAHoldOnTheSameItemTwice() {
		assertTrue(serv.placeHold(ORDER_1, ITEM_1));
		assertFalse(serv.placeHold(ORDER_1, ITEM_1));
		assertFalse(serv.placeHold(ORDER_2, ITEM_1));
	}

	@Test
	public void testCanPlaceHoldOnMultipleItems() {
		assertTrue(serv.placeHold(ORDER_1, ITEM_1));
		assertTrue(serv.placeHold(ORDER_1, ITEM_2));
		assertFalse(serv.placeHold(ORDER_1, ITEM_1));
		assertFalse(serv.placeHold(ORDER_1, ITEM_2));
	}

	@Test
	public void testDifferentOrdersCanPlaceHoldsOnDifferentItems() {
		assertTrue(serv.placeHold(ORDER_1, ITEM_1));
		assertTrue(serv.placeHold(ORDER_2, ITEM_2));
		assertFalse(serv.placeHold(ORDER_1, ITEM_1));
		assertFalse(serv.placeHold(ORDER_2, ITEM_1));
		assertFalse(serv.placeHold(ORDER_1, ITEM_2));
		assertFalse(serv.placeHold(ORDER_2, ITEM_2));
	}

	@Test
	public void testHoldsCanBeCleared() {
		assertTrue(serv.placeHold(ORDER_1, ITEM_1));
		assertFalse(serv.placeHold(ORDER_1, ITEM_1));
		serv.clearHold(ORDER_1, ITEM_1);
		assertTrue(serv.placeHold(ORDER_2, ITEM_1));
		assertFalse(serv.placeHold(ORDER_1, ITEM_1));
		assertFalse(serv.placeHold(ORDER_2, ITEM_1));
	}

	@Test
	public void testSameOrderCanHoldAndClearHoldMultipleTimes() {
		assertTrue(serv.placeHold(ORDER_1, ITEM_1));
		assertFalse(serv.placeHold(ORDER_1, ITEM_1));
		serv.clearHold(ORDER_1, ITEM_1);
		assertTrue(serv.placeHold(ORDER_1, ITEM_1));
		assertFalse(serv.placeHold(ORDER_1, ITEM_1));
		serv.clearHold(ORDER_1, ITEM_1);
		assertTrue(serv.placeHold(ORDER_1, ITEM_1));
		assertFalse(serv.placeHold(ORDER_1, ITEM_1));
	}

	@Test
	public void testClearingAHoldOnlyAffectsThatItem() {
		assertTrue(serv.placeHold(ORDER_1, ITEM_1));
		assertTrue(serv.placeHold(ORDER_1, ITEM_2));
		serv.clearHold(ORDER_1, ITEM_1);
		assertTrue(serv.placeHold(ORDER_2, ITEM_1));
		assertFalse(serv.placeHold(ORDER_1, ITEM_1));
		assertFalse(serv.placeHold(ORDER_2, ITEM_1));
	}

	@Test
	public void testClearingAHoldOnlyAffectsThatOrder() {
		assertTrue(serv.placeHold(ORDER_1, ITEM_1));
		assertTrue(serv.placeHold(ORDER_2, ITEM_2));
		serv.clearHold(ORDER_1, ITEM_1); // does not exist
		assertFalse(serv.placeHold(ORDER_1, ITEM_2));
	}

	@Test
	public void testProcessingAHoldMakesItUnavailable() {
		assertTrue(serv.placeHold(ORDER_1, ITEM_1));
		assertTrue(serv.placeHold(ORDER_1, ITEM_2));
		serv.processHolds(ORDER_1);
		assertFalse(serv.placeHold(ORDER_2, ITEM_1));
		assertFalse(serv.placeHold(ORDER_2, ITEM_2));
	}

	@Test
	public void testProcessingAHoldOnlyAffectsThatOrder() {
		assertTrue(serv.placeHold(ORDER_1, ITEM_1));
		assertTrue(serv.placeHold(ORDER_2, ITEM_2));
		serv.processHolds(ORDER_1);
		serv.clearHold(ORDER_2, ITEM_2);
		assertFalse(serv.placeHold(ORDER_2, ITEM_1));
		assertTrue(serv.placeHold(ORDER_2, ITEM_2));
	}

}
