package me.rahul.main;

import me.rahul.client.NumberEncodingService;
import me.rahul.client.impl.NumberEncodingServiceImpl;
import me.rahul.controller.EncodingController;
import me.rahul.controller.impl.EncodingControllerImpl;
import me.rahul.input.data.DictionaryFactory;
import me.rahul.input.search.SearchStrategyFactory;
import me.rahul.output.ConsoleOutputHandler;

public class Main {

	public static void main(String[] args) {
		// Run the application with provided inputs
		String dictionaryLocation = args[0];
		String phoneNumbersLocation = args[1];
		EncodingController controller = new EncodingControllerImpl(new DictionaryFactory(), new SearchStrategyFactory(),
				new ConsoleOutputHandler());
		NumberEncodingService service = new NumberEncodingServiceImpl(controller);

		service.encodeNumbers(dictionaryLocation, phoneNumbersLocation);
	}
}
