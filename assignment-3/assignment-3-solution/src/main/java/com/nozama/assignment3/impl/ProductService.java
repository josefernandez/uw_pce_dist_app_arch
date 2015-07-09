package com.nozama.assignment3.impl;

import java.util.Collection;

import com.nozama.products.Product;
import com.nozama.products.Products;

public interface ProductService extends Products {

	Collection<Product> getProducts();

	Product getProduct(long productId);
	
	void addProduct(Product product);

}
