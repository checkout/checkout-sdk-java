package com.checkout.events;

import com.checkout.ApiTestFixture;
import org.junit.Assert;
import org.junit.Test;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class EventsTests extends ApiTestFixture {

    @Test
    public void retrieve_all_event_types() throws Exception {
        List<EventTypesResponse> allEventTypesResponses = getApi().eventsClient().retrieveAllEventTypes(null).get();
        Assert.assertNotNull(allEventTypesResponses);
        Assert.assertEquals(2, allEventTypesResponses.size());

        List<EventTypesResponse> v1EventTypesResponses = getApi().eventsClient().retrieveAllEventTypes("1.0").get();
        Assert.assertNotNull(v1EventTypesResponses);
        Assert.assertEquals(1, v1EventTypesResponses.size());
        Assert.assertEquals("1.0", v1EventTypesResponses.get(0).getVersion());
        Assert.assertNotNull(v1EventTypesResponses.get(0).getEventTypes());
        Assert.assertFalse(v1EventTypesResponses.get(0).getEventTypes().isEmpty());

        List<EventTypesResponse> v2EventTypesResponses = getApi().eventsClient().retrieveAllEventTypes("2.0").get();
        Assert.assertNotNull(v2EventTypesResponses);
        Assert.assertEquals(1, v2EventTypesResponses.size());
        Assert.assertEquals("2.0", v2EventTypesResponses.get(0).getVersion());
        Assert.assertNotNull(v2EventTypesResponses.get(0).getEventTypes());
        Assert.assertFalse(v2EventTypesResponses.get(0).getEventTypes().isEmpty());
    }

    @Test
    public void retrieve_all_events() throws Exception {
        EventsPageResponse eventsPageResponse = getApi().eventsClient().retrieveEvents(null, null, null, null, null).get();
        Assert.assertNotNull(eventsPageResponse);
        Assert.assertNotNull(eventsPageResponse.getFrom());
        Assert.assertNotNull(eventsPageResponse.getTo());
        Assert.assertTrue(eventsPageResponse.getFrom().isBefore(eventsPageResponse.getTo()));
        Assert.assertTrue(0 < eventsPageResponse.getTotalCount());
        Assert.assertEquals(0, eventsPageResponse.getSkip());
        Assert.assertEquals(10, eventsPageResponse.getLimit());
        Assert.assertNotNull(eventsPageResponse.getData());
        Assert.assertEquals(10, eventsPageResponse.getData().size());
        Assert.assertNotNull(eventsPageResponse.getData().get(0).getId());
        Assert.assertNotNull(eventsPageResponse.getData().get(0).getType());
        Assert.assertNotNull(eventsPageResponse.getData().get(0).getCreatedOn());
        Assert.assertEquals(eventsPageResponse.getData().get(0).getCreatedOn(), eventsPageResponse.getTo());
    }

    @Test
    public void retrieve_some_events() throws Exception {
        Instant from = Instant.now().minus(90, ChronoUnit.DAYS).truncatedTo(ChronoUnit.SECONDS);
        Instant to = Instant.now().minus(7, ChronoUnit.DAYS).truncatedTo(ChronoUnit.SECONDS);
        int limit = 5;
        int skip = 2;
        EventsPageResponse eventsPageResponse = getApi().eventsClient().retrieveEvents(from, to, limit, skip, null).get();
        Assert.assertNotNull(eventsPageResponse);
        Assert.assertEquals(from, eventsPageResponse.getFrom());
        Assert.assertTrue(0 < eventsPageResponse.getTotalCount());
        Assert.assertEquals(skip, eventsPageResponse.getSkip());
        Assert.assertEquals(limit, eventsPageResponse.getLimit());
        Assert.assertNotNull(eventsPageResponse.getData());
        Assert.assertEquals(limit, eventsPageResponse.getData().size());
        Assert.assertNotNull(eventsPageResponse.getData().get(0).getId());
        Assert.assertNotNull(eventsPageResponse.getData().get(0).getType());
        Assert.assertNotNull(eventsPageResponse.getData().get(0).getCreatedOn());
        Assert.assertFalse(to.isBefore(eventsPageResponse.getData().get(0).getCreatedOn()));
        Assert.assertEquals(eventsPageResponse.getData().get(0).getCreatedOn(), eventsPageResponse.getTo());
    }
}