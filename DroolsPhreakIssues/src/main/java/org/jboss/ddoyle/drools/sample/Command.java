package org.jboss.ddoyle.drools.sample;

import java.io.Serializable;

public class Command implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8326258401043176930L;
	
	private final String id;
	private final long timestamp;
	private Object data;
	private Source source;

	public Command(String id) {
		this(id, System.currentTimeMillis());
	}

	public Command(String id, long timestamp) {
		this.id = id;
		this.timestamp = timestamp;
	}

	public long getTimestamp() {
		return timestamp;
	}

	public String getId() {
		return id;
	}

	public Source getSource() {
		return source;
	}

	public void setSource(Source source) {
		this.source = source;
	}

	public Object getData() {
		return data;
	}

	public void setData(final Object data) {
		this.data = data;
	}

	public static enum Source {
		ANALYSIS
	}

}
