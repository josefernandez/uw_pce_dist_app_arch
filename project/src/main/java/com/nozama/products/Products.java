package com.nozama.products;

import java.util.Collection;

public interface Products {
	public static final String ENDPOINT_LOCATION = "http://localhost:8080/products/Products";
	public static final String NAMESPACE = "http://products.nozama.com/";
	public static final String SERVICE = "ProductService";

	Collection<Product> getProducts();

	Product getProduct(long productId);
	
	void addProduct(Product product);
}
