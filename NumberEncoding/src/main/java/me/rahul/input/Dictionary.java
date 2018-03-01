package me.rahul.input;

import java.util.List;

public interface Dictionary {

	public void createDictionary(String fileLocation);
	void addWord(String word);
	List<String> getMatches(String phoneNumber);
}
