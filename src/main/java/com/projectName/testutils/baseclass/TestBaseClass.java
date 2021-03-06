package com.projectName.testutils.baseclass;

import java.awt.AWTException;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.FileAppender;
import org.apache.log4j.Layout;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import com.projectName.testutils.genericutility.DateTimeUtility;
import com.projectName.testutils.genericutility.ExceptionHandler;
import com.projectName.testutils.genericutility.FileUtility;
import com.projectName.testutils.pages.genericPages.HomePage;
import com.projectName.testutils.pages.genericPages.LoginPage;
import com.projectName.testutils.seleniumutils.SeleniumWebDriver;
import com.projectName.testutils.testdatareader.DataAccessClient;
import com.projectName.testutils.testdatareader.EnvironmentPropertiesReader;

public class TestBaseClass extends Assert {

	/**
	 * This page object is initialized before the start of every test.
	 */
	protected HomePage homePage;

	/**
	 * For Core Selenium2 functionality
	 */
	protected WebDriver driver = null;
	protected WebDriver driver1 = null;
	protected WebDriverWait wait;
	private final String deliminator = "####";
	protected String url = "http://systems.aspiresys.com/";
	/**
	 * Standard log4j logger.
	 */
	protected final Logger log = Logger.getLogger(getClass().getSimpleName());

	/**
	 * To Read the environment details
	 */
	EnvironmentPropertiesReader environmentPropertiesReader;

	/**
	 * Getting the base path of screen shot
	 */
	String screenshotBasePath;
	String logBasePath;
	String logFile;
	/**
	 * Global retry count for the tests failed because of TimedOut Exceptions
	 */
	/**
	 * Instantiating the driver path
	 */
	private final String IE_FILE_PATH = "/src/test/resources/extensions/IEDriverServer.exe";
	private final String CHROME_FILE_PATH = "/src/test/resources/extensions/chromedriver.exe";
	/**
	 * For DB connection
	 */
	public static DataAccessClient dataAccess = null;

	public enum BrowserType {
		FIREFOX("firefox"), IE("iexplore"), SAFARI("SAFARI"), CHROME("CHROME");
		private String label;

		private BrowserType(String label) {
			this.label = label;
		}

		public String Value() {
			return label;
		}
	}

	/***
	 * Declaring the Users
	 */
	protected String TLADMIN = "TLADMIN";
	protected String LHALL = "LHALL";
	protected String KWINTERS = "KWINTERS";

