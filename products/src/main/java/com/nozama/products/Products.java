package com.nozama.products;

import java.util.Collection;
import java.util.List;

import com.nozama.inventory.InventoryItem;

public interface Products {
	public static final String ENDPOINT_LOCATION = "http://localhost:8080/products/ProductService";
	public static final String NAMESPACE = "http://products.nozama.com/";
	public static final String SERVICE = "ProductService";

	Collection<Product> getProducts();

	Product getProduct(long productId);
	
	void addProduct(Product product);

	List<InventoryItem> getInventory();
}
