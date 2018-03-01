package me.rahul.controller.impl;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import me.rahul.controller.EncodingController;
import me.rahul.input.Dictionary;
import me.rahul.input.SearchStrategy;
import me.rahul.input.impl.DefaultTrieSearchStrategy;
import me.rahul.input.impl.TrieDictionaryImpl;

public class EncodingControllerImpl implements EncodingController {

	private TrieDictionaryImpl dictionary;
	private DefaultTrieSearchStrategy searchStrategy;
	public void encodeNumbers(String dictionaryLocation, String phoneNumbersLocation) {
		//Create dictionary
		createDictionary(dictionaryLocation);
		
		//Read incoming numbers from the stream, Get matches from dictionary
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(phoneNumbersLocation));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		String line;
	    try {
			while ((line = reader.readLine()) != null) {
				
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		printMatches(phoneNumbersLocation);
		//for ()
	}

	private void printMatches(String phoneNumbersLocation) {
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(phoneNumbersLocation));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		String line;
	    try {
			while ((line = reader.readLine()) != null) {
			    // process line here.
				char[] digits = getDigitsOnly(line);
				//Get matches
				List<String> matches = dictionary.getMatches(digits);
				matches.stream().forEach(System.out::println);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    
	    
	    //With Streams
	    /*List<String> alist = Files.lines(Paths.get(phoneNumbersLocation))
	    	    .filter(line -> line.contains("abc"))
	    	    .collect(Collectors.toList());*/
	}

	private char[] getDigitsOnly(String line) {
		// Fix this method!!!
		char[] number = line.toCharArray(); 
		return number;
	}

	private void createDictionary(String dictionaryLocation) {
		//Dictionary is best gotten from another call. or through injection
		dictionary = new TrieDictionaryImpl();
	}

	/**
	 * This has to be there in every impl of Controller. If many controllers, we can create an abstract impl which 
	 * has an abstract method by this name.
	 * @param number
	 * @return
	 */
	/*private List<String> getMatches(String number) {	
		
	}*/
}
