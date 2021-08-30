package com.orangeHRM.qa.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.orangeHRM.qa.base.TestBase;

public class LoginPage extends TestBase{
	
	@FindBy(id="txtUsername")
	private WebElement userName;
	
	@FindBy(id="txtPassword")
	private WebElement password;
	
	@FindBy(id="btnLogin")
	private WebElement loginBtn;
	
	@FindBy(xpath="//a[contains(text(),'Forgot your password?')]")
	private WebElement forgetPassword;
	
	@FindBy(id="spanMessage")
	private WebElement errorMessage;
	
	public LoginPage() {
		PageFactory.initElements(webDriver, this);
	}
	
	public String getLoginPageTitle() {
		return webDriver.getTitle();
	}
	
	public DashboardPage login(String user, String pwd) {
		userName.sendKeys(user);
		password.sendKeys(pwd);
		loginBtn.click();
		return new DashboardPage();
	}
	
	public boolean errorMessagePresence() {
		return errorMessage.isDisplayed();
	}
	
	public String getErrorMessage() {
		return errorMessage.getText();
	}
	
	public boolean forgetPasswordPresence() {
		return forgetPassword.isDisplayed();
	}
	
	public ForgetPasswordPage forgetPasswordClick() {
		forgetPassword.click();
		return new ForgetPasswordPage();
	}
}
