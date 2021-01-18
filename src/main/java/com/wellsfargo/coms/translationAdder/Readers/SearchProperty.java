package com.wellsfargo.coms.translationAdder.Readers;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Locale;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.Scanner;
import com.wellsfargo.coms.translationAdder.Misc.OrderedProperties;

public class SearchProperty {
    
	public SearchProperty(String fileName, String filePath, String propertyName, String newTranslation) {
		ResourceBundle rb = ResourceBundle.getBundle("config");
        boolean disablePrompt = Boolean.valueOf(rb.getString("disablePrompt"));
        Properties props = new OrderedProperties();
		boolean done=false;
		File currentTransFile=new File(filePath+"\\"+ fileName);
		System.out.println(fileName+ " found at: " + filePath+fileName);
     	try {
	        if (currentTransFile.isFile()) {
	        	FileInputStream input = new FileInputStream(new File(filePath+"\\"+ fileName));
	        	props.load(new InputStreamReader(input, Charset.forName("UTF-8")));
	        	//System.out.println(propertyName + "=" + props.getProperty(propertyName));
	        	BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filePath+fileName), "UTF-8"));
	        	if(props.containsKey(propertyName)) {
	        		System.out.println("-----" + propertyName+"="+props.getProperty(propertyName) + "----- already present in "+ currentTransFile.getName());
		            if(!disablePrompt) {
		                System.out.println("Do you want to replace it with -----" + propertyName+"="+newTranslation + "-----  ? (y/n) : ");
	                	Scanner myIn = new Scanner(System.in);
		                String decide = myIn.nextLine();
		                //myIn.close();
		                switch(decide.toLowerCase()) {
		                	case "y":
		                		props.replace(propertyName, newTranslation);
		                		props.store(out, "");              
		                		break;
		                	case "n":
		                		break;
		                }
		            }
		            else {
		            	System.out.println("Replacing existing property with -----" + propertyName+"="+newTranslation + "-----\n");
		            	props.put(propertyName, newTranslation);
                		props.store(out, "");   
		            }
	                done=true;
	            }
	            if(!done) {
	            	System.out.println("Writing New -----" + propertyName+"="+newTranslation + "----- in "+ currentTransFile.getName());
	            	props.put(propertyName, newTranslation);
	            	props.store(out, "");
	            }	
	        }
	    }
     	catch(Exception e){
        	//System.out.print("Cant find " + propertyName);
            e.printStackTrace();
        }
    }	
}    