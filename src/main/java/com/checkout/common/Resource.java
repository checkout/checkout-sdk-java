package com.checkout.common;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@Data
@NoArgsConstructor
public abstract class Resource {

    @SerializedName("_links")
    private Map<String, Link> links = new HashMap<>();

    @Expose(deserialize = false, serialize = false)
    private ApiResponseInfo apiResponseInfo;

    public Link getSelfLink() {
        return getLink("self");
    }

    public Link getLink(final String relation) {
        return links.get(relation);
    }

}
