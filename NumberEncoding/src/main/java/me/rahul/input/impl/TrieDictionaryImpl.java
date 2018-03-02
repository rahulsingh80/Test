package me.rahul.input.impl;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import me.rahul.input.Dictionary;
import me.rahul.input.SearchStrategy;

public class TrieDictionaryImpl implements Dictionary {

	private Node root = new Node();

	public Node getRoot() {
		return root;
	}

	protected static class Node {
		Map<Character, Node> children;
		boolean isLeaf;
		String word;

		@Override
		public String toString() {
			String str = "";
			for (Entry<Character, Node> entry : children.entrySet())
				str += entry.getKey().toString();
			return str;
		}
	}

	@Override
	public void addWord(String word) {
		char[] wordChars = word.toCharArray();
		Node currNode = root;
		for (int i = 0; i < wordChars.length; i++) {
			char c = wordChars[i];
			if (c == '"')
				continue;
			if (null == currNode.children)
				currNode.children = new HashMap<>();
			if (currNode.children.containsKey(c)) {
				currNode = currNode.children.get(c);
			} else {
				Node newNode = new Node();
				currNode.children.put(c, newNode);
				currNode = newNode;
			}
		}
		Node newNode = new Node();
		newNode.isLeaf = true;
		newNode.word = word;
		if (null == currNode.children)
			currNode.children = new HashMap<>();
		currNode.children.put('$', newNode);
	}

	@Override
	public void createDictionary(String fileLocation) {
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(fileLocation));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		String line;
		try {
			while ((line = reader.readLine()) != null) {
				addWord(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
