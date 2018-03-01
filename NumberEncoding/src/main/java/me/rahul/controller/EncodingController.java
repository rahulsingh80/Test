package me.rahul.controller;

/**
 * The Encoding Engine for the application 
 * @author Rahul
 *
 */
public interface EncodingController {

	//Controller gets the input file locations
	//Controller gets the Dictionary made via a call to separate, dedicated class
	//Controller uses a separate strategy class for encoding. By passing it a specific dictionary object
	public void encodeNumbers(String dictionaryLocation, String phoneNumbersLocation);
}
