package me.rahul.input.data.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

public class TrieDictionaryImplTest {

	TrieDictionaryImpl trieDictionaryImpl;

	@Test
	public void testAddWord() {
		trieDictionaryImpl = new TrieDictionaryImpl();
		trieDictionaryImpl.addWord("Pa");
		assertEquals(1, trieDictionaryImpl.getRoot().getChildren().size());
		assertNotNull(trieDictionaryImpl.getRoot().getChild('P'));
		assertEquals(1, trieDictionaryImpl.getRoot().getChild('P').getChildren().size());
		assertNotNull(trieDictionaryImpl.getRoot().getChild('P').getChild('a'));
		assertEquals(1, trieDictionaryImpl.getRoot().getChild('P').getChild('a').getChildren().size());
		assertNotNull(trieDictionaryImpl.getRoot().getChild('P').getChild('a').getChild('$'));
		assertEquals("Pa", trieDictionaryImpl.getRoot().getChild('P').getChild('a').getChild('$').getWord());
	}

}
