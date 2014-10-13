package com.projectName.test.functional.moduleName1;


import java.io.IOException;

import org.testng.annotations.Test;
import com.projectName.testutils.baseclass.TestBaseClass;
import com.projectName.testutils.genericutility.ExceptionHandler;

import com.projectName.testutils.retryAnalyser.RetryRule;


public class LoginTest extends TestBaseClass {

	/**
	 * Test to verify login
	 * @throws IOException 
	 */

	@Test(retryAnalyzer = RetryRule.class)
	public void loginTest() throws ExceptionHandler{
		try{

			// ------------------------------------------------------------------//
			// Step-1: Get the test data //
			// ------------------------------------------------------------------//
			/*HashedMap testData = ExcelReader.getTestDataByTestCaseId(
					"TC_EBS_001", LoginTest.class.getSimpleName());
			log.info(testData.get("TC_ID").toString() + " - ");*/

			// ------------------------------------------------------------------//
			// Step-2: Load the application //
			// ------------------------------------------------------------------//
			
			//driver = loadURL();
			homePage = loginUser1();
			log.info("Successfully navigated to Preferences Page.");
			
			
		}catch(Exception e){
			new ExceptionHandler (e);
		}
	}
}
