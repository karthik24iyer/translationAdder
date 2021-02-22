package com.wellsfargo.coms.translationAdder;

import java.io.IOException;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;

import com.wellsfargo.coms.translationAdder.Readers.ExcelReader;
import com.wellsfargo.coms.translationAdder.Misc.FetchFileFormat;

public class App 
{
    public static void main( String[] args )
    {
    	final Logger logger = Logger.getLogger(App.class.getName());
    	ResourceBundle rb = ResourceBundle.getBundle("config");
        final String location = rb.getString("xcelLocation");
        String[] currentLangs;
    	String[][] translationData;
    	int maxRowCount=0;
    	// Instantiating Excel Sheet Reader
    	FetchFileFormat fff = new FetchFileFormat();
        ExcelReader sessionData = fff.getExcelFileFormat(location);
    	if(sessionData==null) {
    		System.exit(0);
    	}
    	try {
        	// Reading data from whole sheet
        	maxRowCount=sessionData.readSheet();
		} catch (IOException e) {
			e.printStackTrace();
		}
        
        //Fetching listed languages in Excel Sheet
        currentLangs = sessionData.getRowHeaderData();
        
        //Fetching listed languages in Excel Sheet
        translationData = sessionData.getSheetData();
        
        //Starting Translation Process !
        try {
        	System.out.println("App is running");
        	logger.info("App is running\n");
        	new RunIterator(currentLangs,translationData,maxRowCount);
        	System.out.println("Translations done");
        	logger.info("Translations done");
        }
        catch(Exception e) {
        	e.printStackTrace();
        }
      //for(int i=0;i<currentLangs.length && currentLangs[i]!=null; i++) { System.out.print(currentLangs[i] + "\t");}
        //System.out.println();
    }
}
