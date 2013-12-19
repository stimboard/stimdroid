package com.esiroi.stimboard;

public class Constants {
	
//	For broadcast receiver
	public final static String PROXIMITY_DETECTED = "com.esiroi.stimboard.PROXIMITY_DETECTED";
	
//	Constant used for Service
	public final static int START_SCAN = 1;
	public final static int START_SERVER_COMMUNICATION = 2;
	public final static int SCAN_PERIOD = 15000; // in ms
	
//	For WiFi manager
	public final static int SIGNAL_NUMBER_LEVEL = 100;
	public final static String ACCESS_POINT_NAME = "stimberry_wn";
//	The le level of the signal that we consider to detect proximity
	public final static int SIGNA_QUALITY_VERY_GOOD = 95;
	

}
