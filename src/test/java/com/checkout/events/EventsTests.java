package com.checkout.events;

import com.checkout.SandboxTestFixture;
import com.checkout.TestHelper;
import com.checkout.payments.PaymentResponse;
import com.checkout.webhooks.WebhookRequest;
import com.checkout.webhooks.WebhookResponse;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class EventsTests extends SandboxTestFixture {

    @Before
    public void before() throws Exception {
        Thread.sleep(5000);
    }

    @Test
    public void retrieve_all_event_types() throws Exception {
        List<EventTypesResponse> allEventTypesResponses = getApi().eventsClient().retrieveAllEventTypes(null).get();
        Assert.assertNotNull(allEventTypesResponses);
        Assert.assertEquals(2, allEventTypesResponses.size());
    }

    @Test
    public void retrieve_v1_event_types() throws Exception {
        List<EventTypesResponse> v1EventTypesResponses = getApi().eventsClient().retrieveAllEventTypes("1.0").get();
        Assert.assertNotNull(v1EventTypesResponses);
        Assert.assertEquals(1, v1EventTypesResponses.size());
        Assert.assertEquals("1.0", v1EventTypesResponses.get(0).getVersion());
        Assert.assertNotNull(v1EventTypesResponses.get(0).getEventTypes());
        Assert.assertFalse(v1EventTypesResponses.get(0).getEventTypes().isEmpty());
    }

    @Test
    public void retrieve_v2_event_types() throws Exception {
        List<EventTypesResponse> v2EventTypesResponses = getApi().eventsClient().retrieveAllEventTypes("2.0").get();
        Assert.assertNotNull(v2EventTypesResponses);
        Assert.assertEquals(1, v2EventTypesResponses.size());
        Assert.assertEquals("2.0", v2EventTypesResponses.get(0).getVersion());
        Assert.assertNotNull(v2EventTypesResponses.get(0).getEventTypes());
        Assert.assertFalse(v2EventTypesResponses.get(0).getEventTypes().isEmpty());
    }

    // TODO: Find ways to test events APIs again
}