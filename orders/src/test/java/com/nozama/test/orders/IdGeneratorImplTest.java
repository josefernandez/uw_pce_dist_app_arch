package com.nozama.test.orders;

import static org.junit.Assert.assertNotEquals;

import org.junit.Test;

import com.nozama.orders.IdGenerator;
import com.nozama.orders.impl.IdGeneratorImpl;

public class IdGeneratorImplTest {

	@Test
	public void testIdsAreUnique() {
		IdGenerator idGen = new IdGeneratorImpl();
		String id1 = idGen.next();
		String id2 = idGen.next();
		String id3 = idGen.next();
		
		assertNotEquals(id1, id2);
		assertNotEquals(id1, id3);
		assertNotEquals(id2, id3);
	}
}
