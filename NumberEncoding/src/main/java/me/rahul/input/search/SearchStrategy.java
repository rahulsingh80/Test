package me.rahul.input.search;

import java.util.List;

public interface SearchStrategy {

	public List<String> getMatches(String phoneNumber);
}
