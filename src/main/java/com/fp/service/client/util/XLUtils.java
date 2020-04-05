package com.fp.service.client.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class XLUtils {
	public static FileInputStream fi;
	public static FileOutputStream fo;
	public static Workbook wb;
	public static Sheet ws;
	public static Row row;
	public static Cell cell;
	public static CellStyle style;

	public static int getRowCount(String xlfile, String xlsheet) throws IOException {
		fi = new FileInputStream(xlfile);
		wb = new XSSFWorkbook(fi);
		ws = wb.getSheet(xlsheet);
		int rowcount = ws.getLastRowNum();
		wb.close();
		fi.close();
		return rowcount;
	}

	public static int getCellCount(String xlfile, String xlsheet, int rownum) throws IOException {
		fi = new FileInputStream(xlfile);
		wb = new XSSFWorkbook(fi);
		ws = wb.getSheet(xlsheet);
		row = ws.getRow(rownum);
		int cellcount = row.getLastCellNum();
		wb.close();
		fi.close();
		return cellcount;
	}

	public static String getCellData(String xlfile, String xlsheet, int rownum, int colnum) throws IOException {
		fi = new FileInputStream(xlfile);
		wb = new XSSFWorkbook(fi);
		ws = wb.getSheet(xlsheet);
		row = ws.getRow(rownum);
		cell = row.getCell(colnum);
		String data;
		try {
			data = cell.getStringCellValue();
		} catch (Exception e) {
			data = "";
		}
		wb.close();
		fi.close();
		return data;
	}

	public static void setCellData(String xlfile, String xlsheet, int rownum, int colnum, String data)
			throws IOException {
		fi = new FileInputStream(xlfile);
		wb = new XSSFWorkbook(fi);
		ws = wb.getSheet(xlsheet);
		row = ws.getRow(rownum);
		cell = row.createCell(colnum);
		cell.setCellValue(data);
		fo = new FileOutputStream(xlfile);
		wb.write(fo);
		wb.close();
		fi.close();
		fo.close();
	}

	public static Map<String, String> readDataFromXLFile(String filePath, List<String> sheetNames) throws IOException {
		Map<String, String> testData = new HashMap<String, String>();

		try {
			System.out.println("File Path ::: "+filePath);
			ClassLoader classLoader = ClassLoader.getSystemClassLoader();
			 
			File file = new File(classLoader.getResource(filePath).getFile());
			
			FileInputStream inputStream = new FileInputStream(file);

			// Create Workbook instance holding reference to .xlsx file
			XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
			for (String sheetName : sheetNames) {

				// Get first/desired sheet from the workbook
				XSSFSheet sheet = workbook.getSheet(sheetName);

				// Iterate through each rows one by one
				Iterator<Row> rowIterator = sheet.iterator();
				
				int rowCount = 0;
				
				while (rowIterator.hasNext()) {
					if(rowCount == 0){
						rowCount ++;
						continue;
					}
					Row row = rowIterator.next();
					// For each row, iterate through all the columns
					Iterator<Cell> cellIterator = row.cellIterator();
					int cellCount = 0;
					String key = null;
					String value = null;
					while (cellIterator.hasNext()) {
						Cell cell = cellIterator.next();
						// Check the cell type and format accordingly
						switch (cellCount) {
						case 0:
							key = cell.getStringCellValue();
							cellCount++;
							break;
						case 1:
							value = cell.getStringCellValue();
							cellCount++;
							break;
						}
					}
					testData.put(key, value);
				}
			}
			inputStream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return testData;
	}
}
