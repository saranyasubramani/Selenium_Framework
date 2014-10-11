package com.projectName.testutils.pages.projectNamePages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LmsScreen {

	protected By txtReleaseNote = By.xpath("//td[@id='ctl00_IDMMenun2']/table/tbody/tr/td");
	protected By ajxMyLeave = By.xpath("//td[@id='ctl00_IDMMenun1']/table/tbody/tr/td");
	
	public LmsScreen(WebDriver driver) {
		
	}
}
