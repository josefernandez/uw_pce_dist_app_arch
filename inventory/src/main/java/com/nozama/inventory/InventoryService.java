package com.nozama.inventory;

import java.util.List;

public interface InventoryService {
	boolean placeHold(String orderId, long productId);
	void clearHold(String orderId, long productId);
	void processHolds(String orderId);
	List<InventoryItem> getInventory();
	List<Hold> getHolds();
	void nuke();
}
