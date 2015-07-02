package com.nozama.assignment2;

import java.util.UUID;

import com.nozama.orders.IdGenerator;

public class IdGeneratorImpl implements IdGenerator {

	public String next() {
		return UUID.randomUUID().toString();
	}
}
