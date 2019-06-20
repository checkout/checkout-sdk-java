package com.checkout.sources;

import com.checkout.common.Resource;

public class SourceResponse extends Resource {
    private SourceProcessed source;

    public static SourceResponse from(SourceProcessed processedResponse) {
        SourceResponse response = new SourceResponse();
        response.source = processedResponse;
        return response;
    }

    public SourceProcessed getSource() {
        return source;
    }

    public void setSource(SourceProcessed source) {
        this.source = source;
    }

    public boolean isPending() {
        return source == null;
    }
}