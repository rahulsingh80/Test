package me.rahul.input.impl;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class TrieDictionaryImplTest {

	@Mock
	private TrieDictionaryImpl dict;

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testAddWord() {
		
	}

	@Test
	public void test() {
		TrieDictionaryImpl dict = new TrieDictionaryImpl();
		dict.addWord("Pa");
		dict.addWord("Papa");
		dict.addWord("Mama");
		dict.addWord("Ma");
		fail("Not yet implemented");
	}

}
