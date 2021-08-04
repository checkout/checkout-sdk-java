package com.checkout.common.beta;

import com.checkout.common.ApiResponseInfo;
import com.checkout.common.Link;
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

    protected Link getSelfLink() {
        return getLink(LinkType.SELF);
    }

    protected boolean hasLink(final LinkType linkType) {
        return links.containsKey(linkType.getKey());
    }

    protected boolean hasLink(final String linkType) {
        return links.containsKey(linkType);
    }

    protected Link getLink(final LinkType linkType) {
        return links.get(linkType.getKey());
    }

    protected Link getLink(final String linkType) {
        return links.get(linkType);
    }

}
