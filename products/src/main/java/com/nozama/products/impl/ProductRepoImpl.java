package com.nozama.products.impl;

import java.util.*;

import com.nozama.products.Product;
import com.nozama.products.ProductRepo;

public class ProductRepoImpl implements ProductRepo {
	private static Map<Long, Product> products = new HashMap<>(10);
	
	static {
		products.put(1L, new Product(1L, "Purple Unicorn"));
		products.put(2L, new Product(2L, "Purple Rain"));
		products.put(3L, new Product(3L, "Barney the Purple Dinosaur"));
		products.put(4L, new Product(4L, "Hello Kitty toy"));
		products.put(5L, new Product(5L, "Hello Kitty #11"));
		products.put(6L, new Product(6L, "The Cat Came Back"));
		products.put(7L, new Product(7L, "Back In Black"));
		products.put(8L, new Product(8L, "Black Hole Sun"));
		products.put(9L, new Product(9L, "G.I. Joe"));
	}

	@Override
	public Product getProduct(long productId) {
		return products.get(productId);
	}

	@Override
	public Collection<Product> getProducts() {
		return products.values();
	}

	@Override
	public Collection<Product> getSomeProducts(int count) {
		List<Product> trimmedProducts = new ArrayList<Product>((int) count);

		for (long i = 1L; (i <= products.size() && i <= count); i++) {
			trimmedProducts.add(products.get(i));
		}
		
		return trimmedProducts;
	}

	@Override
	public void put(Product product) {
		products.put(product.getId(), product);
	}

}
