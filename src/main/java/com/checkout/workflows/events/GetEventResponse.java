package com.checkout.workflows.events;

import com.checkout.HttpMetadata;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Map;

@Data
@EqualsAndHashCode(callSuper = true)
public final class GetEventResponse extends HttpMetadata {

    private String id;

    private String source;

    private String type;

    private String timestamp;

    private String version;

    private Map<String, Object> data;

}
