package me.rahul.controller.impl;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import me.rahul.controller.EncodingController;
import me.rahul.input.data.Dictionary;
import me.rahul.input.data.DictionaryFactory;
import me.rahul.input.search.SearchStrategy;
import me.rahul.input.search.SearchStrategyFactory;
import me.rahul.output.ConsoleOutputHandler;
import me.rahul.output.OutputHandler;

public class EncodingControllerImpl implements EncodingController {

	private DictionaryFactory dictionaryFactory;
	private SearchStrategyFactory searchStrategyFactory;
	OutputHandler outputHandler;

	public EncodingControllerImpl(DictionaryFactory dictionaryFactory, SearchStrategyFactory searchStrategyFactory,
			OutputHandler outputHandler) {
		this.dictionaryFactory = dictionaryFactory;
		this.searchStrategyFactory = searchStrategyFactory;
		this.outputHandler = outputHandler;
	}

	public void encodeNumbers(String dictionaryDataLocation, String phoneNumbersDataLocation)
			throws FileNotFoundException, IOException {
		// Create dictionary
		Dictionary dictionary = dictionaryFactory.createDictionary(dictionaryDataLocation);

		// Get search strategy
		SearchStrategy searchStrategy = searchStrategyFactory.getSearchStrategy(dictionary);

		// Read incoming numbers from the stream, Get matches from dictionary
		handleNumberMappings(phoneNumbersDataLocation, searchStrategy);
	}

	private void handleNumberMappings(String phoneNumbersDataLocation, SearchStrategy searchStrategy)
			throws FileNotFoundException, IOException {
		try (BufferedReader reader = new BufferedReader(new FileReader(phoneNumbersDataLocation))) {
			String number;
			while ((number = reader.readLine()) != null) {
				List<String> matches = searchStrategy.getMatches(number);
				outputHandler.handlePartialOutput(number, matches);
			}
		}
	}
}
