package com.esiroi.stimboard;

import java.util.Date;


public class ScheduleModel {
	
	private String title;
	/**
	 * heure de début
	 */
	private String start;
	/**
	 * heure de fin
	 */
	private String end;
	
	public String getTitle() {
		return title;
	}
	
	private transient Date startDate;
	private transient Date endDate;
	
	public void setTitle(String title) {
		this.title = title;
	}
	public String getStart() {
		return start;
	}
	public void setStart(String start) {
		this.start = start;
	}
	public String getEnd() {
		return end;
	}
	public void setEnd(String end) {
		this.end = end;
	}
	public Date getStartDate() {
		//retourner le start date depuis la chaine
		
		return startDate;
	}
	public Date getEndDate() {
		//retourner le end date depuis la chaine
		return endDate;
	}	
	
	

}
