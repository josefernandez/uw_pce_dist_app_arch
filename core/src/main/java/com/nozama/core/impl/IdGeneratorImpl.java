package com.nozama.core.impl;

import java.util.UUID;

import com.nozama.core.IdGenerator;

public class IdGeneratorImpl implements IdGenerator {

	public String next() {
		return UUID.randomUUID().toString();
	}
}