	/**
	 * Displaying the environment details
	 * 
	 * @throws IOException
	 */
	public TestBaseClass() {
		// Getting the properties
		try {
			PropertyConfigurator.configure(new File(".").getCanonicalPath()
					+ File.separator + "src" + File.separator + "main"
					+ File.separator + "resources" + File.separator
					+ "log4j.properties");

			// Location for screenshot
			screenshotBasePath = new File(".").getCanonicalPath()
					+ File.separator + "test-output" + File.separator
					+ "screenshots";

			// Location for logs
			logBasePath = new File(".").getCanonicalPath() + File.separator
					+ "test-output" + File.separator + "logs";

			// Instantiating logger
			logFile = new File(".").getCanonicalPath() + File.separator
					+ "test-output" + File.separator + "temp.log";

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	

	/**
	 * Log in to the application using the user name and password
	 * properties file
	 * 
	 * @return homePage
	 * @throws ClassNotFoundException
	 * @throws ExceptionHandler
	 * @throws AWTException
	 * @throws InterruptedException
	 */
	protected HomePage loginUser1() throws Exception {
		// Intializing the objects
		LoginPage LoginPage = PageFactory.initElements(driver, LoginPage.class);
		homePage = PageFactory.initElements(driver, HomePage.class);

		// Get the user name from home page
		String user = environmentPropertiesReader.getAccMgrUsername();
		//String user = (String) loginData.get("UserName");
		//String password = (String) loginData.get("Password");
		// Invoke login after log out
		//LoginPage.logOut();
		homePage = LoginPage.login(user,
				environmentPropertiesReader.getPassword());
		log.info("Logged into the application as - "
				+user);
		return homePage;
	}

	/**
	 * Initializing the DB connection
	 * 
	 * @return DataAccessClient
	 * @throws Exception
	 */
	protected DataAccessClient getDataAccessClient() throws Exception {
		if (dataAccess == null) {
			dataAccess = new DataAccessClient(
					environmentPropertiesReader.getDbDriver(),
					environmentPropertiesReader.getDBurl(),
					environmentPropertiesReader.getDBusername(),
					environmentPropertiesReader.getDBpassword());
		}
		return dataAccess;
	}

	/**
	 * Set up logger info
	 * @throws IOException 
	 * 
	 * @throws Exception
	 */
	@BeforeMethod(alwaysRun = true)
	public final void genericSetUp() throws ExceptionHandler, IOException {
		// Instantiating Logger
		
		driver = loadURL();
		Layout layout = new PatternLayout(
				"%d{dd-MMM-yyyy HH:mm:ss:SSS} %-5p %c{1}:%L - %m%n");
		log.removeAllAppenders();
		FileAppender appender = new FileAppender(layout, logFile, false);
		log.addAppender(appender);

		String fileParam = System.getProperty("selenium.properties.file");

		log.info("=====================================================================================================");
		if (fileParam == null || fileParam.contains("selenium.properties.file")) {
			environmentPropertiesReader = new EnvironmentPropertiesReader();
		} else {
			log.info("Properties file used : " + fileParam);
			environmentPropertiesReader = new EnvironmentPropertiesReader(
					fileParam);
		}

		log.info("App URL    : " + environmentPropertiesReader.getURL());
		log.info("Browser    : " + environmentPropertiesReader.getBrowser());
		log.info("=====================================================================================================");
	}

	

	/**
	 * Returning the driver based on the browser
	 * 
	 * @param browser
	 * @return
	 * @throws IOException
	 */
	public WebDriver loadURL() throws IOException {
		// Reading the URL and Browser type
		String url = "http://systems.aspiresys.com/";
		String browser = "FIREFOX";
//		String browser = environmentPropertiesReader.getBrowser();

		// Instantiating the browser
		driver = getWebDriver(browser);
		wait = new WebDriverWait(driver, 30);
		driver.get(url);

		// Maximize the window
		driver.manage().window().maximize();

		return driver;

	}

	/**
	 * Returning the driver based on the browser
	 * 
	 * @param browser
	 * @return
	 * @throws IOException
	 */
	public WebDriver getWebDriver(String browser) throws IOException {
		switch (BrowserType.valueOf(browser)) {
		case FIREFOX:
			return new FirefoxDriver();
		case IE:
			DesiredCapabilities IECapabilities = DesiredCapabilities
					.internetExplorer();
			IECapabilities.setCapability("nativeEvents", false);
			IECapabilities.setCapability("requireWindowFocus", true);
			IECapabilities
					.setCapability(
							InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS,
							true);
			IECapabilities.setCapability("enableElementCacheCleanup", true);
			File file = new File(new java.io.File(".").getCanonicalPath()
					+ IE_FILE_PATH);
			System.setProperty("webdriver.ie.driver", file.getAbsolutePath());
			return new InternetExplorerDriver(IECapabilities);
		case SAFARI:
			DesiredCapabilities safariCapabilities = DesiredCapabilities
					.safari();
			safariCapabilities.setCapability(
					SafariDriver.CLEAN_SESSION_CAPABILITY, true);
			return new SafariDriver(safariCapabilities);
		case CHROME:
			DesiredCapabilities chromeCapabilities = DesiredCapabilities
					.chrome();
			File chromeFile = new File(new java.io.File(".").getCanonicalPath()
					+ CHROME_FILE_PATH);
			System.setProperty("webdriver.chrome.driver",
					chromeFile.getAbsolutePath());
			return new ChromeDriver(chromeCapabilities);
		default:
			throw new RuntimeException("Browser type unsupported");
		}
	}

	/**
	 * Capturing screenshot, Setting test result and creating log files after
	 * each test run
	 * 
	 * @param result
	 * @throws IOException
	 */
	@AfterMethod(alwaysRun = true)
	public final void tearDown(ITestResult result) throws IOException {

		String dateTimeStamp = DateTimeUtility
				.getCurrentDateAndTimeInLoggerFormat();
		String status = "PASS";

		// Capture screen shot in case test has failed.
		try {
			if (!result.isSuccess()) {
				String destFile = screenshotBasePath + File.separator
						+ result.getName() + " " + dateTimeStamp + ".png";
				log.info("Captured Screenshot : " + destFile);
				status = "FAIL";
				File scrFile = SeleniumWebDriver.takeScreenshot(driver);
				FileUtility.copyFile(scrFile, new File(destFile));
			}
		} catch (Exception e) {
			log.error("The following error has occured while capturing a screen shot : "
					+ e.getMessage());
		} finally {

			String fileName = logBasePath + File.separator + result.getName()
					+ " " + dateTimeStamp + " " + status + ".log";

			// Create log file with method name
			FileUtility.copyFile(new File(logFile), new File(fileName));

			// Logging the test result
			log.info("The test result for " + result.getName() + " is "
					+ status);

			// Closing the browser and closing driver
			driver.quit();
		}
	}
	
	//Report Part
		//private final String deliminator = "####";
		protected final String empty = "";
		
		protected final String dot = ".";	
		
		protected String status = null;
		

		protected ITestResult logCustomMessage() {
			return Reporter.getCurrentTestResult();
		}
		
		/**
		 * used for get the calling method name with line number
		 * @return
		 */
		protected String getCallingMethodAndLineNumber(){
			StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
			
			String callingMethodWithLineNumber = stackTraceElements[3].getClassName() + dot + stackTraceElements[3].getMethodName() + dot + stackTraceElements[3].getLineNumber() ;
			
			return callingMethodWithLineNumber;
		}
		
		
		/**
		 * This method returns the current date and time in format HH-mm-ss.SSS
		 * 
		 * @return time - in the above mentioned format
		 */
		protected static String getCurrentDateAndTime() {
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
		protected String getCustomAttributeValue(String operation,String elementLocator1, String optional,String status, String screenShot, String callingMethodAndLineNumber){
			
			return operation + deliminator + elementLocator1 + deliminator + optional + deliminator + status + deliminator + screenShot + deliminator + callingMethodAndLineNumber;
			
		}
		
		//End of code for reporting
			
		protected boolean logMessage(String message1,String message2){
			
			logCustomMessage().setAttribute(getCurrentDateAndTime(), getCustomAttributeValue(message1,message2, empty, empty, empty, getCallingMethodAndLineNumber()));
			log.info(message2);
			return true;
		}
}
