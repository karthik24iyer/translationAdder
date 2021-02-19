package com.wellsfargo.coms.translationAdder;

import java.io.IOException;
import org.apache.log4j.Logger;
import com.wellsfargo.coms.translationAdder.Readers.SheetReader;

public class App 
{
    public static void main( String[] args )
    {
    	final Logger logger = Logger.getLogger(App.class.getName());
    	String[] currentLangs;
    	String[][] translationData;
    	int maxRowCount=0;
    	// Instantiating Excel Sheet Reader
    	SheetReader sessionData = new SheetReader();
        try {
        	// Reading data from whole sheet
        	maxRowCount=sessionData.readSheet();
		} catch (IOException e) {
			e.printStackTrace();
		}
        System.out.println();
        
        //Fetching listed languages in Excel Sheet
        currentLangs = sessionData.getRowHeaderData();
        
        //Fetching listed languages in Excel Sheet
        translationData = sessionData.getSheetData();
        
        //Starting Translation Process !
        try {
        	System.out.println("App is running");
        	logger.info("App is running");
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
