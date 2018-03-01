package core;

import flowData.DbTable;
import flowData.FlowData;

public class CodeGeneratorHelper {

	public static void setDefaultValuesWhereNotProvided(FlowData data) {
		for (DbTable dbTable : data.getDbTables()) {
			if (null == dbTable.getVoClassName()) {
				dbTable.setVoClassName(dbTable.getName() + "VO");
			}
			if (null == dbTable.getServiceClassName()) {
				dbTable.setServiceClassName(dbTable.getName() + "Service");
			}
			if (null == dbTable.getDbClassName()) {
				dbTable.setDbClassName(dbTable.getName() + "DAO");
			}
		}
	}

	public static String firstCharacterUpperCase(String string) {
		StringBuffer strBuffer = new StringBuffer(string);
		String firstLetter = string.substring(0, 1).toUpperCase();
		strBuffer.replace(0, 1, firstLetter);
		return strBuffer.toString();
	}

	public static String firstCharacterLowerCase(String string) {
		StringBuffer strBuffer = new StringBuffer(string);
		String firstLetter = string.substring(0, 1).toLowerCase();
		strBuffer.replace(0, 1, firstLetter);
		return strBuffer.toString();
	}

	public static String approximateCamelCase(String string) {
		//First character lower case
		string = firstCharacterLowerCase(string);
		//Replace _Letter with ""CapitalLetter
		StringBuffer strBuffer = new StringBuffer(string);
		//char[] stringCharArray = string.toCharArray();
		int index;
		while ((index = strBuffer.indexOf("_")) > 0) {
			int nextIndex = index + 1;
			String capitalizedLetter = strBuffer.substring(nextIndex, nextIndex+1).toUpperCase();
			strBuffer.replace(index, nextIndex+1, capitalizedLetter);
		}
		return strBuffer.toString();
	}

	public static String approximatePascalCase(String string) {
		return firstCharacterUpperCase(approximateCamelCase(string));
		
	}

	public static String addSpaces(int numOfSpaces) {
		String spaces="";
		while (numOfSpaces-- > 0) {
			spaces+=" ";
		}
		return spaces;
	}

	public static short getStartingSpaces(String line) {
		short numOfSpaces = 0;
		char[] lineCharArray = line.toCharArray();
		while (lineCharArray[numOfSpaces] == ' ') {
			numOfSpaces++;
		}
		return numOfSpaces;
	}
}
