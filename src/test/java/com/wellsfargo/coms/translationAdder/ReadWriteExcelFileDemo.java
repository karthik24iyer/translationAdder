//package com.wellsfargo.coms.translationAdder;
//
//import java.io.FileInputStream;
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.io.InputStream;
//import java.util.Iterator;
//
//import org.apache.poi.hssf.usermodel.HSSFCell;
//import org.apache.poi.hssf.usermodel.HSSFRow;
//import org.apache.poi.hssf.usermodel.HSSFSheet;
//import org.apache.poi.hssf.usermodel.HSSFWorkbook;
//import org.apache.poi.ss.usermodel.CellType;
//import org.apache.poi.xssf.usermodel.XSSFCell;
//import org.apache.poi.xssf.usermodel.XSSFRow;
//import org.apache.poi.xssf.usermodel.XSSFSheet;
//import org.apache.poi.xssf.usermodel.XSSFWorkbook;
//
//public class ReadWriteExcelFileDemo {
//
//	private static String locationS = "C:\\Users\\Karthik\\eclipse-workspace\\translationAdder\\src\\main\\java\\resources\\tmpTranslations.xls";
//	private static String locationSX = "C:\\Users\\Karthik\\eclipse-workspace\\translationAdder\\src\\main\\java\\resources\\tmpTranslations.xlsx";
//	public static void readXLSFile() throws IOException
//	{
//		InputStream ExcelFileToRead = new FileInputStream(locationS);
//		HSSFWorkbook wb = new HSSFWorkbook(ExcelFileToRead);
//
//		HSSFSheet sheet=wb.getSheetAt(0);
//		HSSFRow row; 
//		HSSFCell cell;
//
//		Iterator rows = sheet.rowIterator();
//
//		while (rows.hasNext())
//		{
//			row=(HSSFRow) rows.next();
//			Iterator cells = row.cellIterator();
//			
//			while (cells.hasNext())
//			{
//				cell=(HSSFCell) cells.next();
//		
//				if (cell.getCellType() == CellType.STRING)
//				{
//					System.out.print(cell.getStringCellValue()+" ");
//				}
//				else if(cell.getCellType() == CellType.NUMERIC)
//				{
//					System.out.print(cell.getNumericCellValue()+" ");
//				}
//				else
//				{
//				}
//			}
//			System.out.println();
//		}
//	
//	}
//	
//	public static void writeXLSFile() throws IOException {
//		
//		String excelFileName = locationS;
//		System.out.println(excelFileName);
//		String sheetName = "Sheet1";
//		HSSFWorkbook wb = new HSSFWorkbook();
//		System.out.println("Hi");
//		HSSFSheet sheet = wb.createSheet(sheetName) ;
//
//		for (int r=0;r < 5; r++ )
//		{
//			HSSFRow row = sheet.createRow(r);
//
//			for (int c=0;c < 5; c++ )
//			{
//				HSSFCell cell = row.createCell(c);
//				
//				cell.setCellValue("Cell "+r+" "+c);
//			}
//		}
//		FileOutputStream fileOut = new FileOutputStream(excelFileName);
//		
//		wb.write(fileOut);
//		fileOut.flush();
//		fileOut.close();
//	}
//	
//	public static void readXLSXFile() throws IOException
//	{
//		InputStream ExcelFileToRead = new FileInputStream(locationSX);
//		XSSFWorkbook  wb = new XSSFWorkbook(ExcelFileToRead);
//		
//		XSSFWorkbook test = new XSSFWorkbook(); 
//		
//		XSSFSheet sheet = wb.getSheetAt(0);
//		XSSFRow row; 
//		XSSFCell cell;
//
//		Iterator rows = sheet.rowIterator();
//
//		while (rows.hasNext())
//		{
//			row=(XSSFRow) rows.next();
//			Iterator cells = row.cellIterator();
//			while (cells.hasNext())
//			{
//				cell=(XSSFCell) cells.next();
//		
//				if (cell.getCellType() == CellType.STRING)
//				{
//					System.out.print(cell.getStringCellValue()+" ");
//				}
//				else if(cell.getCellType() == CellType.NUMERIC)
//				{
//					System.out.print(cell.getNumericCellValue()+" ");
//				}
//				else
//				{
//				}
//			}
//			System.out.println();
//		}
//	
//	}
//	
//	public static void writeXLSXFile() throws IOException {
//		
//		String excelFileName = locationSX;
//
//		String sheetName = "Sheet1";
//
//		XSSFWorkbook wb = new XSSFWorkbook();
//		XSSFSheet sheet = wb.createSheet(sheetName) ;
//		for (int r=0;r < 5; r++ )
//		{
//			XSSFRow row = sheet.createRow(r);
//			for (int c=0;c < 5; c++ )
//			{
//				XSSFCell cell = row.createCell(c);
//	
//				cell.setCellValue("Cell "+r+" "+c);
//			}
//		}
//
//		FileOutputStream fileOut = new FileOutputStream(excelFileName);
//
//		wb.write(fileOut);
//		fileOut.flush();
//		fileOut.close();
//	}
//
//	public static void main(String[] args) throws IOException {
//		System.out.println("Write Start");
//		writeXLSFile();
//		System.out.println("Write Done");
//		readXLSFile();
//		
//		writeXLSXFile();
//		readXLSXFile();
//
//	}
//
//}