package com.application.rule;


public class PersonPageRule {
	
	 public static boolean isHasUnderAgeChild(String age){
		 return Integer.parseInt(age) < 18;
	 }
	 

	
	

}
