package com.checkout.sources;

import com.checkout.common.Resource;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class SourceResponse extends Resource {
    private SourceProcessed source;

    public static SourceResponse from(SourceProcessed processedResponse) {
        SourceResponse response = new SourceResponse();
        response.source = processedResponse;
        return response;
    }

    public boolean isPending() {
        return source == null;
    }
}