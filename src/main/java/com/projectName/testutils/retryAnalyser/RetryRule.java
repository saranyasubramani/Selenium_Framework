package com.projectName.testutils.retryAnalyser;

import org.apache.log4j.Logger;
import org.junit.rules.MethodRule;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.Statement;
import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

import com.thoughtworks.selenium.Wait.WaitTimedOutException;

/**
 * Retry rule for testclasses which are failed because of wait timed out No of retries can be specified in the TestBaseClass @Rule
 * annotated place
 * 
 * @author yc30s1k
 * 
 */
public class RetryRule implements IRetryAnalyzer {
	private int retryCount;
	private int maxRetry;

	public RetryRule() {
		this.retryCount = 1;
		this.maxRetry=1;
	}
	
	public boolean retry(ITestResult result) {
		String e= result.getThrowable().toString();
		if(e.contains("AssertionError")&&maxRetry>retryCount) {
				retryCount++;
				return true;
		}
		return false;
	}

}
