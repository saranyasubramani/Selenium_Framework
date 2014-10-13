package com.projectName.testutils.seleniumutils;


import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
import org.testng.Reporter;

import com.projectName.testutils.genericutility.Constants;
import com.projectName.testutils.genericutility.ExceptionHandler;
import com.thoughtworks.selenium.Selenium;

public class SeleniumWebDriver {

	/**
	 * Creating the web driver object to be used
	 */
	protected static WebDriver driver;
	WebDriverWait wait;
	Boolean result = true;
	public String returnString="";

	public SeleniumWebDriver(WebDriver driver) {
		SeleniumWebDriver.driver = driver;
	}

	/**
	 * Veriy the presence of a text in the page.
	 * 
	 * @param driver
	 * @param text
	 * @return true/false
	 * @throws IOException 
	 */
	public boolean isTextPresent(String text) throws IOException {
		try {
		status = "done";
		result= driver.getPageSource().contains(text);
		}
		
		catch (Exception e) {
			status = "Fail";
			File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
			
			String workingdirectory = System.getProperty("user.dir");
			
			File scrFile1 = new File(workingdirectory +"/custom-test-report/Failure_Screenshot/"+getCallingMethodAndLineNumber()+".jpg");
			
			FileUtils.copyFile(scrFile, scrFile1);
			result = false;
		}
		
		logCustomMessage().setAttribute(getCurrentDateAndTime(), getCustomAttributeValue("isTextPresent", text.toString(), empty, status, empty, getCallingMethodAndLineNumber()));
		return result;
	}

	/**
	 * Veriy the presence of a element in the page.
	 * 
	 * @param By
	 * @param text
	 * @return true/false
	 * @throws IOException 
	 */
	public boolean isElementPresent(By element) throws IOException {
		boolean exists = true;
		try {
			exists = driver.findElement(element).isDisplayed();
			status = "done";
		} catch (Exception e) {
			status = "Fail";
			File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
			
			String workingdirectory = System.getProperty("user.dir");
			
			File scrFile1 = new File(workingdirectory +"/custom-test-report/Failure_Screenshot/"+getCallingMethodAndLineNumber()+".jpg");
			
			FileUtils.copyFile(scrFile, scrFile1);
			exists = false;
		}
		
		logCustomMessage().setAttribute(getCurrentDateAndTime(), getCustomAttributeValue("isElementPresent", element.toString(), empty, status, empty, getCallingMethodAndLineNumber()));
		return exists;
	}

	/**
	 * Wait for page to load
	 */
	public void causeMinorTimeDelay() {
		driver.manage().timeouts()
				.implicitlyWait(Constants.DELAY_TIME, TimeUnit.SECONDS);
	}

	/**
	 * Wait for page to load
	 */
	public void causeTimeDelay() {
		try {
			int counter = 0;
			Thread.sleep(2000);
			while (true) {
				String ajaxIsComplete = ((JavascriptExecutor) driver)
						.executeScript("return Ajax.activeRequestCount")
						.toString();
				if (Integer.parseInt(ajaxIsComplete) == 0)
					break;
				if (counter == 100)
					break;
				Thread.sleep(100);
			}
		} catch (Exception e) {

		}

	}

	/**
	 * The screen shot is captured
	 * 
	 * @param driver
	 * @return
	 */
	public static File takeScreenshot(WebDriver driver) {
		return ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
	}

	public boolean waitForElement(final By ajaxElementName, int timeOutValue)
			throws ExceptionHandler {
		driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
		wait = new WebDriverWait(driver, timeOutValue);
		try {
			ExpectedCondition<Boolean> e = new ExpectedCondition<Boolean>() {
				public Boolean apply(WebDriver driver) {
					return driver.findElement(ajaxElementName).isDisplayed();

				}
			};
			wait.until(e);
			return true;
		} catch (Exception e) {
			driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
			throw new ExceptionHandler("The Element '" + ajaxElementName
					+ "' did not appear  within " + timeOutValue + " ms."
					+ timeOutValue * 1000);

		}

	}

	// Wait for Element to Load
	public void waitForElementToLoad(Selenium selenium, String elementId)
			throws InterruptedException {
		int i = 0;
		while (!selenium.isElementPresent(elementId)) {
			i++;
			Thread.sleep(3000);
			if (i == 9) {
				// Assert.fail("Time out :-CounldNotFind the Element With ID  : "+elementId
				// );
				break;
			}
		}
	}

	public boolean sendKeys(By elementLocator, String value) throws IOException {
		boolean flag;
		try {
			driver.manage().timeouts().implicitlyWait(45, TimeUnit.SECONDS);
			driver.findElement(elementLocator).clear();
			driver.findElement(elementLocator).sendKeys(value);
			status = "done";
			flag = true;
		} catch (Exception e) {
			status = "Fail";
			File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
			
			String workingdirectory = System.getProperty("user.dir");
			
			File scrFile1 = new File(workingdirectory +"/custom-test-report/Failure_Screenshot/"+getCallingMethodAndLineNumber()+".jpg");
			
			FileUtils.copyFile(scrFile, scrFile1);
			flag = false;
		}
		
		logCustomMessage().setAttribute(getCurrentDateAndTime(), getCustomAttributeValue("Send Keys", elementLocator.toString(), value, status, empty, getCallingMethodAndLineNumber()));
		
		return flag;
	}

