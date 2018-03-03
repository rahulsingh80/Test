package me.rahul.client.impl;

import java.io.IOException;

import me.rahul.client.NumberEncodingService;
import me.rahul.controller.EncodingController;

class NumberEncodingServiceImpl implements NumberEncodingService {

	private EncodingController encodingController;
	public void encodeNumbers(String dictionaryLocation, String phoneNumbersLocation) {
		try {
			encodingController.encodeNumbers(dictionaryLocation, phoneNumbersLocation);
		} catch (IOException e) {
			System.err.println("Exception encountered in reading files.");
			e.printStackTrace();
		}
	}

}
