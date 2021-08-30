package com.orangeHRM.qa.test;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.orangeHRM.qa.base.TestBase;
import com.orangeHRM.qa.pages.LoginPage;
import com.orangeHRM.qa.utils.TestUtil;

public class LoginPageTest extends TestBase{
	
	LoginPage loginPage;
	String invalidUserNameSheetName = "empty credentials";
	String invalidPasswordSheetName = "invalid credentials";
	String validCredentials = "valid credentials";
	String testDataDocumentName = TestUtil.LOGIN_TESTDATA_SHEET_PATH;
	
	public LoginPageTest() {
		super();
	}
	
	@BeforeMethod
	public void setUp() {
		initialize();
		loginPage = new LoginPage();
	}
	
	@AfterMethod
	public void tearDown() {
		webDriver.close();
	}
	
	@Test
	public void loginPageTitleTest() {
		Assert.assertEquals(loginPage.getLoginPageTitle(), "OrangeHRM");
	}
	
	@Test (dataProvider = "LoginDataProvider")
	public void loginTest(String user, String pwd) {
		loginPage.login(user, pwd);
	}
	
	@DataProvider
	public Object[][] LoginDataProvider() {
			Object data[][] = TestUtil.getTestData(testDataDocumentName, validCredentials);
			return data;
	}
	
	@Test (dataProvider="invalidLoginDataProvider")
	public void invalidLoginTest(String user, String pwd) {
		loginPage.login(user,pwd);
		Assert.assertEquals(loginPage.errorMessagePresence(), true);
		Assert.assertEquals(loginPage.getErrorMessage(), "Invalid credentials");
	}
	
	@DataProvider
	public Object[][] invalidLoginDataProvider() {
			Object data[][] = TestUtil.getTestData(testDataDocumentName, invalidPasswordSheetName);
			return data;
	}
	
	@Test (dataProvider="invalidUsernameDataProvider")
	public void invalidUsernameTest(String pwd) {
		loginPage.login("", pwd);
		Assert.assertEquals(loginPage.errorMessagePresence(), true);
		Assert.assertEquals(loginPage.getErrorMessage(), "Username cannot be empty");
	}
	
	@DataProvider
	public Object[] invalidUsernameDataProvider() {
			Object data[] = TestUtil.getTestDataSingleColumn(testDataDocumentName, invalidUserNameSheetName);
			return data;
	}
	
	@Test
	public void forgetPasswordTest() {
		Assert.assertEquals(loginPage.forgetPasswordPresence(), true);
		loginPage.forgetPasswordClick();
	}
}
