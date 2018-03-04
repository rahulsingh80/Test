package me.rahul.client.impl;

import java.io.IOException;

import me.rahul.client.NumberEncodingService;
import me.rahul.controller.EncodingController;

public class NumberEncodingServiceImpl implements NumberEncodingService {

	private EncodingController encodingController;

	public NumberEncodingServiceImpl(EncodingController encodingController) {
		this.encodingController = encodingController;
	}
	@Override
	public void encodeNumbers(String dictionaryDataLocation, String phoneNumbersDataLocation) {
		try {
			encodingController.encodeNumbers(dictionaryDataLocation, phoneNumbersDataLocation);
		} catch (IOException e) {
			System.err.println("Exception encountered in reading files.");
			e.printStackTrace();
		}
	}

}
