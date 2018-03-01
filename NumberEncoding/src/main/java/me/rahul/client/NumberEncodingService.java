package me.rahul.client;

/**
 * This represents the service exposed to the clients of this application.
 * It is the window to the outside world.
 * @author Rahul
 *
 */
public interface NumberEncodingService {

	/**
	 * Find and print the encodings of numbers for the given inputs
	 * @param dictionaryLocation
	 * @param phoneNumbersLocation
	 */
	public void encodeNumbers(String dictionaryLocation, String phoneNumbersLocation);
}
