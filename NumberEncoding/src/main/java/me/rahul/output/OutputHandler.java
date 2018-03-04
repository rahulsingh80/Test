package me.rahul.output;

import java.util.List;

/**
 * This class handles the partial output provided to it.
 * @author Rahul
 *
 */
public interface OutputHandler {

	/**
	 * Handle the partial output provided.
	 * @param number
	 * @param mappings
	 */
	public void handlePartialOutput(String number, List<String> mappings);
}
