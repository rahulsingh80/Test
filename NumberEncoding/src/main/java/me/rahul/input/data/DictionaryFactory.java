package me.rahul.input.data;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import me.rahul.input.data.impl.TrieDictionaryImpl;

public class DictionaryFactory {

	/**
	 * Create a dictionary of names. 
	 * @param fileLocation - Location of file containing the names
	 * @return
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public Dictionary createDictionary(String fileLocation) throws FileNotFoundException, IOException {
		Dictionary dictionary = new TrieDictionaryImpl();
		try (BufferedReader reader = new BufferedReader(new FileReader(fileLocation))) {
			String name;
			while ((name = reader.readLine()) != null) {
			    dictionary.addWord(name);
			}
		}

		return dictionary;
	}
}
