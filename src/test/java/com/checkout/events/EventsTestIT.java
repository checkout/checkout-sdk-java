package com.checkout.events;

import com.checkout.PlatformType;
import com.checkout.SandboxTestFixture;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class EventsTestIT extends SandboxTestFixture {

    public EventsTestIT() {
        super(PlatformType.CLASSIC);
    }

    @BeforeEach
    public void before() throws Exception {
        Thread.sleep(5000);
    }

    @Test
    public void retrieve_all_event_types() throws Exception {
        final List<EventTypesResponse> allEventTypesResponses = getApi().eventsClient().retrieveAllEventTypes(null).get();
        assertNotNull(allEventTypesResponses);
        assertEquals(2, allEventTypesResponses.size());
    }

    @Test
    public void retrieve_v1_event_types() throws Exception {
        final List<EventTypesResponse> v1EventTypesResponses = getApi().eventsClient().retrieveAllEventTypes("1.0").get();
        assertNotNull(v1EventTypesResponses);
        assertEquals(1, v1EventTypesResponses.size());
        assertEquals("1.0", v1EventTypesResponses.get(0).getVersion());
        assertNotNull(v1EventTypesResponses.get(0).getEventTypes());
        assertFalse(v1EventTypesResponses.get(0).getEventTypes().isEmpty());
    }

    @Test
    public void retrieve_v2_event_types() throws Exception {
        final List<EventTypesResponse> v2EventTypesResponses = getApi().eventsClient().retrieveAllEventTypes("2.0").get();
        assertNotNull(v2EventTypesResponses);
        assertEquals(1, v2EventTypesResponses.size());
        assertEquals("2.0", v2EventTypesResponses.get(0).getVersion());
        assertNotNull(v2EventTypesResponses.get(0).getEventTypes());
        assertFalse(v2EventTypesResponses.get(0).getEventTypes().isEmpty());
    }

    // TODO: Find ways to test events APIs again
}