package org.jboss.ddoyle.drools.sample;

import java.util.Date;

public class SimpleEvent {
	
	public enum Status { ENRICHED, TO_SEND, SENT, FILTERED}
	
	private Date eventDate;
	
	private String id;
	
	private  Status status; 
	
	private String code;
	
	public SimpleEvent() {
		super();
		id = System.nanoTime() + "-" + Math.random();
		code = "";
		eventDate = new Date();
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Date getEventDate() {
		return eventDate;
	}

	public void setEventDate(Date eventDate) {
		this.eventDate = eventDate;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

}
