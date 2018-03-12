package me.rahul.input.search.impl;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import me.rahul.input.data.impl.TrieDictionaryImpl;

public class DefaultTriePartialEncodingSearchStrategyTest {

	TrieDictionaryImpl dictionary;
	final Map<Character, List<Character>> mapping = new HashMap<>();

	@Before
	public void setup() {
		dictionary = new TrieDictionaryImpl();
		mapping.put('0', Arrays.asList('E', 'e'));
		mapping.put('1', Arrays.asList('J', 'N', 'Q', 'j', 'n', 'q'));
		mapping.put('2', Arrays.asList('R', 'W', 'X', 'r', 'w', 'x'));
		mapping.put('3', Arrays.asList('D', 'S', 'Y', 'd', 's', 'y'));
		mapping.put('4', Arrays.asList('F', 'T', 'f', 't'));
		mapping.put('5', Arrays.asList('A', 'M', 'a', 'm'));
		mapping.put('6', Arrays.asList('C', 'I', 'V', 'c', 'i', 'v'));
		mapping.put('7', Arrays.asList('B', 'K', 'U', 'b', 'k', 'u'));
		mapping.put('8', Arrays.asList('L', 'O', 'P', 'l', 'o', 'p'));
		mapping.put('9', Arrays.asList('G', 'H', 'Z', 'g', 'h', 'z'));
	}

	@Test
	public void testGetMatches() {
		dictionary = new TrieDictionaryImpl();
		dictionary.addWord("Rahul");
		DefaultTriePartialEncodingSearchStrategy strategy = new DefaultTriePartialEncodingSearchStrategy(dictionary, mapping);
		assertTrue(strategy.getMatches("25978").contains("Rahul"));
		assertEquals(0, strategy.getMatches("").size());
	}

	@Test
	public void testGetMatchesEmptyDictionary() {
		dictionary = new TrieDictionaryImpl();
		DefaultTriePartialEncodingSearchStrategy strategy = new DefaultTriePartialEncodingSearchStrategy(dictionary, mapping);
		assertFalse(strategy.getMatches("25978").contains("Rahul"));
		assertEquals(0, strategy.getMatches("").size());
	}

	@Test
	public void testGetMatchesOverlappingWords() {
		dictionary = new TrieDictionaryImpl();
		dictionary.addWord("Papa");
		dictionary.addWord("Pa");
		DefaultTriePartialEncodingSearchStrategy strategy = new DefaultTriePartialEncodingSearchStrategy(dictionary, mapping);
		assertTrue(strategy.getMatches("85").contains("Pa"));
		assertEquals(1, strategy.getMatches("85").size());
		assertTrue(strategy.getMatches("8585").contains("Papa"));
		assertTrue(strategy.getMatches("8585").contains("Pa Pa"));
		assertEquals(2, strategy.getMatches("8585").size());
	}

	@Test
	public void testGetMatchesTwiceWithDigitEncoding() {
		dictionary = new TrieDictionaryImpl();
		dictionary.addWord("e");
		DefaultTriePartialEncodingSearchStrategy strategy = new DefaultTriePartialEncodingSearchStrategy(dictionary, mapping);
		assertTrue(strategy.getMatches("10").contains("1 e"));
		assertTrue(strategy.getMatches("10").contains("1 e"));
	}

	@Test
	public void testDashesSlashesAndSpaces() {
		dictionary = new TrieDictionaryImpl();
		/*List<String> words = Arrays.asList("Jagdgefa\"hrten","Jagdgenossenschaft","Jagdgera\"t","Jagdgeschwaders","Jagdgesetz","Jagdgewehr","jagdgru\"n","Jagdgru\"nde","Jagdhaus","Jagdha\"user","Jagdhund","Jagdhu\"tte");
		for (String word : words)
			dictionary.addWord(word);*/
		dictionary.addWord("a");
		dictionary.addWord("ab");
		dictionary.addWord("abc");
		dictionary.addWord("w\"xyz");
		dictionary.addWord("x\"yz");
		dictionary.addWord("yz");
		dictionary.addWord("\"\"\"z\"\"");
		DefaultTriePartialEncodingSearchStrategy strategy = new DefaultTriePartialEncodingSearchStrategy(dictionary, mapping);
		assertTrue(strategy.getMatches("--5").contains("a"));
		assertTrue(strategy.getMatches("5").contains("a"));
		assertTrue(strategy.getMatches("-/-5/--").contains("a"));
		assertTrue(strategy.getMatches("--5/7--/").contains("ab"));
		assertTrue(strategy.getMatches("239").contains("x\"yz"));
		assertEquals(0, strategy.getMatches("23").size());
		assertTrue(strategy.getMatches("/23/-9--4").contains("x\"yz 4"));
		assertTrue(strategy.getMatches("9").contains("\"\"\"z\"\""));
		assertEquals(0, strategy.getMatches("234").size());
		assertTrue(strategy.getMatches("--57--8-").contains("ab 8"));
		assertTrue(strategy.getMatches("--57--9-").contains("ab \"\"\"z\"\""));
		assertEquals(0, strategy.getMatches("---///-").size());
		
	}

	@Test
	public void testSpecificCase_1() {
		dictionary = new TrieDictionaryImpl();
		dictionary.addWord("Mai");
		DefaultTriePartialEncodingSearchStrategy strategy = new DefaultTriePartialEncodingSearchStrategy(dictionary, mapping);
		assertTrue(strategy.getMatches("1556/0").contains("1 Mai 0"));
		dictionary.addWord("Ja");
		assertEquals(0, strategy.getMatches("1556/0").size());
	}

	@Test
	public void testSpecificCase_2() {
		dictionary = new TrieDictionaryImpl();
		dictionary.addWord("Alf");
		dictionary.addWord("alt");
		dictionary.addWord("ej");
		DefaultTriePartialEncodingSearchStrategy strategy = new DefaultTriePartialEncodingSearchStrategy(dictionary, mapping);
		assertTrue(strategy.getMatches("584201").contains("Alf 2 ej"));
		assertTrue(strategy.getMatches("584201").contains("alt 2 ej"));
		assertEquals(2, strategy.getMatches("584201").size());
		assertTrue(strategy.getMatches("584/2").contains("Alf 2"));
		assertTrue(strategy.getMatches("584/2").contains("alt 2"));
		assertEquals(2, strategy.getMatches("584/2").size());
	}

}
