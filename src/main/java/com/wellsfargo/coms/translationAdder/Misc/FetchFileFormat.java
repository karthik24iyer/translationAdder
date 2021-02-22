package com.wellsfargo.coms.translationAdder.Misc;

import org.apache.log4j.Logger;

import com.wellsfargo.coms.translationAdder.App;
import com.wellsfargo.coms.translationAdder.Readers.ExcelReader;
import com.wellsfargo.coms.translationAdder.Readers.XlsReader;
import com.wellsfargo.coms.translationAdder.Readers.XlsxReader;

public class FetchFileFormat {
	
	public ExcelReader getExcelFileFormat(String location) {	
		final Logger logger = Logger.getLogger(App.class.getName());
		if(location.contains(".xlsx")) {
        	XlsxReader sessionData = new XlsxReader();
        	return sessionData;
        }
		else if(location.contains(".xls")) {
        	XlsReader sessionData = new XlsReader();
        	return sessionData;
        }
		if(location.contains(".csv")) {
			System.out.println("CSV format not yet supported\n");
    		logger.error("CSV format not yet supported\n");
        	return null;
        }
   		System.out.println("Unable to fetch given Excel file from \""+location+"\". Please check file path and format\n");
		logger.error("Unable to fetch given Excel file. Please check file path and format\n");
		return null;
	}

}