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
	private String addr="";
	private int port=0;

	BufferedReader in;
	PrintWriter out;
	DataOutputStream outToServer;

	String data;

	NetworkManager(){
		super();
	}
	public void setCfg(String addr, int port){
		//set address and port were socket going to be connected.
		this.addr=addr;
		this.port=port;
	}
	public boolean initSocket(){
		if(addr!="" & port!=0)
		try{

			socket = new Socket(addr,port);
			Log.i("NetworkManager","socket opened to "+addr+":"+port);
			//PrintWriter out=new PrintWriter(socket.getOutputStream(),true);

			outToServer = new DataOutputStream(socket.getOutputStream());
			in=new BufferedReader(new InputStreamReader(socket.getInputStream()));
			return true;

		}catch (UnknownHostException e) {

			e.printStackTrace();
			Log.i("NetworkManager","fail to open socket to "+addr+":"+port);
			return false;
		}catch (IOException e) {

			e.printStackTrace();
			Log.i("NetworkManager","fail to open socket to "+addr+":"+port);
			return false;
		}
		else {
			Log.i("NetworkManager","missing Serveur addr or port");
			return false;
			}
	}
	public boolean sendData(String str){
		try{
			data="";
			outToServer.writeUTF(str);
			//outToServer.
			Log.i("NetworkManager","sending data: "+str);
			return true;

		}catch(Exception e){
			Log.i("NetworkManager","sending error"+e.getMessage());
			return false;
		}
	}
	public boolean readData(){
		try{
			//Log.i("data","reading data");

			while ((data = in.readLine()) != null) {
				Log.i("NetworkManager","read: "+data);
				
			}
			if(data.isEmpty()) return false;
			return true;

			//Log.i("data","reading DONE!!");
		}catch(Exception e){
			Log.i("data",e.getMessage());
			return false;

		}
	}
	public boolean sendAndGet(String str){
		data="";
		if(sendData(str))
			if(readData()){ 
				Log.i("NetworkManager","sendAndGet: succeeded");
				return true;
			}
		Log.i("NetworkManager","sendAndGet: failed");
		return false;
	}
	public String getData(){
		return data;
	}
	
	public Socket getSocket() {
		return socket;
	}
	
	


}
