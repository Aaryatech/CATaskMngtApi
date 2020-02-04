package com.ats.cataskapi.controller;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TestControl {

	public static void main (String[]args)
	  {
	StringBuffer sb = new StringBuffer();
	String[] tokens = {"","plane tickets","friends"};
	
	String x="I am first a developer plane fre";
	ArrayList<String> stringArr=new ArrayList<>();
	stringArr.add("");
	stringArr.add("plane");
	stringArr.add("tickets");
	stringArr.add("friends");
	
	
	
	if(stringArr.contains(x));
	{
		System.err.println("Yes");
		x.replace(x, "");
		System.err.println("after replace" +x);
	}
	System.err.println("x" +x);
}
}
