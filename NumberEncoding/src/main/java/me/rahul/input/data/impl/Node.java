package me.rahul.input.data.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

/**
 * This represents a node in a TrieDictionaryImpl object.
 * 
 * @author Rahul
 *
 */
public class Node {
	private Map<Character, Node> children;	//The key marks the outgoing character.
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

	/**
	 * Does this node represent the end of a name. Each end node will have an
	 * outgoing edge for '$' The node at the end of the '$' edge contains the name.
	 * 
	 * @return
	 */
	public boolean isEndNode() {
		if (null == this.children)
			return false;
		return this.children.containsKey('$');
	}

	/**
	 * If this is an end node, return the corresponding word.
	 * @return
	 */
	public String getEndWord() {
		if (this.isEndNode())
			return children.get('$').word;
		else
			return null;
	}

	/**
	 * Is there an outgoing edge for character 'c'
	 * @param c
	 * @return
	 */
	public boolean hasEdge(char c) {
		if (null == children)
			return false;
		if (!children.containsKey(c))
			return false;
		return true;
	}

	/**
	 * Get node for outgoing edge 'c'
	 * @param c
	 * @return
	 */
	public Node getChild(char c) {
		if (null == children)
			return null;
		return children.get(c);
	}

	/**
	 * Add a child node on an outgoing edge marked 'c'
	 *  
	 * @param c
	 * @param child
	 */
	public void addChild(char c, Node child) {
		if (null == children)
			children = new HashMap<>();
		children.put(c, child);
	}
}