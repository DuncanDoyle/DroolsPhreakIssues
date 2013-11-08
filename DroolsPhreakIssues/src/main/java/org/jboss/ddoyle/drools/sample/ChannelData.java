package org.jboss.ddoyle.drools.sample;

public class ChannelData {

	private Object kHelper;
	private Object data;
	private Command.Source commandSource;
	private String uniqueID;

	public ChannelData() {
		// Empty constructor
	}

	public ChannelData(final Object data, final Object helper, final Command.Source commandSource) {
		this.data = data;
		this.kHelper = helper;
		this.commandSource = commandSource;
	}

	public Object getData() {
		return data;
	}

	public Object getkHelper() {
		return kHelper;
	}

	public Command.Source getCommandSource() {
		return commandSource;
	}

	public String getUniqueID() {
		return uniqueID;
	}

	public void setUniqueID(final String uniqueID) {
		this.uniqueID = uniqueID;
	}
}
