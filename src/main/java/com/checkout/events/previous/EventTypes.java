package com.checkout.events.previous;

import lombok.Data;

import java.util.List;

@Data
public final class EventTypes {

    private String version;

    private List<String> eventTypes;

}
