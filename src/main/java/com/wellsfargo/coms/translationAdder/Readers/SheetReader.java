package com.wellsfargo.coms.translationAdder.Readers;

import java.io.File;  
import java.io.FileInputStream;  
import java.io.IOException;
import java.util.ResourceBundle;

import org.apache.poi.xssf.usermodel.XSSFSheet;  
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.FormulaEvaluator;  
import org.apache.poi.ss.usermodel.Row;  

public class SheetReader {  
	ResourceBundle rb = ResourceBundle.getBundle("config");
    private String location = rb.getString("xcelLocation");
    private int count = 0, i=0;
    private String[] rowHeader = new String[16];
    private String[][] sheetData = new String[50][16];
	
    public String[] getLanguageHeader() {
    	return this.rowHeader;
    }
    
    public String[] getRowHeaderData() {
    	return this.rowHeader;
    }
    
    public String[][] getSheetData() {
    	return this.sheetData;
    }
    
    public void readSheet() throws IOException {  

		FileInputStream fis=new FileInputStream(new File(location));  
		XSSFWorkbook wb=new XSSFWorkbook(fis);    
		XSSFSheet sheet=wb.getSheetAt(0);  
		FormulaEvaluator formulaEvaluator=wb.getCreationHelper().createFormulaEvaluator();  
		for(Row row: sheet)
		{  
			for(Cell cell: row)
			{  
				switch(formulaEvaluator.evaluateInCell(cell).getCellType())  
				{  
					case NUMERIC:
						System.out.print(cell.getNumericCellValue()+ "\t\t");   
						break;  
					case STRING:
						if(count==0) {
							rowHeader[i]=cell.getStringCellValue();
						}
						else {
							sheetData[count-1][i]=cell.getStringCellValue();
						}
						System.out.print(cell.getStringCellValue()+ "\t\t");  
						break;
					default:
						System.out.print("Not able to identify datatype");
						break;  
				}  
				i++;		
			}  
			count++; i=0;
			System.out.println();  
		}
		wb.close();
	}  
}  