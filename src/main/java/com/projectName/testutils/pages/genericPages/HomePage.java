package com.projectName.testutils.pages.genericPages;


import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

import com.projectName.testutils.genericutility.ExceptionHandler;

import com.projectName.testutils.pages.projectNamePages.HomeScreen;
import com.projectName.testutils.seleniumutils.SeleniumWebDriver;


public class HomePage extends SeleniumWebDriver {

	/***
	 * Call to super constructor
	 */
	public HomePage(WebDriver driver) {
		super(driver);
	}

	public String getLoggedInUserName() {
		// TODO Auto-generated method stub
		return null;
	}

	public void logOut() {
		// TODO Auto-generated method stub

	}

	

	public LoginPage navigateToLoginPage() throws ExceptionHandler {
		LoginPage createRequestPage = PageFactory.initElements(driver,
				LoginPage.class);
		return createRequestPage;

	}

	public HomeScreen navigateToHomePage()throws ExceptionHandler{
		HomeScreen createHomePage = PageFactory.initElements(driver,
				HomeScreen.class);
		return createHomePage;
	}
	
	public SearchPage navigateToSearchPage()throws ExceptionHandler{
		SearchPage searchPage = PageFactory.initElements(driver,
				SearchPage.class);
		return searchPage;
	}

}
