package com.esiroi.stimboard;


import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.List;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class MainActivity extends Activity {
	
	TextView numeroTextView;
//	List<ScheduleModel> dataToShow;
	ListView listSchedule;
	
	/**
	 * 
	 * This class is a Service Connection, used to bind to the Android Service in order to execute task 
	 * specified in the message.what
	 * 
	 * this class is used to bind to the service, so to execute another task within the same service which scan for AP
	 * for proximity
	 * 
	 * the msgWhat parameter is an integer which is already set in the Constants class, this value is used in the ServiceHandler class
	 * 
	 * a call example : bindService(new StimBoardService(context, StimBoardService.class),new StimBoardServiceConnection(value),BIND_AUTO_CREATE)
	 * BIND_AUTO_CREATE : is an constant in the Android Service class, to specify that this class shouldn't create another instance
	 * of the same service if it is already running
	 * 
	 * 
	 * @author herilaza
	 *
	 */
	class StimBoardServiceConnection implements ServiceConnection {
		
		private Message msg;
		
		public StimBoardServiceConnection (int msgWhat) {
			msg = Message.obtain(null, msgWhat);
		}

		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
		
			try {
				msg.replyTo = new Messenger(StimBoardService.getHandler());
				new Messenger(service).send(msg);
			} catch (RemoteException e) {
				e.printStackTrace();
			}
			
		}

		@Override
		public void onServiceDisconnected(ComponentName name) {
			Log.d("StimBoardServiceConnection","Service Disconnected");
		}
		
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		numeroTextView = ((TextView)findViewById(R.id.numero));
		listSchedule = (ListView)findViewById(R.id.listSchedule);
		
//		start the service Wifi Scan for proximity detection
		Intent intent = new Intent(this, StimBoardService.class);
		intent.putExtra("action", Constants.START_SCAN);
		//intent.putExtra("numero", value)
		startService(intent);
		
		}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch(item.getItemId()) {
		case R.id.action_settings : 
			startActivity(new Intent(MainActivity.this, Preference.class));
			break;
			
		case R.id.action_start_service :
			startService(new Intent(this,StimBoardService.class).putExtra("action", Constants.START_SCAN));
			break;
			
		case R.id.action_stop_service : 
			stopService(new Intent(this,StimBoardService.class));
			break;

		default : 
			break;
		}
		return super.onOptionsItemSelected(item);
		
//	private void getPreferences() {
//
//		SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
//
//		((TextView)findViewById(R.id.action_settings)).setText("Num�ro Etudiant : " + preferences.getString("editTextPref", ""));		
//
//	}
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		numeroTextView.setText(PreferenceManager.getDefaultSharedPreferences(this).getString("editTextPref", "default"));
		EventListAdapter eventAdapter = new EventListAdapter(this, R.layout.list_item, parseJson());
		listSchedule.setAdapter(eventAdapter);
		
		//handling the scan result here
		StimBoardReceiver sbReceiver = new StimBoardReceiver();
		IntentFilter filter = new IntentFilter();
		registerReceiver(sbReceiver,filter);
		
	}
	
	@SuppressWarnings("finally")
	public List<ScheduleModel> parseJson() {
		 //Create a InputStream to read the file into  
        InputStream iS; 
        ByteArrayOutputStream oS = new ByteArrayOutputStream();  

        //get the file as a stream  
        try {
			iS = getResources().getAssets().open("text.json");
			
			//create a buffer that has the same size as the InputStream  
	        byte[] buffer = new byte[iS.available()];  
	        //read the text file as a stream, into the buffer  
	        iS.read(buffer);  
	        //create a output stream to write the buffer into  

	        //write this buffer to the output stream  
	        oS.write(buffer);  
	        //Close the Input and Output streams  
	        oS.close();  
	        iS.close(); 
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
        
        finally {
        	
        	final Type listScheduleModelType = new TypeToken<List<ScheduleModel>>(){}.getType();
            //return (List<ScheduleModel>) new Gson().fromJson(oS.toString(), listScheduleModelType);
        	Log.i("Preferences","json: "+PreferenceManager.getDefaultSharedPreferences(this).getString("json", "default"));
        	if(!PreferenceManager.getDefaultSharedPreferences(this).getString("json", "default").isEmpty())
        	return (List<ScheduleModel>) new Gson().fromJson(oS.toString(), listScheduleModelType);
        	else return (List<ScheduleModel>) new Gson().fromJson(oS.toString(), listScheduleModelType);
        }
         
        
        
	}
	

}
