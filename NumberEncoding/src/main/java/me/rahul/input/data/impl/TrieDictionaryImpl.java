package me.rahul.input.data.impl;

import me.rahul.input.data.Dictionary;

public class TrieDictionaryImpl implements Dictionary {

	private Node root = new Node();

	public Node getRoot() {
		return root;
	}

	@Override
	public void addWord(String word) {
		char[] wordChars = word.toCharArray();
		Node currNode = root;
		for (int i = 0; i < wordChars.length; i++) {
			char c = wordChars[i];
			//Ignore '"'
			if (c == '"')
				continue;
			if (currNode.hasEdge(c)) {
				currNode = currNode.getChild(c);
			} else {
				Node newNode = new Node();
				currNode.addChild(c, newNode);
				currNode = newNode;
			}
		}

		//Add '$' node to mark end of the word  
		Node newNode = new Node();
		newNode.setWord(word);
		currNode.addChild('$', newNode);
	}
}
