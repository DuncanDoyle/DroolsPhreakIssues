package org.jboss.ddoyle.drools.sample;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.kie.api.KieServices;
import org.kie.api.event.rule.AgendaEventListener;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

public class PhreakyTest {

	private static final String OUT_CHANNEL_NAME = "out";

	private static KieContainer kieContainer;
	private KieSession kieSession;
	private MockChannel channel;

	@BeforeClass
	public static void initKieContainer() {
		KieServices kieServices = KieServices.Factory.get();
		kieContainer = kieServices.getKieClasspathContainer();
	}

	/*
	 * Load and initialize a new KieSession on every test.
	 */
	@Before
	public void initKieSession() {
		kieSession = kieContainer.newKieSession();
		channel = new MockChannel();
		kieSession.getChannels().put(OUT_CHANNEL_NAME, channel);
		AgendaEventListener myAgendaEventListener = new MyAgendaEventListener();
		kieSession.addEventListener(myAgendaEventListener);
	}

	@Test
	public void testRG_FLT_03_One() {
		// Test that the first 18 inserts are filtered.
		/*
		 * This test does not die
		 */
		testFrequency("K08", 18);
	}

	@Test
	public void testRG_FLT_03_Two() {
		/*
		 * Please not that the rule and the test are a bit flawed. The intention was to test that the first 18 events are filtered and every
		 * 20th event is sent to an output channel, but the rule actually does not work as intended (see the comments in the DRL file).
		 * 
		 * However, it seems that that incorrect rule and this test trigger a bug in the PHREAK engine 'every now and then'. It doesn't
		 * happen on every run, but every other run or so, a ClassCastException is thrown. It seems that is has to do with the fact that
		 * we're batch inserting a number of events (21) that is higher than the value we check for in the rule with the accumulate function.
		 */
		//testFrequencyTwo("K08", 20);
		testFrequencyTwo("K08", 30);
	}

	private void testFrequency(final String code, final int fq) {

		for (int i = 0; i < fq; i++) {
			insertEvent(code, SimpleEvent.Status.ENRICHED);
		}
		kieSession.fireAllRules();
		assertEquals("All event must be still in working memory", fq, kieSession.getFactHandles().size());
		assertEquals("No event must be in channel out", 0, channel.getSentObject().size());

		insertEvent(code, SimpleEvent.Status.ENRICHED);
		kieSession.fireAllRules();
		assertEquals("Last event must be in working memory", 1, kieSession.getFactHandles().size());
		assertEquals("One event must be in channel out", 1, channel.getSentObject().size());

		insertEvent(code, SimpleEvent.Status.ENRICHED);
		kieSession.fireAllRules();
		assertEquals("Two last event must be in working memory", 2, kieSession.getFactHandles().size());
		assertEquals("Only one event must be in channel out", 1, channel.getSentObject().size());
	}

	private void testFrequencyTwo(final String code, final int fq) {
		// Just some testing
		for (int i = 0; i < fq; i++) {
			insertEvent(code, SimpleEvent.Status.ENRICHED);
		}
		kieSession.fireAllRules();
		/*
		 * assertEquals("Last event must be in working memory", 1, kieSession.getFactHandles().size());
		 * assertEquals("One event must be in channel out", 1, channel.getSentObject().size());
		 * 
		 * insertEvent(code, SimpleEvent.Status.ENRICHED, "1", "1");
		 */
		assertEquals("Two last event must be in working memory", 12, kieSession.getFactHandles().size());
		assertEquals("Only one event must be in channel out", 1, channel.getSentObject().size());
	}

	private SimpleEvent createEvent(final String code, final SimpleEvent.Status status) {
		SimpleEvent event = new SimpleEvent();
		event.setCode(code);
		event.setStatus(status);
		return event;
	}

	protected void insertEvent(final String code, final SimpleEvent.Status status) {
		kieSession.insert(createEvent(code, status));
	}

}
