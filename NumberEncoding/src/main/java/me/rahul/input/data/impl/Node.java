package me.rahul.input.data.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class Node {
	private Map<Character, Node> children;
	private String word;

	public Map<Character, Node> getChildren() {
		return children;
	}

	public String getWord() {
		return word;
	}

	public void setWord(String word) {
		this.word = word;
	}

	public boolean isEndNode() {
		if (null == this.children)
			return false;
		return this.children.containsKey('$');
	}

	public String getEndWord() {
		if (this.isEndNode())
			return children.get('$').word;
		else
			return null;
	}

	public boolean hasEdge(char c) {
		if (null == children)
			return false;
		if (!children.containsKey(c))
			return false;
		return true;
	}

	public Node getChild(char c) {
		if (null == children)
			return null;
		return children.get(c);
	}

	public void addChild(char c, Node child) {
		if (null == children)
			children = new HashMap<>();
		children.put(c, child);
	}

	@Override
	public String toString() {
		String str = "";
		for (Entry<Character, Node> entry : children.entrySet())
			str += entry.getKey().toString();
		return str;
	}

}
