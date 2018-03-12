package me.rahul.input.search.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import me.rahul.input.data.impl.Node;
import me.rahul.input.data.impl.TrieDictionaryImpl;
import me.rahul.input.search.SearchStrategy;

public class DefaultTrieSearchStrategy_2 implements SearchStrategy {

	private TrieDictionaryImpl dictionary;
	private final Map<Character, List<Character>> mapping;

	public DefaultTrieSearchStrategy_2(TrieDictionaryImpl dictionary, Map<Character, List<Character>> mapping) {
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
		if (pos == digits.length)
			return Collections.emptyList();

		List<String> result = new ArrayList<>();
		List<String> matches = dfs(digits, pos, dictionary.getRoot());
		int lengthLeft = getLengthLeft(digits, pos);
		for (String match : matches) {
			//Handle full match
			if (lengthLeft == getActualLengthOfEncoding(match))
				result.add(match);
			//Handle partial match
			else {
				List<String> remainingMatches = getMatches(digits, pos+getActualLengthOfEncoding(match), false);
				List<String> fusedMatches = addWords(match, remainingMatches);
				addEligibleWords(result, fusedMatches, lengthLeft);
			}
		}
		//If no result, add digit and then call back recursively
		if (matches.isEmpty() && !isPrevDigitUsed) {
			String currWord = digits[pos] + "";
			if (pos == digits.length - 1)
				return Arrays.asList(currWord);
			List<String> remainingMatches = getMatches(digits, pos+1, true);
			List<String> fusedMatches = addWords(currWord, remainingMatches);
			addEligibleWords(result, fusedMatches, lengthLeft);
		}

		return result;
	}

	private int getLengthLeft(char[] digits, int pos) {
		return digits.length - pos;
	}

	private int getActualLengthOfEncoding(String encoding) {
		int length = 0;
		for (char c : encoding.toCharArray()) {
			if (c == ' ' || c == '"')
				continue;
			length++;
			
		}
		return length;
	}

	private List<String> addWords(String prefix, List<String> suffixes) {
		List<String> result = new ArrayList<>();
		for (String suffix : suffixes) {
			result.add(prefix + " " + suffix);
		}
		return result;
	}

	private void addEligibleWords(List<String> targetList, List<String> offeredWords, int expectedLength) {
		for (String s : offeredWords) {
			if (getActualLengthOfEncoding(s) == expectedLength)
				targetList.add(s);
		}
	}
	/**
	 * Recursive function to perform Depth First Search down the Trie Dictionary.
	 * Returns list of exact and partial matches starting at pos.
	 * 
	 * @param digits
	 * @param pos
	 * @param currNode
	 * @return
	 */
	private List<String> dfs(char[] digits, int pos, Node currNode) {
		//If end of digits reached, return end word of currNode
		if (pos == digits.length)
			return (currNode.isEndNode() ? Arrays.asList(currNode.getEndWord()) : Collections.emptyList());

		List<String> result = new ArrayList<>();

		//If currNode is an end node, add its end word to results
		if (currNode.isEndNode())
			result.add(currNode.getEndWord()); 

		//Get mappings for current digit in current node. Call back recursively and add to results.
		char currDigit = digits[pos];
		List<Character> digitMappings = mapping.get(currDigit);
		// Call back for each mapping and add to result.
		for (char searchChar : digitMappings)
			if (currNode.hasEdge(searchChar))
				result.addAll(dfs(digits, pos + 1, currNode.getChild(searchChar)));

		return result;
	}
}
