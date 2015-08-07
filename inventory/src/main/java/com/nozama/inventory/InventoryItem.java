package com.nozama.inventory;

import java.io.Serializable;

public class InventoryItem implements Serializable {
	private static final long serialVersionUID = 2279327389520451679L;

	private long item;
	private int quantity;
	
	public InventoryItem() {
		// do nothing
	}
	
	public InventoryItem(long item, int quantity) {
		this.item = item;
		this.quantity = quantity;
	}

	public void setItem(long item) {
		this.item = item;
	}

	public long getItem() {
		return item;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public int getQuantity() {
		return quantity;
	}

}
