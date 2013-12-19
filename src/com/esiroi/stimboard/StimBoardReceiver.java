package com.esiroi.stimboard;

import java.util.List;

import android.app.Notification;
import android.app.Notification.Builder;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.util.Log;
import android.widget.Toast;

public class StimBoardReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {

		if (intent.getAction().equals(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION)) {
			WifiManager w = (WifiManager) context
	                .getSystemService(Context.WIFI_SERVICE);
	        List<ScanResult> listResult = w.getScanResults();
//	        String result = null;
	        for (ScanResult res : listResult) {
	        	
	        	
//	            if (result == null) result = res.SSID + "" + WifiManager.calculateSignalLevel(res.level, Constants.SIGNAL_NUMBER_LEVEL) + "\r\n";
//	            else result = result + res.SSID + "" + WifiManager.calculateSignalLevel(res.level, Constants.SIGNAL_NUMBER_LEVEL) + "\r\n";
	        	Log.i("Result",res.SSID + " - " + WifiManager.calculateSignalLevel(res.level, Constants.SIGNAL_NUMBER_LEVEL));
	        	
	        	if (res.SSID.equals(Constants.ACCESS_POINT_NAME) && WifiManager.calculateSignalLevel(res.level, Constants.SIGNAL_NUMBER_LEVEL) >= Constants.SIGNA_QUALITY_VERY_GOOD) {
	        		Toast.makeText(context, "Proximity Detected ... Engaging server communication", Toast.LENGTH_LONG).show();
	        			        		
//	        		Send a notification to the user
	        		
//	        		NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE); 
//	        		Intent intentActivity = new Intent(context, MainActivity.class);
//	        	    PendingIntent pIntent = PendingIntent.getActivity(context, 0, intentActivity, 0);
//	        	    
//	        	    Notification.Builder builder = new Notification.Builder(context);
//	        	    builder.setContentTitle("ProximityDetected");
//	        	    builder.setContentText("Check your schedule");
//	        	    builder.setContentIntent(pIntent);
//	        	    
//	        	    notificationManager.notify(5, builder.getNotification());
	        		break;
	        	}
	        	        	
	        }
	       	        
		}		
		
    }

}
