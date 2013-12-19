package com.esiroi.stimboard;
import org.json.*;

import android.util.Log;
public class JsonParser {
	public static String buildRequest(String stnb) throws JSONException{
		JSONObject obj = new JSONObject();
		obj.put("id", new JSONObject().put("stnb", stnb));
		Log.i("JSONPARSER", obj.toString()+" char numb: " + obj.toString().length());
		return(obj.toString());
	}
}
