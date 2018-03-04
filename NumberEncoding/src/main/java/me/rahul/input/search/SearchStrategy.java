package me.rahul.input.search;

import java.util.List;

/**
 * Strategy for searching names inside a dictionary
 * @author Rahul
 *
 */
public interface SearchStrategy {

	public List<String> getMatches(String phoneNumber);
}
