package com.pgorecki.pcr.appliedBiosystems;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;

import org.junit.Test;

public class ExperimentDefinitionTest {

	@Test
	public void test() {
		ExperimentDefinition def = new ExperimentDefinition("/home/piotr/workspace/groups");
		assertEquals("Test 007 Zglos sie", def.getExperimentName());
		assertEquals("RPL0", def.getReference());
		assertArrayEquals(new String[] {"130404 RT 1","130404 RT 2","130404 RT 3"}, def.getControllGroup());
		
		String[] expectedSamples = new String[] {"130404 RT 4", "130404 RT 5", "130404 RT 6"};	
		String[] samples = def.getGroupList().iterator().next().get("3h");		
		assertArrayEquals(expectedSamples, samples);
	}

}
