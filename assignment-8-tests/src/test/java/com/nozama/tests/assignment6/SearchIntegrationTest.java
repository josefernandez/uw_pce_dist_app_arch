package com.nozama.tests.assignment6;

import static com.jayway.restassured.RestAssured.*;
import static org.junit.Assert.*;

import org.junit.Test;

import com.nozama.products.Product;

public class SearchIntegrationTest {
	private final String REST_ENDPOINT = "rest/search";
	
	private Product p1 = new Product(1L, "Purple Unicorn");
	private Product p2 = new Product(2L, "Purple Rain");
	private Product p3 = new Product(3L, "Barney the Mauve Dinosaur");
	private Product p4 = new Product(4L, "Hello Kitty toy");
	private Product p5 = new Product(5L, "Hello Kitty #11");
	private Product p6 = new Product(6L, "The Cat Came Back");
	private Product p7 = new Product(7L, "Back In Black");
	private Product p8 = new Product(8L, "Black Hole Sun");
	private Product p9 = new Product(9L, "G.I. Joe");
	private Product p10 = new Product(10L, "Land of the Dinosaurs");
	private Product p11 = new Product(11L, "Exposé");
	private Product p12 = new Product(12L, "Willy Exposes the Truth!");
	private Product p13 = new Product(13L, "The Landing of Immigrants");

	@Test
	public void testJoe() {
		//	products.put(9L, new Product(9L, "G.I. Joe"));

		Product[] products = get(REST_ENDPOINT + "?query=joe").as(Product[].class);
		
		assertArrayEquals(new Product[]{p9}, products);
	}

	@Test
	public void testBlackUpperCase() {
		//products.put(7L, new Product(7L, "Back In Black"));
		//products.put(8L, new Product(8L, "Black Hole Sun"));

		Product[] products = get(REST_ENDPOINT + "?query=Black").as(Product[].class);
		
		assertArrayEquals(new Product[]{p7, p8}, products);
	}

	@Test
	public void testBlackLowerCase() {
		//products.put(7L, new Product(7L, "Back In Black"));
		//products.put(8L, new Product(8L, "Black Hole Sun"));

		Product[] products = get(REST_ENDPOINT + "?query=black").as(Product[].class);
		
		assertArrayEquals(new Product[]{p7, p8}, products);
	}

	@Test
	public void testPurple() {
		//	products.put(1L, new Product(1L, "Purple Unicorn"));
		//	products.put(2L, new Product(2L, "Purple Rain"));
		//	products.put(3L, new Product(3L, "Barney the Mauve Dinosaur"));

		Product[] products = get(REST_ENDPOINT + "?query=purple").as(Product[].class);
		
		assertArrayEquals(new Product[]{p1, p2, p3}, products);
	}

	@Test
	public void testMauve() {
		//	products.put(1L, new Product(1L, "Purple Unicorn"));
		//	products.put(2L, new Product(2L, "Purple Rain"));
		//	products.put(3L, new Product(3L, "Barney the Mauve Dinosaur"));

		Product[] products = get(REST_ENDPOINT + "?query=mauve").as(Product[].class);
		
		assertArrayEquals(new Product[]{p1, p2, p3}, products);
	}

	@Test
	public void testDinosaur() {
		//	products.put(3L, new Product(3L, "Barney the Mauve Dinosaur"));
		//	products.put(10L, new Product(10L, "Land of the Dinosaurs"));

		Product[] products = get(REST_ENDPOINT + "?query=dinosaur").as(Product[].class);
		
		assertArrayEquals(new Product[]{p3, p10}, products);
	}

	@Test
	public void testDinosaurs() {
		//	products.put(3L, new Product(3L, "Barney the Mauve Dinosaur"));
		//	products.put(10L, new Product(10L, "Land of the Dinosaurs"));

		Product[] products = get(REST_ENDPOINT + "?query=dinosaurs").as(Product[].class);
		
		assertArrayEquals(new Product[]{p3, p10}, products);
	}
	
	@Test
	public void testKitty() {
		//	products.put(4L, new Product(4L, "Hello Kitty toy"));
		//	products.put(5L, new Product(5L, "Hello Kitty #11"));
		//	products.put(6L, new Product(6L, "The Cat Came Back"));

		Product[] products = get(REST_ENDPOINT + "?query=kitty").as(Product[].class);
		
		assertArrayEquals(new Product[]{p4, p5, p6}, products);
	}
	
	@Test
	public void testKittyToy() {
		//	products.put(4L, new Product(4L, "Hello Kitty toy"));
		//	products.put(5L, new Product(5L, "Hello Kitty #11"));
		//	products.put(6L, new Product(6L, "The Cat Came Back"));

		Product[] products = get(REST_ENDPOINT + "?query=kitty toy").as(Product[].class);
		
		assertArrayEquals(new Product[]{p4, p5, p6}, products);
	}
	
	@Test
	public void testToy() {
		//	nothing!

		Product[] products = get(REST_ENDPOINT + "?query=toy").as(Product[].class);
		
		assertEquals(0, products.length);
	}
	
	@Test
	public void testCat() {
		//	products.put(4L, new Product(4L, "Hello Kitty toy"));
		//	products.put(5L, new Product(5L, "Hello Kitty #11"));
		//	products.put(6L, new Product(6L, "The Cat Came Back"));

		Product[] products = get(REST_ENDPOINT + "?query=cat").as(Product[].class);
		
		assertArrayEquals(new Product[]{p6, p4, p5}, products);
	}
	
	@Test
	public void testLand() {
		//	products.put(10L, new Product(10L, "Land of the Dinosaurs"));
		//	products.put(13L, new Product(13L, "The Landing of Immigrants"));

		Product[] products = get(REST_ENDPOINT + "?query=land").as(Product[].class);
		
		assertArrayEquals(new Product[]{p10, p13}, products);
	}
	
	@Test
	public void testLandOfThe() {
		//	products.put(10L, new Product(10L, "Land of the Dinosaurs"));
		//	products.put(13L, new Product(13L, "The Landing of Immigrants"));

		Product[] products = get(REST_ENDPOINT + "?query=land").as(Product[].class);
		
		assertArrayEquals(new Product[]{p10, p13}, products);
	}

	@Test
	public void testExpose() {
		//	products.put(11L, new Product(11L, "Exposé"));
		//	products.put(12L, new Product(12L, "Willy Exposes the Truth!"));

		Product[] products = get(REST_ENDPOINT + "?query=expose").as(Product[].class);
		
		assertArrayEquals(new Product[]{p11, p12}, products);
	}

}