	public boolean click(final By ajaxElementName) throws IOException {
		try {
			waitForElement(ajaxElementName, Constants.AVG_WAIT_TIME_FOR_ELEMENT);
			if (driver.findElement(ajaxElementName).isDisplayed()
					&& driver.findElement(ajaxElementName).isEnabled()) {
				driver.findElement(ajaxElementName).click();
				
				status = "done";
				
				
				
			} else {
				
				
				result = false;
			}
			
		} catch (ExceptionHandler e) {
			e.printStackTrace();
			status = "Fail";
			
			File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
			
			String workingdirectory = System.getProperty("user.dir");
			
			File scrFile1 = new File(workingdirectory +"/custom-test-report/Failure_Screenshot/"+getCallingMethodAndLineNumber()+".jpg");
			
			FileUtils.copyFile(scrFile, scrFile1);
			
			result = false;
		}
		
		logCustomMessage().setAttribute(getCurrentDateAndTime(), getCustomAttributeValue("Click", ajaxElementName.toString(), empty, status, empty, getCallingMethodAndLineNumber()));
		
		return result;
	}

	/**
	 * isChecked function to verify if the AJAX based Checkbox is checked
	 * 
	 * @param selenium
	 * @param ajaxCheckboxName
	 *            (Name of the ajax Checkbox)
	 * @throws IOException 
	 * @throws MyException
	 * 
	 * @since March 04, 2013
	 */
	public boolean isChecked(final By ajaxCheckboxName) throws ExceptionHandler, IOException {
		try{
		if (waitForElement(ajaxCheckboxName,
				Constants.AVG_WAIT_TIME_FOR_ELEMENT)) {
			driver.findElement(ajaxCheckboxName).isSelected();
			boolean checkBoxStatus = driver.findElement(ajaxCheckboxName)
					.isSelected();
			if (checkBoxStatus) {
				status = "done";
				return true;
			} else {
				status = "Fail";
				result = false;
				return false;
			}
		} else {
			status = "Fail";
			result = false;
			return false;
		}
		}
		catch (Exception e) {

			e.printStackTrace();
			status = "Fail";
			
			File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
			
			String workingdirectory = System.getProperty("user.dir");
			
			File scrFile1 = new File(workingdirectory +"/custom-test-report/Failure_Screenshot/"+getCallingMethodAndLineNumber()+".jpg");
			
			FileUtils.copyFile(scrFile, scrFile1);
			
			result = false;
		}

		logCustomMessage().setAttribute(getCurrentDateAndTime(), getCustomAttributeValue("Check", ajaxCheckboxName.toString(), empty, status, empty, getCallingMethodAndLineNumber()));
		return result;
	}


	public void waitForPageToLoad() throws IOException {
		String element="";
		try {
			int counter = 0;
			
			Thread.sleep(1000);
			while (true) {
				String ajaxIsComplete = ((JavascriptExecutor) driver)
						.executeScript("return Ajax.activeRequestCount")
						.toString();
				if (Integer.parseInt(ajaxIsComplete) == 0)
					break;
				if (counter == 20)
					break;
				Thread.sleep(100);
				counter++;
			}
			status = "done";
		} catch (Exception e) {

			e.printStackTrace();
			status = "Fail";
			
			File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
			
			String workingdirectory = System.getProperty("user.dir");
			
			File scrFile1 = new File(workingdirectory +"/custom-test-report/Failure_Screenshot/"+getCallingMethodAndLineNumber()+".jpg");
			
			FileUtils.copyFile(scrFile, scrFile1);
			
			result = false;
		}

		logCustomMessage().setAttribute(getCurrentDateAndTime(), getCustomAttributeValue("Wait for page to load", element.toString(), empty, status, empty, getCallingMethodAndLineNumber()));		
	}
	

	// Mouse over Method
	public void mouseOver(WebElement element) throws IOException {
		try {
		String code = "var fireOnThis = arguments[0];"
				+ "var evObj = document.createEvent('MouseEvents');"
				+ "evObj.initEvent( 'mouseover', true, true );"
				+ "fireOnThis.dispatchEvent(evObj);";
		((JavascriptExecutor) driver).executeScript(code, element);
		status = "done";
		}
		catch (RuntimeException e) {
			e.printStackTrace();
			status = "Fail";
			
			File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
			
			String workingdirectory = System.getProperty("user.dir");
			
			File scrFile1 = new File(workingdirectory +"/custom-test-report/Failure_Screenshot/"+getCallingMethodAndLineNumber()+".jpg");
			
			FileUtils.copyFile(scrFile, scrFile1);
			
			result = false;
		}
		
		logCustomMessage().setAttribute(getCurrentDateAndTime(), getCustomAttributeValue("Mouse over", element.toString(), empty, status, empty, getCallingMethodAndLineNumber()));

	}

