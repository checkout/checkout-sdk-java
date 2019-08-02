package com.checkout.common;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.HashMap;
import java.util.Map;

public class Resource {

    @SerializedName("_links")
    private Map<String, Link> links;
    @Expose(deserialize = false, serialize = false)
    private String requestId;

    public Resource() {
        links = new HashMap<>();
    }

    public Map<String, Link> getLinks() {
        return links;
    }

    public void setLinks(Map<String, Link> links) {
        this.links = links;
    }

    public Link getSelfLink() {
        return getLink("self");
    }

    public boolean hasLink(String relation) {
        return links.containsKey(relation);
    }

    public Link getLink(String relation) {
        return links.get(relation);
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }
}
