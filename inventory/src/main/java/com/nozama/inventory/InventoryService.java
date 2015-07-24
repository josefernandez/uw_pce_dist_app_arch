package com.nozama.inventory;

public interface InventoryService {
	boolean placeHold(String orderId, long productId);
	void clearHold(String orderId, long productId);
	void processHolds(String orderId);
}
