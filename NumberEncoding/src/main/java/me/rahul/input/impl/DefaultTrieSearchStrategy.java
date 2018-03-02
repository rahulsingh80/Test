package me.rahul.input.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.rahul.input.SearchStrategy;
import me.rahul.input.impl.TrieDictionaryImpl.Node;

public class DefaultTrieSearchStrategy implements SearchStrategy {

	private TrieDictionaryImpl dictionary; 
	static final Map<Character, List<Character>> mapping = new HashMap<>();	//This should probably be populated outside!!!

	public DefaultTrieSearchStrategy(TrieDictionaryImpl dictionary) {
		this.dictionary = dictionary;
		populate();
	}

	static void populate() {	//This should probably be populated outside!!!
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

	@Override
	public List<String> getMatches(String phoneNumber) {
		int actualSize = getActualSize(phoneNumber);
		char[] digits = new char[actualSize];
		int j=0;
		for (int i=0; i<phoneNumber.length(); i++) {
			if (phoneNumber.charAt(i) == '-' || phoneNumber.charAt(i) == '/')
				continue;
			digits[j++] = phoneNumber.charAt(i);
		}
		return getMatches(digits, 0);
	}

	private int getActualSize(String phoneNumber) {
		int i=0;
		for (int j=0; j<phoneNumber.length(); j++) {
			if (phoneNumber.charAt(j) == '-' || phoneNumber.charAt(j) == '/')
				i++;
		}
		return phoneNumber.length() - i;
	}

	private boolean isPrevDigitUsed = false;

	private List<String> getMatches(char[] digits, int pos) {
		List<String> res = dfs(digits, pos, dictionary.getRoot());

		if (!isPrevDigitUsed && res.isEmpty()) {
			String currWord = digits[pos] + "";
			if (pos == digits.length-1)
				return Arrays.asList(currWord);
			res = dfs(digits, pos+1, dictionary.getRoot());
			for (int i=0; i<res.size(); i++)
				res.set(i, currWord + " " + res.get(i));
			isPrevDigitUsed = true;
		}
		else
			isPrevDigitUsed = false;
		return res;
	}

	/**
	 * Partial match
	 * @param digits
	 * @param pos
	 * @param currNode
	 * @return
	 */
	private List<String> dfs(char[] digits, int pos, Node currNode) {
		//If end of digits reached, return String in currNode
		if (pos == digits.length)
			return getWordForNode(currNode);

		// Get current digit.
		char currDigit = digits[pos];

		List<String> partialRes = new ArrayList<>();
		//If curr Node is an end node. (But phone number has not reached its end)
		if (currNode.children.containsKey('$')) {
			//curr word + dfs rest of digits with root node
			String currWord = currNode.children.get('$').word;
			partialRes = getMatches(digits, pos);
			for (int i=0; i<partialRes.size(); i++)
				partialRes.set(i, currWord + " " + partialRes.get(i));
		}

		//Get mappings for current digit
		List<String> fullRes = new ArrayList<>();
		List<Character> digitMappings = mapping.get(currDigit);
		// Call back for each mapping and add to result.
		for (char searchChar : digitMappings)
			if (currNode.children.containsKey(searchChar))
				fullRes.addAll(dfs(digits, pos+1, currNode.children.get(searchChar)));

		fullRes.addAll(partialRes);
			
		return fullRes;
	}

	private List<String> getWordForNode(Node node) {
		if (node.children.containsKey('$'))
			return Arrays.asList(node.children.get('$').word);
		else
			return Collections.emptyList();
	}
}
