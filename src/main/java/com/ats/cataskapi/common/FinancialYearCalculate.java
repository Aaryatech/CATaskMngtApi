package com.ats.cataskapi.common;

import java.util.Calendar;

public class FinancialYearCalculate {

	public static String calFy() {
		Calendar c = Calendar.getInstance();
		String myString = String.valueOf(c.get(Calendar.YEAR));
		myString = myString.substring(myString.indexOf('0') + 1);
		int a = Integer.parseInt(myString) + 1;
		String fy = myString.concat(myString).concat("-").concat(String.valueOf(a));

		return fy;

	}

}
