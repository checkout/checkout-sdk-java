package com.checkout;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public final class ContentResponse extends HttpMetadata {

    public ContentResponse(final String content) {
        this.content = content;
    }

    private String content;
}
