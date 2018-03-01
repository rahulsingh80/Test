package me.rahul.input.impl;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.rahul.input.SearchStrategy;
import me.rahul.input.impl.TrieDictionaryImpl.Node;

public class DefaultTrieSearchStrategy implements SearchStrategy {

	private TrieDictionaryImpl dictionary; 
	static Map<Character, List<Character>> mapping;	//This should probably be populated outside!!!

	public void DefaultTrieSearchStrategy(TrieDictionaryImpl dictionary) {
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
		char[] digits = phoneNumber.toCharArray();

		//You start with root node as current node
		Node currNode = dictionary.getRoot();

		//You read digit by digit
		for (int i=0; i<phoneNumber.length(); i++) {
			char digit = digits[i];
			//For each digit, go through corresponding alphabets and get matching children of curr Node
			currNode.
				//For the matches, recursive DFS 
		}
		return null;
	}

	private String dfs(Node currNode, char digit) {
		List<Character> alphabets = mapping.get(digit);
		for (char c : alphabets) {
			Node next = currNode.children.get(c);
			if (null != next) {
				
			}
		}
		return null;
	}
}
