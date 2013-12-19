package com.esiroi.stimboard;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.wifi.WifiManager;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.Messenger;
import android.os.Process;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;

public class StimBoardService extends Service {
	
	private static ServiceHandler mServiceHandler;
	private NetworkManager networkManager;
	public Looper mServiceLooper;
	
//	Used to bound from other component
	public Messenger mMessenger;
	
//	private LocalBroadcastManager
	class ServiceHandler extends Handler {
		
		private Timer timer;
		
//		this variable is used to stop the service
		private int serviceStartId;
		public ServiceHandler(Looper looper) {
	          super(looper);
	          timer = new Timer();
	      }
	      @Override
	      public void handleMessage(Message msg) {
	    	  
	    	  serviceStartId = msg.arg1;
	    	  
	    	  switch (msg.what) {
	    	  	case Constants.START_SCAN :
	    	  		
	    	  		timer.scheduleAtFixedRate(new TimerTask() {
	    				
	    				@Override
	    				public void run() {
	    					// do Wifi scan here
	    					String connectivity_context = Context.WIFI_SERVICE;
	    					final WifiManager wifi = (WifiManager) getSystemService(connectivity_context);  
	    					if (wifi.isWifiEnabled()) {
	    		                    wifi.startScan();
	    					}
	    				}
	    	    	  }, 0, Constants.SCAN_PERIOD);
	    	  		//break;
	    	  		
	    	  	//case Constants.START_SERVER_COMMUNICATION : 
	    	  		
//	    	  		here goes the code when the server has detected proximity
//	    	  		this includes the sending of the information about the student
//	    	  		and also the reception of the json file returned by the server
	    	  		SharedPreferences preferences =getSharedPreferences("com.esiroi.stimboard_preferences", MODE_MULTI_PROCESS); // get preferences settings
	    	        String addr=preferences.getString("editTextServ", "");
	    	        String port=preferences.getString("editTextPort","");
	    	        Log.i("Service_pref","address:"+addr);
	    	        Log.i("Service_pref","port:"+Integer.parseInt(port));
	    	        
	    	        networkManager.setCfg(addr, Integer.parseInt(port));
	    	        if(networkManager.initSocket()){
	    	        	if(networkManager.sendAndGet(preferences.getString("editTextPref", "")))
	    	        		Log.i("networkManager","gettext:"+networkManager.getData());
	    	        		SharedPreferences.Editor editor = preferences.edit();
	    	        		editor.putString("json", networkManager.getData());
	    	        		editor.commit();
	    	        		Log.i("NetworkManager",networkManager.getData());
	    	        		Log.i("Preferences",preferences.getString("json", ""));
	    	        }
	    	        
	    	        
	    	        
	    	  		
	    	  		break;
	    	  		
	    	  	default : 
	    	  		break;
	    	  };
	          
	      }
	      
	      public void stopTask() {
	    	  timer.cancel();
	          stopSelf(serviceStartId);
	      }

	}
	

	@Override
	public void onCreate() {
		super.onCreate();

		// Start up the thread running the service.  Note that we create a
	    // separate thread because the service normally runs in the process's
	    // main thread, which we don't want to block.  We also make it
	    // background priority so CPU-intensive work will not disrupt our UI.
	    HandlerThread thread = new HandlerThread("ServiceStartArguments",Process.THREAD_PRIORITY_BACKGROUND);
	    thread.start();
	    
	    // Get the HandlerThread's Looper and use it for our Handler 
	    mServiceLooper = thread.getLooper();
	    mServiceHandler = new ServiceHandler(mServiceLooper);
	    mMessenger = new Messenger(mServiceHandler);
	    
	    networkManager=new NetworkManager();

	}
	

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		Toast.makeText(this, "service starting", Toast.LENGTH_SHORT).show();

	      // For each start request, send a message to start a job and deliver the
	      // start ID so we know which request we're stopping when we finish the job
	      Message msg = mServiceHandler.obtainMessage();
	      msg.arg1 = startId;
	      mServiceHandler.sendMessage(msg);
	      
//	      set the message what so we know which task to start
	      msg.what = intent.getIntExtra("action", Constants.START_SCAN);
	      
	      // If we get killed, after returning from here, restart
	      return START_STICKY;
	}

	@Override
	public IBinder onBind(Intent intent) {
		return mMessenger.getBinder();
	}


	@Override
	public boolean onUnbind(Intent intent) {
		// TODO Auto-generated method stub
		return super.onUnbind(intent);
	}


	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		// stop the service correctly stop the task
		mServiceHandler.stopTask();
	}
	
	public static Handler getHandler() {
		return mServiceHandler;
	}
	
	

}
