package org.jboss.ddoyle.drools.sample;

import java.util.LinkedList;
import java.util.List;

import org.kie.api.runtime.Channel;

public class MockChannel implements Channel {

	private List<Object> sent = new LinkedList<>();

	@Override
	public void send(Object o) {
		sent.add(o);
	}

	public void clean() {
		sent = new LinkedList<>();
	}

	public List<Object> getSentObject() {
		return sent;
	}
}
