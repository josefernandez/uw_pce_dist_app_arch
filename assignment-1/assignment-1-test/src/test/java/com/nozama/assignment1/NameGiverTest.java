package com.nozama.assignment1;

import static org.junit.Assert.assertNotNull;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.UnknownExtensionTypeException;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.nozama.assignment1.impl.NameGiverImpl;

@RunWith(Arquillian.class)
public class NameGiverTest {
	
	@Deployment
	public static JavaArchive createDeployment() 
			throws IllegalArgumentException, UnknownExtensionTypeException, ClassNotFoundException {
		return ShrinkWrap.create(JavaArchive.class)
				.addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml")
				.addClasses(NameGiver.class, NameGiverImpl.class);
	}

	@Test
	public void testNameIsValid() throws InstantiationException, IllegalAccessException {
		NameGiver namer = new NameGiverImpl();
		assertNotNull(namer.getName());

		System.out.println();
		System.out.println("*************** " + namer.getName() + " ***************");
		System.out.println();
	}

}
