package com.esiroi.stimboard;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class EventListAdapter extends ArrayAdapter<ScheduleModel> {
	
	private int ressourceId;
	private List<ScheduleModel> data;
	private LayoutInflater inflater = null;

	public EventListAdapter(Context context, int resource, List<ScheduleModel> objects) {
		super(context, resource, objects);
		ressourceId = resource;
		data = objects;
		inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}	
	
	@Override
	public int getCount() {
		return data.size();
	}

	@Override
	public ScheduleModel getItem(int arg0) {
		return data.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		return arg0;
	}

	@Override
	public View getView(int arg0, View view, ViewGroup parent) {
		if(view==null) {
            // inflate the layout
            view = inflater.inflate(ressourceId, parent, false);
        }
		
		ScheduleModel schedule = data.get(arg0);
		TextView txtEvent = (TextView) view.findViewById(R.id.txt_event);
		TextView txtStart = (TextView) view.findViewById(R.id.txt_start_hour);
		TextView txtEnd = (TextView) view.findViewById(R.id.txt_end_hour);
		txtEvent.setText(schedule.getTitle());
		txtStart.setText(schedule.getStart());
		txtEnd.setText(schedule.getEnd());
		//init the rest of the text view;
		
		
		return view;
	}

}
