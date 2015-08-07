package com.nozama.test.products;

import static org.junit.Assert.assertEquals;

import java.util.Collection;

import org.junit.Before;
import org.junit.Test;

import com.nozama.products.Product;
import com.nozama.products.ProductRepo;
import com.nozama.products.impl.ProductRepoImpl;

public class ProductRepoImplTest {
	private ProductRepo repo;
	
	@Before
	public void doBefore() {
		repo = new ProductRepoImpl();
	}

	@Test
	public void testProductCanBeRetrieved() {
		final long productId = 2L;
		Product p = repo.getProduct(productId);
		
		assertEquals(productId, p.getId());
	}

	@Test
	public void testAllProductsCanBeRetrieved() {
		Collection<Product> all = repo.getProducts();
		
		assertEquals(9, all.size());
	}

	@Test
	public void testASubsetOfProductsCanBeRetrieved() {
		final int count = 4;
		Collection<Product> subset = repo.getSomeProducts(count);
		
		assertEquals(count, subset.size());
	}

	@Test
	public void testProductsCanBeAdded() {
		final long id = 333L;
		final String name = "new product";
		
		Product p = new Product(id, name);
		repo.put(p);
		
		Product testProduct = repo.getProduct(id);
		assertEquals(id, testProduct.getId());
		assertEquals(name, testProduct.getName());

		Collection<Product> all = repo.getProducts();
		assertEquals(10, all.size());
	}

}
