package me.rahul.controller;

import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * The Controller creates the relevant objects, delegates relevant tasks to them,
 * and forwards the output for display. 
 * @author Rahul
 *
 */
public interface EncodingController {

	/**
	 * Create Dictionary. Match the phone numbers against it. Forward the data for printing.
	 * @param dictionaryDataLocation
	 * @param phoneNumbersDataLocation
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public void encodeNumbers(String dictionaryDataLocation, String phoneNumbersDataLocation) throws FileNotFoundException, IOException;
}
