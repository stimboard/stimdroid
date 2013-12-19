package com.esiroi.stimboard;

import android.app.IntentService;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.IBinder;
import android.util.Log;

public class STIMBoardServiceOld extends IntentService {
	NetworkManager NM;
	String ADDR="10.230.77.61";
	int PORT=6969;
	
	public STIMBoardServiceOld() {
		super("");
		NM=new NetworkManager(ADDR, PORT);
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		Log.i("socket","init");
		
		
		//String numero = intent.getExtras().getString("numero");
		
		
		NM.initSocket();
		try{
		NM.sendData(JsonParser.buildRequest("3088896"));}catch(Exception e){}
		//NM.readData();
		
		while (NM.getSocket().isConnected()) {
			NM.readData();
		}
	}
	
}
