package me.rahul.output;

import java.util.List;

public interface OutputHandler {

	public void handlePartialOutput(String number, List<String> mappings);
}
