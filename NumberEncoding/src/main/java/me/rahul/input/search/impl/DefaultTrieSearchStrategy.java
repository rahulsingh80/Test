package me.rahul.input.search.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import me.rahul.input.data.impl.Node;
import me.rahul.input.data.impl.TrieDictionaryImpl;
import me.rahul.input.search.SearchStrategy;

public class DefaultTrieSearchStrategy implements SearchStrategy {

	private TrieDictionaryImpl dictionary;
	private final Map<Character, List<Character>> mapping;

	public DefaultTrieSearchStrategy(TrieDictionaryImpl dictionary, Map<Character, List<Character>> mapping) {
		this.dictionary = dictionary;
		this.mapping = mapping;
	}

	@Override
	public List<String> getMatches(String phoneNumber) {
		if (null == phoneNumber || phoneNumber.isEmpty())
			return Collections.emptyList();
		int actualSize = getActualSize(phoneNumber);
		char[] digits = new char[actualSize];
		// Populate digits array, ignore slashes and dashes
		int j = 0;
		for (int i = 0; i < phoneNumber.length(); i++) {
			// Ignore slashes and dashes
			if (phoneNumber.charAt(i) == '-' || phoneNumber.charAt(i) == '/')
				continue;
			digits[j++] = phoneNumber.charAt(i);
		}

		return getMatches(digits, 0, false);
	}

	/**
	 * Get actual size of phone number, ignoring slashes and dashes.
	 * 
	 * @param phoneNumber
	 * @return
	 */
	private int getActualSize(String phoneNumber) {
		int numCharsToIgnore = 0;
		for (int j = 0; j < phoneNumber.length(); j++) {
			if (phoneNumber.charAt(j) == '-' || phoneNumber.charAt(j) == '/')
				numCharsToIgnore++;
		}
		return phoneNumber.length() - numCharsToIgnore;
	}

	private List<String> getMatches(char[] digits, int pos, boolean isPrevDigitUsed) {
		List<String> res = dfs(digits, pos, dictionary.getRoot(), isPrevDigitUsed);

		if (!isPrevDigitUsed && res.isEmpty()) {
			String currWord = digits[pos] + "";
			if (pos == digits.length - 1)
				return Arrays.asList(currWord);
			res = dfs(digits, pos + 1, dictionary.getRoot(), true);
			for (int i = 0; i < res.size(); i++)
				res.set(i, currWord + " " + res.get(i));
		}
		return res;
	}

	/**
	 * Recursive function to perform Depth First Search down the Trie Dictionary.
	 * Returns list of matches.
	 * 
	 * @param digits
	 * @param pos
	 * @param currNode
	 * @return
	 */
	private List<String> dfs(char[] digits, int pos, Node currNode, boolean isPrevDigitUsed) {
		// If end of digits reached, return end word of currNode
		if (pos == digits.length)
			return (currNode.isEndNode() ? Arrays.asList(currNode.getEndWord()) : Collections.emptyList());

		char currDigit = digits[pos];

		// If curr Node is an end node. (But phone number has not reached its end)
		List<String> partialRes = new ArrayList<>();
		if (currNode.isEndNode()) {
			// curr word + dfs rest of digits with root node
			String currWord = currNode.getEndWord();
			partialRes = getMatches(digits, pos, isPrevDigitUsed);
			for (int i = 0; i < partialRes.size(); i++)
				partialRes.set(i, currWord + " " + partialRes.get(i));
		}

		// Get mappings for current digit
		List<String> fullRes = new ArrayList<>();
		List<Character> digitMappings = mapping.get(currDigit);
		// Call back for each mapping and add to result.
		for (char searchChar : digitMappings)
			if (currNode.hasEdge(searchChar))
				fullRes.addAll(dfs(digits, pos + 1, currNode.getChild(searchChar), false));

		fullRes.addAll(partialRes);

		return fullRes;
	}
}
