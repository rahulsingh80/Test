package me.rahul.data.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import me.rahul.data.Dictionary;

public class TrieDictionaryImpl implements Dictionary {

	private Node root = new Node();

	public Node getRoot() {
		return root;
	}

	protected static class Node {
		Map<Character, Node> children;
		String word;

		@Override
		public String toString() {
			String str = "";
			for (Entry<Character, Node> entry : children.entrySet())
				str += entry.getKey().toString();
			return str;
		}

		public boolean isEndNode() {
			return this.children.containsKey('$');
		}

		public String getEndWord() {
			if (this.isEndNode())
				return children.get('$').word;
			else
				return null;
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

		//Add '$' node to mark end of the word  
		Node newNode = new Node();
		newNode.word = word;
		if (null == currNode.children)
			currNode.children = new HashMap<>();
		currNode.children.put('$', newNode);
	}
}
