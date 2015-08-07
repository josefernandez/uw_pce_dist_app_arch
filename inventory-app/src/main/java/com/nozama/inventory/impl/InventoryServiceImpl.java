package com.nozama.inventory.impl;

import java.util.*;
import java.util.Map.Entry;

import com.nozama.inventory.*;

/**
 * We default to having only 1 of each item in stock and can only
 * have one of each item per order.
 */
public class InventoryServiceImpl implements InventoryService {
	private Map<Long, Integer> inventory = new HashMap<>();
	private Map<String, ArrayList<Long>> holds = new HashMap<>();
	
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
				ArrayList<Long> l = new ArrayList<>();
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
	
	public List<InventoryItem> getInventory() {
		List<InventoryItem> items = new ArrayList<>(inventory.size());

		for (Entry<Long, Integer> entry : inventory.entrySet()) {
			items.add(new InventoryItem(entry.getKey(), entry.getValue()));
		}

		return items;
	}
	
	public List<Hold> getHolds() {
		List<Hold> holdList = new ArrayList<>(holds.size());

		for (Entry<String, ArrayList<Long>> hold : holds.entrySet()) {
			holdList.add(new Hold(hold.getKey(), hold.getValue()));
		}

		return holdList;
	}
	
	public void nuke() {
		inventory = new HashMap<>();
		holds = new HashMap<>();
	}

}
