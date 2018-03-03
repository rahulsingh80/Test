package me.rahul.controller.impl;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import me.rahul.controller.EncodingController;
import me.rahul.data.Dictionary;
import me.rahul.data.DictionaryFactory;
import me.rahul.data.SearchStrategy;
import me.rahul.data.SearchStrategyFactory;

public class EncodingControllerImpl implements EncodingController {

	private DictionaryFactory dictionaryFactory;
	private SearchStrategyFactory searchStrategyFactory;

	public EncodingControllerImpl(DictionaryFactory dictionaryFactory, SearchStrategyFactory searchStrategyFactory) {
		this.dictionaryFactory = dictionaryFactory;
		this.searchStrategyFactory = searchStrategyFactory;
	}

	public void encodeNumbers(String dictionaryLocation, String phoneNumbersLocation) throws FileNotFoundException, IOException {
		//Create dictionary
		Dictionary dictionary = dictionaryFactory.getDictionary(dictionaryLocation);

		//Get search strategy
		SearchStrategy searchStrategy = searchStrategyFactory.getSearchStrategy(dictionary);
		
		//Read incoming numbers from the stream, Get matches from dictionary
		handleNumberMappings(phoneNumbersLocation, searchStrategy);
	}

	private void handleNumberMappings(String phoneNumbersLocation, SearchStrategy searchStrategy) throws FileNotFoundException, IOException {
		//Can try Files.lines
		try (BufferedReader reader = new BufferedReader(new FileReader(phoneNumbersLocation))) {
			String number;
			while ((number = reader.readLine()) != null) {
				List<String> matches = searchStrategy.getMatches(number);
				handlePartialMappings(number, matches);
			}
		}
	}

	private void handlePartialMappings(String number, List<String> partialMappings) {
		partialMappings.stream().map(name -> number + ": " + name).forEach(System.out::println);
	}

	public static void main(String[] args) {
		EncodingControllerImpl controller = new EncodingControllerImpl(new DictionaryFactory(), new SearchStrategyFactory());
		try {
			//controller.encodeNumbers("F:\\TestDictionary.txt", "F:\\TestPhoneNumbers.txt");
			controller.encodeNumbers("F:\\360T\\numberencoding\\dictionary.txt", "F:\\360T\\numberencoding\\input.txt");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println();
	}
}
