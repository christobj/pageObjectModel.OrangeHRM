package com.orangeHRM.qa.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import com.orangeHRM.qa.base.TestBase;

public class TestUtil extends TestBase{
	
	public static long PAGE_LOAD_TIMEOUT = 40;
	public static long IMPLICIT_WAIT = 40;
	public static String LOGIN_TESTDATA_SHEET_PATH = System.getProperty("user.dir") + "\\src\\main\\java\\com\\orangeHRM\\qa\\testdata\\LoginTestData.xlsx";
	static Workbook book;
	static Sheet sheet;
	
	public static Object[][] getTestData(String testDataDocumentName, String sheetName) {
		FileInputStream file = null;
		try {
			file = new FileInputStream(testDataDocumentName);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		try {
			book = WorkbookFactory.create(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
		sheet = book.getSheet(sheetName);
		Object[][] data = new Object[sheet.getLastRowNum()][sheet.getRow(0).getLastCellNum()];
		for (int i = 0; i < sheet.getLastRowNum(); i++) {
			for (int k = 0; k < sheet.getRow(0).getLastCellNum(); k++) {
				data[i][k] = sheet.getRow(i + 1).getCell(k).toString();
			}
		}
		return data;
	}
	
	public static Object[] getTestDataSingleColumn(String testDataDocumentName, String sheetName) {
		FileInputStream file = null;
		try {
			file = new FileInputStream(testDataDocumentName);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		try {
			book = WorkbookFactory.create(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
		sheet = book.getSheet(sheetName);
		Object[] data = new Object[sheet.getLastRowNum()];
		for (int i = 0; i < sheet.getLastRowNum(); i++) {
			data[i] = sheet.getRow(i+1).getCell(0).toString();
		}
		return data;
	}
	
	public static void takeScreenshotAtEndOfTest() throws IOException {
		File sourceFile = ((TakesScreenshot) webDriver).getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(sourceFile, new File(System.getProperty("user.dir") + "/screenshots" 
				+ System.currentTimeMillis() + ".png"));
	}
}
