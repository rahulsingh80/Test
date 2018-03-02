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
	static Map<Character, List<Character>> mapping;	//This should probably be populated outside!!!

	public DefaultTrieSearchStrategy(TrieDictionaryImpl dictionary) {
		this.dictionary = dictionary;
		populate();
	}

	static void populate() {	//This should probably be populated outside!!!
		mapping = new HashMap<>();
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
		// Take every digit and dfs down from root.
				char[] digits = phoneNumber.toCharArray();
				//return dfs(digits, 0, dictionary.getRoot());
				return dfs2(digits, 0, dictionary.getRoot());
	}

	/**
	 *
	 * @param digits - char array of phone number
	 * @param pos - current position in digits
	 * @param currNode - current node in tree
	 * 
	 * @return
	 * 
	 */
	/*private List<String> dfs(char[] digits, int pos, Node currNode) {
		// Check if final digit.
		if (pos == digits.length) {
			// Do '$' check and return name if such is the case
			if (currNode.children.containsKey('$')) {
				return Arrays.asList(currNode.children.get('$').word);
			}
			return Collections.emptyList();
		}

		// Get current digit.
		char currDigit = digits[pos];
		//Ignore dashes and slashes.	//Fix this!!!!!!
		while (currDigit == '-' || currDigit == '/') {
			currDigit = digits[++pos];
		}
			
		// Get mappings for current digit
		List<Character> digitMappings = mapping.get(currDigit);
		List<String> res = new ArrayList<>();
		// Call back for each mapping and add to result.
		for (char searchChar : digitMappings) {
			if (currNode.children.containsKey(searchChar)) {
				res.addAll(dfs(digits, pos + 1, currNode.children.get(searchChar)));
			}
		}
		return res;

	}*/

	/**
	 * Partial match
	 * @param digits
	 * @param pos
	 * @param currNode
	 * @return
	 */
	private List<String> dfs2(char[] digits, int pos, Node currNode) {
		//If end of digits reached, return String in currNode
		if (pos == digits.length) {
			if (currNode.children.containsKey('$'))
				return Arrays.asList(currNode.children.get('$').word);
			else
				return Collections.emptyList();
		}

		// Get current digit.
		char currDigit = digits[pos];
		//Ignore dashes and slashes.	//Fix this!!!!!!
		while (currDigit == '-' || currDigit == '/') {
			currDigit = digits[++pos];
		}
		List<String> partialRes = new ArrayList<>();
		//If curr Node has a $ child
		if (currNode.children.containsKey('$')) {
			//curr word + dfs rest of digits with root node
			String currWord = currNode.children.get('$').word;
			partialRes = dfs2(digits, pos, dictionary.getRoot());
			for (int i=0; i<partialRes.size(); i++) {
				partialRes.set(i, currWord + " " + partialRes.get(i));
			}
		}
		//Get mappings for current digit
		List<String> fullRes = new ArrayList<>();
		List<Character> digitMappings = mapping.get(currDigit);
		// Call back for each mapping and add to result.
		for (char searchChar : digitMappings) {
			if (currNode.children.containsKey(searchChar)) {
				fullRes.addAll(dfs2(digits, pos+1, currNode.children.get(searchChar)));
			}
		}

		fullRes.addAll(partialRes);
		return fullRes;
	}
}
