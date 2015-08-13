package com.nozama.search.impl;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.List;

import org.junit.Test;

import com.nozama.products.Product;
import com.nozama.products.ProductRepo;
import com.nozama.products.impl.ProductRepoImpl;
import com.nozama.search.Searcher;

public class SearcherImplTest {
	private ProductRepo repo = new ProductRepoImpl();
	
	private Searcher getSearcher() throws IOException {
		Searcher s = new SearcherBuilder().build();
		s.index(repo.getProducts());
		return s;
	}

	@Test
	public void testNoResultsFound() throws IOException {
		Searcher s = getSearcher();
		List<Product> products = s.doQuery("Blah");
		assertEquals(0, products.size());
	}

	@Test
	public void testSomeResultsFound() throws IOException {
		Searcher s = getSearcher();
		List<Product> products = s.doQuery("Black");
		assertEquals(2, products.size());
	}

	@Test
	public void testSearchForStopword() throws IOException {
		Searcher s = getSearcher();
		List<Product> products = s.doQuery("and");
		assertEquals(0, products.size());
	}

}
