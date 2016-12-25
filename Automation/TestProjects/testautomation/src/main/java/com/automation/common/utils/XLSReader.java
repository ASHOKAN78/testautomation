package com.automation.common.utils;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class XLSReader {

	private String filepath;
	FileInputStream fis;
	XSSFWorkbook wb;

	//Initialize the Excel
	public XLSReader(String excelfilepath) throws IOException {
		filepath = excelfilepath;
		fis = new FileInputStream(filepath);
		wb = new XSSFWorkbook(fis);
	}

	// Get Row count
	public int getRowCount(String sheetName) {
		return wb.getSheet(sheetName).getLastRowNum();

	}

	//Get Column count
	public int getColumnCount(String sheetName){
		return wb.getSheet(sheetName).getRow(0).getLastCellNum();
	}

	// Read the Cell
	@SuppressWarnings("static-access")
	public String readCellValue(String sheetName, int rowNumber, int columnNumber) {
		String cellText = "";
		XSSFSheet sheet = wb.getSheet(sheetName);
		XSSFCell cell = sheet.getRow(rowNumber).getCell(columnNumber);
		if(null != cell && cell.getCellType() == cell.CELL_TYPE_STRING) {
			cellText = cell.getStringCellValue();
		}else if(null != cell && cell.getCellType() == cell.CELL_TYPE_NUMERIC){
			cellText = String.valueOf(cell.getNumericCellValue());
		}else if(null != cell && cell.getCellType() == cell.CELL_TYPE_BLANK){
			cellText = "";
		}
		return cellText;
	}

	public void writeToCell(String sheetName, int rowNumber, int columnNumber, String value) {
		XSSFSheet sheet = wb.getSheet(sheetName);
		sheet.getRow(rowNumber).getCell(columnNumber).setCellValue(value);
	}

	public void saveAndCloseExcel( String filePath) throws IOException{
		FileOutputStream fos = new FileOutputStream(filePath);
		wb.write(fos);
		fos.close();
		fis.close();
	}

	public Iterator<Object[]> readTestData(String sheetName, String scenarioName) {

		int rowCount = getRowCount(sheetName);
		int colCount = getColumnCount(sheetName);

		List<Object[]> al = new ArrayList<Object[]>();

		for(int currentRow =1; currentRow <=rowCount; currentRow++){

			String flag=readCellValue(sheetName, currentRow, 2);
			String script=readCellValue(sheetName, currentRow, 1);
			if(flag.equalsIgnoreCase("YES") && script.equalsIgnoreCase(scenarioName)){
				// map
				Map<String, String> hmap = new HashMap<String,String>();
				//	object array
				Object[] x=new Object[1];

				for(int currentCol=0;currentCol<colCount;currentCol++){

					String key = readCellValue(sheetName, 0, currentCol);
					String value = readCellValue(sheetName, currentRow, currentCol);			
					hmap.put(key,value);

				}			

				//add object array to list
				x[0]=hmap;
				al.add(x);
			}

		}
		return al.iterator();

	}

	public Object[][] readTestDataPOM() throws IOException {

		XSSFSheet sheet = wb.getSheetAt(0);
		String globalSheetName = wb.getSheetAt(0).getSheetName();
		int rowCount = getRowCount(globalSheetName);
		int colCount = getColumnCount(globalSheetName);
		int actualRowCount = 0;
		for (int currentRow = 1; currentRow <= rowCount; currentRow++) {
			if(sheet.getRow(currentRow).getCell(1).getStringCellValue().equalsIgnoreCase("YES")) actualRowCount++;
		}

		Object[][] testing = new Object[actualRowCount][1];

		int x = 0;
		for (int currentRow = 1; currentRow <= rowCount; currentRow++) {
			if(sheet.getRow(currentRow).getCell(1).getStringCellValue().equalsIgnoreCase("NO")) continue;
			HashMap<String, HashMap<String, String>> finalValue = new HashMap<String, HashMap<String, String>>();
			for (int currentCol = 0; currentCol < colCount; currentCol++) {
				String keyWord = sheet.getRow(currentRow).getCell(currentCol).getStringCellValue();
				String sheetName = sheet.getRow(0).getCell(currentCol).getStringCellValue();

				if (wb.getSheet(sheetName) != null) {
					finalValue.put(sheetName, getSheetData(sheetName, keyWord));
				}
			}
			testing[x][0] = finalValue;
			x = x + 1;
		}

		return testing;
	}

	public HashMap<String, String> getSheetData(String sheetName, String keyword) {
		HashMap<String, String> returnValue = new HashMap<String, String>();
		try {
			//Workbook w = Workbook.getWorkbook(new File(filepath));
			XSSFSheet sheet = wb.getSheet(sheetName);
			int rowNum = findCell(sheetName,keyword);
			int colCount = getColumnCount(sheetName);
			for (int currentCol = 0; currentCol < colCount; currentCol++) {
				String val = readCellValue(sheetName,rowNum,currentCol);
				String columnName = readCellValue(sheetName,0,currentCol);
				//String val = sheet.getRow(rowNum).getCell(currentCol).getStringCellValue();
				//String columnName = sheet.getRow(0).getCell(currentCol).getStringCellValue();
				returnValue.put(columnName, val);
			}
		} catch (Exception e) {
			throw new RuntimeException("Error while getting sheet data");
		}
		return returnValue;
	}

	public int findCell(String sheetName, String text) {  
		int currentRow = 1;
		int rowCount = getRowCount(sheetName);
		for (; currentRow < rowCount; currentRow++) {
			String keyWord = wb.getSheet(sheetName).getRow(currentRow).getCell(0).getStringCellValue();
			if(text.equals(keyWord))
				return currentRow;
		}
		return currentRow;
	}
}
