package com.projectName.testutils.pages.genericPages;

import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.projectName.testutils.genericutility.ExceptionHandler;
import com.projectName.testutils.seleniumutils.SeleniumWebDriver;

public class SearchPage extends SeleniumWebDriver{

	protected By lnkHome = By.xpath("//a[contains(text(),'Home')]/parent::li");
	protected By lnkAnnouncement = By.xpath("//a[contains(text(),'Announcement')]/parent::li");
	protected By lnkCelebrations = By.xpath("//a[contains(text(),'Celebrations')]/parent::li");
	protected By lnkClassifields = By.xpath("//a[contains(text(),'Classifieds')]/parent::li");
	
	
	
	/***
	 * Call to super constructor
	 */
	public SearchPage(WebDriver driver) {
		super(driver);
	}
	
	public boolean searchPage()throws ExceptionHandler, IOException{
		
		try{
			if(isElementPresent(lnkHome)){
				return true;
			}
			if(isElementPresent(lnkAnnouncement)){
				return true;
			}
			if(isElementPresent(lnkCelebrations)){
				return true;
			}
			if(isElementPresent(lnkClassifields)){
				return true;
			}
			return false;
		}catch(Exception e){
			throw new ExceptionHandler(e);
		}
		
	}
	
}
