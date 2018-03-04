package me.rahul.output;

import java.util.List;

public class ConsoleOutputHandler implements OutputHandler {

	@Override
	public void handlePartialOutput(String number, List<String> mappings) {
		mappings.stream().map(name -> number + ": " + name).forEach(System.out::println);
	}
}
