package com.esiroi.stimboard;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.charset.Charset;

import android.util.Log;

public class NetworkManager {
	Socket socket;
	private String addr;
	private int port;

	BufferedReader in;
	PrintWriter out;
	DataOutputStream outToServer;

	String data;

	NetworkManager(String addr, int port){
		this.addr=addr;
		this.port=port;
	}
	public void initSocket(){
		try{

			socket = new Socket(addr,port);
			//PrintWriter out=new PrintWriter(socket.getOutputStream(),true);

			outToServer = new DataOutputStream(socket.getOutputStream());
			in=new BufferedReader(new InputStreamReader(socket.getInputStream()));

		}catch (UnknownHostException e) {

			e.printStackTrace();
		}catch (IOException e) {

			e.printStackTrace();
		}
	}
	public void sendData(String str){
		try{

			outToServer.writeUTF(str);
			//outToServer.
			Log.i("data","sending data: "+str);

		}catch(Exception e){
			Log.i("data","sending error"+e.getMessage());
		}
	}
	public void readData(){
		try{
			//Log.i("data","reading data");

			while ((data = in.readLine()) != null) {
				Log.i("data",data);
			}

			//Log.i("data","reading DONE!!");
		}catch(Exception e){
			Log.i("data",e.getMessage());

		}
	}
	
	public Socket getSocket() {
		return socket;
	}
	
	


}