	public boolean select(By listName, String valueForSelection) throws IOException {
		valueForSelection = valueForSelection != null ? valueForSelection
				.trim() : "";
		try {
			waitForElement(listName, Constants.AVG_WAIT_TIME_FOR_ELEMENT);
			if (driver.findElement(listName).isDisplayed()) {
				Select elSelect = new Select(driver.findElement(listName));
				elSelect.selectByVisibleText(valueForSelection);
				status = "done";
				return true;
			} else {
				return false;
			}
		} catch (RuntimeException e) {
			e.printStackTrace();
			
			return false;
		} catch (ExceptionHandler e) {
			e.printStackTrace();
			status = "Fail";
			
			File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
			
			String workingdirectory = System.getProperty("user.dir");
			
			File scrFile1 = new File(workingdirectory +"/custom-test-report/Failure_Screenshot/"+getCallingMethodAndLineNumber()+".jpg");
			
			FileUtils.copyFile(scrFile, scrFile1);
			
			result = false;
		}

		logCustomMessage().setAttribute(getCurrentDateAndTime(), getCustomAttributeValue("Select", listName.toString(), valueForSelection, status, empty, getCallingMethodAndLineNumber()));
		
		return result;
	}

	public String getText(By elementName, int wait) throws ExceptionHandler, IOException {

		try {
			if (waitForElement(elementName, wait)) {
				returnString=driver.findElement(elementName).getText();
				status = "done";
			}
		} catch (ExceptionHandler e) {
			e.printStackTrace();
			status = "Fail";
			
			File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
			
			String workingdirectory = System.getProperty("user.dir");
			
			File scrFile1 = new File(workingdirectory +"/custom-test-report/Failure_Screenshot/"+getCallingMethodAndLineNumber()+".jpg");
			
			FileUtils.copyFile(scrFile, scrFile1);
			
			result = false;
		}
		
		logCustomMessage().setAttribute(getCurrentDateAndTime(), getCustomAttributeValue("Store Text", elementName.toString(), empty, status, empty, getCallingMethodAndLineNumber()));
		
		return returnString;
	}

	public String getValue(By elementName) throws ExceptionHandler, IOException {

		try {
			if (waitForElement(elementName, Constants.AVG_WAIT_TIME_FOR_ELEMENT)) {
				returnString=driver.findElement(elementName).getAttribute("value");
				status = "done";
			} else {
				return "";
			}
		} catch (ExceptionHandler e) {
			e.printStackTrace();
			status = "Fail";
			
			File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
			
			String workingdirectory = System.getProperty("user.dir");
			
			File scrFile1 = new File(workingdirectory +"/custom-test-report/Failure_Screenshot/"+getCallingMethodAndLineNumber()+".jpg");
			
			FileUtils.copyFile(scrFile, scrFile1);
			
			result = false;
		}
		
		logCustomMessage().setAttribute(getCurrentDateAndTime(), getCustomAttributeValue("Store Value", elementName.toString(), empty, status, empty, getCallingMethodAndLineNumber()));
		
		return returnString;
	}

	//Report Part
	private final String deliminator = "####";
	private final String empty = "";
	
	private final String dot = ".";	
	
	private String status = null;
	

	private ITestResult logCustomMessage() {
		return Reporter.getCurrentTestResult();
	}
	
	/**
	 * used for get the calling method name with line number
	 * @return
	 */
	private String getCallingMethodAndLineNumber(){
		StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
		
		String callingMethodWithLineNumber = stackTraceElements[3].getClassName() + dot + stackTraceElements[3].getMethodName() + dot + stackTraceElements[3].getLineNumber() ;
		
		return callingMethodWithLineNumber;
	}
	
	
	/**
	 * This method returns the current date and time in format HH-mm-ss.SSS
	 * 
	 * @return time - in the above mentioned format
	 */
	private static String getCurrentDateAndTime() {
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss.SSS");
		Date date = new Date();
		String time = sdf.format(date);
		return time;
	}
	
	/**
	 * used to get the custom attribute value
	 * @param operation
	 * @param elementLocator1
	 * @param optional
	 * @param status
	 * @param screenShot
	 * @param callingMethodAndLineNumber
	 * @return
	 */
	private String getCustomAttributeValue(String operation,String elementLocator1, String optional,String status, String screenShot, String callingMethodAndLineNumber){
		
		return operation + deliminator + elementLocator1 + deliminator + optional + deliminator + status + deliminator + screenShot + deliminator + callingMethodAndLineNumber;
		
	}
	
	//End of code for reporting		
		
}
