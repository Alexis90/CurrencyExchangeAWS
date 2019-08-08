package com.studienarbeit;

public final class CurrencyInformation {
	
	private final static double euro_chf_course = 1.09;
	private final static double euro_dollar_course =1.12;
	private final static double euro_pound_course = 0.92;
	private final static double chf_euro_course = 0.91;
	private final static double dollar_euro_course = 0.89;
	private final static double pound_euro_course = 1.08;
	
	
	public static double getEuro_chf_course() {
		return euro_chf_course;
	}
	public static double getEuro_dollar_course() {
		return euro_dollar_course;
	}
	public static double getEuro_pound_course() {
		return euro_pound_course;
	}
	public static double getChf_euro_course() {
		return chf_euro_course;
	}
	public static double getDollar_euro_course() {
		return dollar_euro_course;
	}
	public static double getPound_euro_course() {
		return pound_euro_course;
	}
	
	

}
