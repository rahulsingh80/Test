package me.rahul.input.search;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.rahul.input.data.Dictionary;
import me.rahul.input.data.impl.TrieDictionaryImpl;
import me.rahul.input.search.impl.DefaultTrieSearchStrategy;

public class SearchStrategyFactory {

	final Map<Character, List<Character>> mapping = new HashMap<>();

	public SearchStrategyFactory() {
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
	public SearchStrategy getSearchStrategy(Dictionary dictionary) {
		return new DefaultTrieSearchStrategy((TrieDictionaryImpl) dictionary, mapping);
	}
}
