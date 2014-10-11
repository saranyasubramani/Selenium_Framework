//package com.projectName.functional.annotations;
//
//import java.lang.reflect.Constructor;
//import java.lang.reflect.Method;
//import org.testng.IAnnotationTransformer;
//import org.testng.IRetryAnalyzer;
//import org.testng.annotations.ITestAnnotation;
//
//import com.projectName.testutils.retryAnalyser.RetryRule;
//
//public class AnnotationTranstofmer implements IAnnotationTransformer {
//    public void transform(ITestAnnotation annotation, Class testClass,
//            Constructor testConstructor, Method testMethod) {
//        IRetryAnalyzer retry = annotation.getRetryAnalyzer();
//        if (retry == null) {
//            annotation.setRetryAnalyzer(RetryRule.class);
//        }
//    }
//}