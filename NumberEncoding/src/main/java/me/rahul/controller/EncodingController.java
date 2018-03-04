package me.rahul.controller;

import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * The Encoding Engine for the application 
 * @author Rahul
 *
 */
public interface EncodingController {

	public void encodeNumbers(String dictionaryDataLocation, String phoneNumbersDataLocation) throws FileNotFoundException, IOException;
}
