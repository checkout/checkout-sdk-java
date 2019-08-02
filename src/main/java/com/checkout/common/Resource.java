package com.checkout.common;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@Data
@NoArgsConstructor
public class Resource {

    @SerializedName("_links")
    private Map<String, Link> links = new HashMap<>();
    @Expose(deserialize = false, serialize = false)
    private String requestId;

    public Link getSelfLink() {
        return getLink("self");
    }

    public boolean hasLink(String relation) {
        return links.containsKey(relation);
    }

    public Link getLink(String relation) {
        return links.get(relation);
    }
}
