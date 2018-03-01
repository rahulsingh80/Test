package core;

import java.io.File;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import flowData.FlowData;

/**
 * This class parses parse.xml and reads the whole document into a set of java bean objects
 * rooted in FlowData 
 * @author Rahul Singh
 *
 */
public class Parser {

	public static void main(String[] args) {
        FlowData data= parse();  
        System.out.println(data.getAppName()+" "+data.getDbTables());  
	}

	public static FlowData parse() {
		FlowData data = null;
		try {  
			   
	        File file = new File("parse.xml");  

	        JAXBContext jaxbContext = JAXBContext.newInstance(FlowData.class);  
	   
	        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();  
	        data = (FlowData) jaxbUnmarshaller.unmarshal(file);  
	          
	      } catch (JAXBException e) {  
	        e.printStackTrace();  
	      }

		return data;
	}

}
