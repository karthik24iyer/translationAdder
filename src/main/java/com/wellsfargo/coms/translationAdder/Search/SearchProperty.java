package com.wellsfargo.coms.translationAdder.Search;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.nio.file.Files;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.Scanner;
import org.apache.log4j.Logger;
import com.wellsfargo.coms.translationAdder.App;
import com.wellsfargo.coms.translationAdder.Misc.UnicodeEncoding;


public class SearchProperty {
    
	public SearchProperty(String fileName, String filePath, String propertyName, String newTranslation) {
		
		final Logger logger = Logger.getLogger(App.class.getName());
		newTranslation= (new UnicodeEncoding()).getEncodedString(newTranslation).trim();
		ResourceBundle rb = ResourceBundle.getBundle("config");
		String ignoreSearchForFiles = rb.getString("IgNoReAlL");
        boolean disablePrompt = Boolean.valueOf(rb.getString("disablePrompt"));
		boolean done=false;
		int lineNumber=0, propAtLine=0;
		String oldTranslation;
		File currentTransFile=new File(filePath+"\\"+ fileName);
		//System.out.println(fileName+ " found at: " + filePath+fileName);
		logger.info(fileName+ " found at: " + filePath+fileName);
     	try {
	        if (currentTransFile.isFile()) {        
	        	BufferedReader br = new BufferedReader(new InputStreamReader(
            	        new FileInputStream(filePath+"\\"+ fileName),"ISO-8859-1"));
	            String s;
	            ArrayList<String> in= new ArrayList<String>(); 
	            while((s = br.readLine()) != null) { 
		            	lineNumber++;
		            	in.add(s);
		            //System.out.println("lineNumber: "+lineNumber+" - "+in);	
		            if((s.startsWith(propertyName+"=")||s.startsWith(propertyName+" =")) && !s.startsWith("#") && !ignoreSearchForFiles.contains(fileName)) {
		            	if(s.contains(propertyName+" = ")) {oldTranslation = s.split(propertyName+" = ")[1].split("\n")[0]; }
		            	else if(s.contains(propertyName+" =")){oldTranslation = s.split(propertyName+" =")[1].split("\n")[0]; }
		            	else {
			            	try {
			            		oldTranslation = s.split(propertyName+"=")[1].split("\n")[0].trim();
			            	}
			            	catch(ArrayIndexOutOfBoundsException e) {
			            		oldTranslation="";
			            	}
		            	}
		            	propAtLine=lineNumber;
			            if(!disablePrompt) {
			            	if(oldTranslation.toLowerCase().equals(newTranslation.toLowerCase())) {
			            		System.out.println("-----" + propertyName+"="+oldTranslation + "----- already present in "+ currentTransFile.getName()+" with same translation at line: "+propAtLine+"\n");
			            		logger.info("-----" + propertyName+"="+oldTranslation + "----- already present in "+ currentTransFile.getName()+" with same translation at line: "+propAtLine+"\n");
			            	}
			            	else {
			            		System.out.println("-----" + propertyName+"="+oldTranslation + "-----(old) already present in "+ currentTransFile.getName()+" at line: "+propAtLine+"\n");
			            		logger.info("-----" + propertyName+"="+oldTranslation + "-----(old) already present in "+ currentTransFile.getName()+" at line: "+propAtLine+"\n");
			            	}
			            	
		                	Scanner myIn = new Scanner(System.in);
			                System.out.println("Do you want to replace it with -----" + propertyName+"="+newTranslation + "-----  ? (y/n) : ");
			                //logger.info("Do you want to replace it with -----" + propertyName+"="+newTranslation + "-----  ? (y/n) : ");
			                String decide = myIn.nextLine();
			                //myIn.close();
			                switch(decide.toLowerCase()) {
			                	case "y":
			                		in.set(propAtLine-1, propertyName+"="+newTranslation);
			                		break;
			                	case "n":
			                		break;
			                	default:
			                		break;
			                }
			            }
			            else {
			            	if(oldTranslation.toLowerCase().equals(newTranslation.toLowerCase())) {
			            		//System.out.println("-----" + propertyName+"="+oldTranslation + "----- already present in "+ currentTransFile.getName()+" with same translation. Skipping... \n");
			            		logger.info("-----" + propertyName+"="+oldTranslation + "----- already present in "+ currentTransFile.getName()+" with same translation at line: "+propAtLine+". Skipping... \n");
			            	}
			            	else {	
			            		//System.out.println("-----" + propertyName+"="+oldTranslation + "-----(old) already present in "+ currentTransFile.getName()+
			            		//		", replacing it with new -----"+ propertyName+"="+newTranslation + "-----\n");
			            		logger.info("-----" + propertyName+"="+oldTranslation + "-----(old) already present in "+ currentTransFile.getName()+
			            				"at line: "+propAtLine+", replacing it with new -----"+ propertyName+"="+newTranslation + "-----\n");
			            		in.set(propAtLine-1, propertyName+"="+newTranslation);
			            	}
			            }			            
		                done=true;
		            }
	            }
	            
	            if(!done) {
	            	propAtLine=lineNumber;
	            	//System.out.println("Writing New -----" + propertyName+"="+newTranslation + "----- in "+ currentTransFile.getName()+"\n");
	            	logger.info("Writing New -----" + propertyName+"="+newTranslation + "----- in "+ currentTransFile.getName()+" at line: "+propAtLine+"\n");
	            	in.add(propAtLine, propertyName+"="+newTranslation);
	            }	            
	            Files.write(currentTransFile.toPath(), in, StandardCharsets.ISO_8859_1);
	            br.close();
	        }
     	}
     	catch(Exception e){
        	//System.out.print("Cant find " + propertyName);
            e.printStackTrace();
        }
    }
}    