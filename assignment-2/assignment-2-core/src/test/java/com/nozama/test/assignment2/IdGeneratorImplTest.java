package com.nozama.test.assignment2;

import org.junit.Test;

import com.nozama.assignment2.IdGeneratorImpl;
import com.nozama.orders.IdGenerator;

public class IdGeneratorImplTest {

	@Test
	public void testIdsAreUnique() {
		IdGenerator idGen = new IdGeneratorImpl();
		String id1 = idGen.next();
		String id2 = idGen.next();
		String id3 = idGen.next();
		
		assert(id1 != id2);
		assert(id1 != id3);
		assert(id2 != id3);
	}
}
