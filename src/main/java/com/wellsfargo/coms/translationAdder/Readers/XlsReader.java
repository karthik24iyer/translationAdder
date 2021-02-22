package com.wellsfargo.coms.translationAdder.Readers;
import com.wellsfargo.coms.translationAdder.Languages.LanguagesList;
import java.io.File;  
import java.io.FileInputStream;  
import java.io.IOException;
import java.util.ResourceBundle;

import org.apache.poi.hssf.usermodel.HSSFSheet;  
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.wellsfargo.coms.translationAdder.App;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.FormulaEvaluator;  
import org.apache.poi.ss.usermodel.Row;  

public class XlsReader extends ExcelReader {  
	
	final Logger logger = Logger.getLogger(App.class.getName());
	ResourceBundle rb = ResourceBundle.getBundle("config");
    private String location = rb.getString("xcelLocation");
    private int count = 0, i=0;
    private int langCount = LanguagesList.langFormats.length+2;
    private String[] rowHeader = new String[langCount];
    private String[][] sheetData = new String[100][langCount];
	
    public String[] getLanguageHeader() {
    	return this.rowHeader;
    }
    
    public String[] getRowHeaderData() {
    	return this.rowHeader;
    }
    
    public String[][] getSheetData() {
    	return this.sheetData;
    }
    
    public int readSheet() throws IOException {  

		FileInputStream fis=new FileInputStream(new File(location));  
		HSSFWorkbook wb=new HSSFWorkbook(fis);    
		HSSFSheet sheet=wb.getSheetAt(0);  
		FormulaEvaluator formulaEvaluator=wb.getCreationHelper().createFormulaEvaluator();  
		for(Row row: sheet)
		{  
			for(Cell cell: row)
			{  
				switch(formulaEvaluator.evaluateInCell(cell).getCellType())  
				{  
					case NUMERIC:
						//System.out.print(cell.getStringCellValue()+ "\t\t");
						//logger.info(cell.getStringCellValue()+ "\t\t");
						sheetData[count-1][i]=cell.getStringCellValue();
						break;  
					case STRING:
						if(count==0) {
							rowHeader[i]=cell.getStringCellValue();
						}
						else {
							sheetData[count-1][i]=cell.getStringCellValue();
						}
						//System.out.print(cell.getStringCellValue()+ "\t\t");
						//logger.info(cell.getStringCellValue()+ "\t\t");
						break;
					default:
						//System.out.print("Not able to identify datatype");
						logger.error("Not able to identify datatype");
						break;  
				}  
				i++;		
			}  
			count++; i=0;
			//System.out.println();
		}
		wb.close();
		return count;
	}  
}  