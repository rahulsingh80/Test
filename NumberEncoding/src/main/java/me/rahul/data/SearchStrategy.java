package me.rahul.data;

import java.util.List;

public interface SearchStrategy {

	public List<String> getMatches(String phoneNumber);
}
