package com.nozama.inventory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * We default to having only 1 of each item in stock and can only
 * have one of each item per order.
 */
public class InventoryServiceImpl implements InventoryService {
	private Map<Long, Integer> inventory = new HashMap<>();
	private Map<String, List<Long>> holds = new HashMap<>();
	
	private boolean reduceInventory(long productId) {
		Integer count = inventory.get(productId);
		
		if (count == null) {
			inventory.put(productId, 0);
			return true;
		} else if (count == 0) {
			return false;
		} else if (count == 1) {
			inventory.put(productId, 0);
			return true;
		} else {
			throw new IllegalStateException("Weird inventory count");
		}
	}
	
	public boolean placeHold(String orderId, long productId) {
		if (reduceInventory(productId)) {
			List<Long> orderHolds = holds.get(orderId);
			
			if (orderHolds == null) {
				List<Long> l = new ArrayList<>();
				l.add(productId);
				holds.put(orderId, l);
			} else {
				orderHolds.add(productId);
			}

			return true;
		} else {
			return false;
		}
	}
	
	public void clearHold(String orderId, long productId) {
		List<Long> orderHolds = holds.get(orderId);
		
		if (orderHolds.remove(productId)) {
			inventory.put(productId, 1);
		}
	}
	
	public void processHolds(String orderId) {
		holds.remove(orderId);
	}

}
