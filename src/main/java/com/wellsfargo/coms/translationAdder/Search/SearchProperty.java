package com.wellsfargo.coms.translationAdder.Search;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;
import java.util.List;
import java.util.Locale;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.Scanner;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;

import com.wellsfargo.coms.translationAdder.App;
import com.wellsfargo.coms.translationAdder.Misc.UnicodeEncoding;


public class SearchProperty {
    
	public SearchProperty(String fileName, String filePath, String propertyName, String newTranslation) {
		
		final Logger logger = Logger.getLogger(App.class.getName());
		newTranslation= (new UnicodeEncoding()).getEncodedString(newTranslation);
		ResourceBundle rb = ResourceBundle.getBundle("config");
        boolean disablePrompt = Boolean.valueOf(rb.getString("disablePrompt"));
		boolean done=false;
		String oldTranslation;
		File currentTransFile=new File(filePath+"\\"+ fileName);
		//System.out.println(fileName+ " found at: " + filePath+fileName);
		logger.info(fileName+ " found at: " + filePath+fileName);
     	try {
	        if (currentTransFile.isFile()) {        
	        	BufferedReader br = new BufferedReader(new InputStreamReader(
            	        new FileInputStream(filePath+"\\"+ fileName), "UTF-8"));
	            String s,in=""; 
	            while((s = br.readLine()) != null) { 
	            	in+=s+"\n";
	            }
	            //System.out.println(in);
	            br.close();
	            br.close();
	            if((in.contains(propertyName+"=")||in.contains(propertyName+" =")) && 
                			!currentTransFile.getName().equals("WriteExcelDemo.java")) {
	            	if(in.contains(propertyName+" = ")) {oldTranslation = in.split(propertyName+" = ")[1].split("\n")[0]; }
	            	else if(in.contains(propertyName+" =")){oldTranslation = in.split(propertyName+" =")[1].split("\n")[0]; }
	            	else {oldTranslation = in.split(propertyName+"=")[1].split("\n")[0]; }
	            	
	            	
	                BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filePath+fileName), "UTF-8"));
		            if(!disablePrompt) {
		            	if(oldTranslation.equals(newTranslation)) {
		            		System.out.println("-----" + propertyName+"="+oldTranslation + "----- already present in "+ currentTransFile.getName()+" with same translation\n");
		            		logger.info("-----" + propertyName+"="+oldTranslation + "----- already present in "+ currentTransFile.getName()+" with same translation\n");
		            	}
		            	else {
		            		System.out.println("-----" + propertyName+"="+oldTranslation + "-----(old) already present in "+ currentTransFile.getName()+"\n");
		            		logger.info("-----" + propertyName+"="+oldTranslation + "-----(old) already present in "+ currentTransFile.getName()+"\n");
		            	}
		            	
	                	Scanner myIn = new Scanner(System.in);
		                System.out.println("Do you want to replace it with -----" + propertyName+"="+newTranslation + "-----  ? (y/n) : ");
		                //logger.info("Do you want to replace it with -----" + propertyName+"="+newTranslation + "-----  ? (y/n) : ");
		                String decide = myIn.nextLine();
		                //myIn.close();
		                switch(decide.toLowerCase()) {
		                	case "y":
		                		in = in.replace(oldTranslation, newTranslation);	                
		                		break;
		                	case "n":
		                		break;
		                }
		            }
		            else {
		            	if(oldTranslation.equals(newTranslation)) {
		            		//System.out.println("-----" + propertyName+"="+oldTranslation + "----- already present in "+ currentTransFile.getName()+" with same translation. Skipping... \n");
		            		logger.info("-----" + propertyName+"="+oldTranslation + "----- already present in "+ currentTransFile.getName()+" with same translation. Skipping... \n");
		            	}
		            	else {	
		            		//System.out.println("-----" + propertyName+"="+oldTranslation + "-----(old) already present in "+ currentTransFile.getName()+
		            		//		", replacing it with new -----"+ propertyName+"="+newTranslation + "-----\n");
		            		logger.info("-----" + propertyName+"="+oldTranslation + "-----(old) already present in "+ currentTransFile.getName()+
		            				", replacing it with new -----"+ propertyName+"="+newTranslation + "-----\n");
		            		in = in.replace(oldTranslation, newTranslation);
		            	}
		            }
	                out.write(in);
	                out.flush();
	                out.close();
	                done=true;
	            }
	            
	            if(!done) {
	            	//System.out.println("Writing New -----" + propertyName+"="+newTranslation + "----- in "+ currentTransFile.getName()+"\n");
	            	logger.info("Writing New -----" + propertyName+"="+newTranslation + "----- in "+ currentTransFile.getName()+"\n");
	            	BufferedWriter out = new BufferedWriter(new FileWriter(filePath+fileName, true)); 
        			out.write("\n"+propertyName+"="+newTranslation); 
        			out.close(); 
	            }		
	        }
     	}
     	catch(Exception e){
        	//System.out.print("Cant find " + propertyName);
            e.printStackTrace();
        }
    }
}    