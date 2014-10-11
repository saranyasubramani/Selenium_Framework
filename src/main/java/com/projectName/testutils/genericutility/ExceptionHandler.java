package com.projectName.testutils.genericutility;

import java.io.IOException;

public class ExceptionHandler extends Exception{
	public   String errorMessage = "";
    public ExceptionHandler(String s){
    	errorMessage = s;
    }
    ExceptionHandler() {}
	public ExceptionHandler(Exception e){
		
		String error[] = e.getMessage().split("###");
		
		if(e.getMessage().contains("ArrayIndexOutOfBoundsException")){
			System.out.println(error[0]);
		}
		else if(e.getMessage().contains("RuntimeException")){
			System.out.println(error[0]);
		}
		else if(e.getMessage().contains("IOException")){
			System.out.println(error[0]);
		}
		else if(e.getMessage().contains("Exception")){
			System.out.println(error[0]);
		}
		else if(e.getMessage().contains("ElementNotFoundException")){
			System.out.println(error[0]);
		}
		else if(e.getMessage().contains("InterruptedException")){
			System.out.println(error[0]);
		}
		e.printStackTrace();
	}
}

