package me.rahul.input.impl;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import me.rahul.input.data.impl.TrieDictionaryImpl;
import me.rahul.input.search.impl.DefaultTrieSearchStrategy;

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
		
		DefaultTrieSearchStrategy strategy = new DefaultTrieSearchStrategy(dict);
		List<String> res = strategy.getMatches("55");
		System.out.println(res);
		
		fail("Not yet implemented");
	}

}
