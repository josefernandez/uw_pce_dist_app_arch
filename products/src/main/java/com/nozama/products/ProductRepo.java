package com.nozama.products;

import java.util.Collection;

public interface ProductRepo {
	Product getProduct(long productId);
	Collection<Product> getProducts();
	Collection<Product> getSomeProducts(int count);
	void put(Product product);
}
