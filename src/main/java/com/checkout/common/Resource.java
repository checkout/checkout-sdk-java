package com.checkout.common;

import com.checkout.HttpMetadata;
import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public abstract class Resource extends HttpMetadata {

    @SerializedName("_links")
    private Map<String, Link> links = new HashMap<>();

    public Link getSelfLink() {
        return getLink("self");
    }

    public Link getLink(final String relation) {
        return links.get(relation);
    }

}
