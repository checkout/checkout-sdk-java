package com.checkout.workflows.beta.events;

import lombok.Data;

import java.util.Map;

@Data
public final class GetEventResponse {

    private String id;

    private String source;

    private String type;

    private String timestamp;

    private String version;

    private Map<String, Object> data;

}
