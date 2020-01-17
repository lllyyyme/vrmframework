package org.vrmframework.parser;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;



public class PatternUtils {

	public static List<String> getStringByPattern(String patternStr, String orgin) {
		Pattern pattern = Pattern.compile(patternStr);
		Matcher matcher = pattern.matcher(orgin);
		List<String> arrayList = new ArrayList<>();
		while (matcher.find()) {
			arrayList.add(matcher.group(0));
		}
		return arrayList;
	}
	
	public static String getMethodNameByPattern(String patternStr, String orgin) {
		Pattern pattern = Pattern.compile(patternStr);
		Matcher matcher = pattern.matcher(orgin);
		if(matcher.find()){
			return matcher.group(1);
		}
		return null;
	}
	
	public static String[] getTypeStringByPattern(String patternStr, String orgin) {
		Pattern pattern = Pattern.compile(patternStr);
		Matcher matcher = pattern.matcher(orgin);
		List<String> arrayList = new ArrayList<>();
		while (matcher.find()) {
			String rtypeStr = matcher.group(2);
			String rtype = rtypeStr.split("=")[1];
			arrayList.add(rtype);
		}
		if(!arrayList.isEmpty()){
			return arrayList.toArray(new String[arrayList.size()]);
		}
		return null;
	}
	

	public static List<Integer> getTypeIndexByPattern(String patternStr, String orgin) {
		Pattern pattern = Pattern.compile(patternStr);
		Matcher matcher = pattern.matcher(orgin);
		List<Integer> arrayList = new ArrayList<>();
		while (matcher.find()) {
			Integer rtypeStr = Integer.parseInt(matcher.group(1));
			arrayList.add(rtypeStr);
		}
		return arrayList;
	}

}
