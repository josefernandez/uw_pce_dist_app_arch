package com.nozama.test.core;

import static org.junit.Assert.*;

import org.junit.Test;

import com.nozama.core.IdGenerator;
import com.nozama.core.impl.IdGeneratorImpl;

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
