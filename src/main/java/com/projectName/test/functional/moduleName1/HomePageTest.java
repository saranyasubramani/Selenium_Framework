package com.projectName.test.functional.moduleName1;

import org.testng.Assert;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.projectName.testutils.baseclass.TestBaseClass;
import com.projectName.testutils.genericutility.ExceptionHandler;
import com.projectName.testutils.pages.genericPages.HomePage;
import com.projectName.testutils.pages.projectNamePages.HomeScreen;
import com.projectName.testutils.retryAnalyser.RetryRule;

@Listeners(com.projectName.testutils.baseclass.CustomizedReporter.class)

public class HomePageTest  extends TestBaseClass{

	@Test(retryAnalyzer = RetryRule.class)
	public void homePageTest() throws ExceptionHandler {
		try{
			
			// ------------------------------------------------------------------//
			// Step-1: Log in to the application //
			// ------------------------------------------------------------------//
			logMessage("STEP 1:","Login to "+url+" website");
			homePage = loginUser1();
			Thread.sleep(5000);
			logMessage("RESULT:","Sucessfully navigated to Home screen");
			
			// ------------------------------------------------------------------//
			// Step-2:Load Home Page Variable //
			// ------------------------------------------------------------------//
			logMessage("STEP 2:","Loading home page elements");
			homePage = PageFactory.initElements(driver, HomePage.class);	
			HomeScreen homeobject = homePage.navigateToHomePage();
			Thread.sleep(7000);
			logMessage("RESULT","Successfully loaded Home Page elements");
			
			
			// ------------------------------------------------------------------//
			// Step-3:Verify Home page element //
			// ------------------------------------------------------------------//
			logMessage("STEP 3:","Verify presense of home page elements");
			Assert.assertTrue(homeobject.verifyelement(),"Verification failed");
			logMessage("RESULT","Verified home page elements");
		}catch(Exception e){
			new ExceptionHandler (e);
		}

	}
}
