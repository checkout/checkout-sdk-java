package com.checkout.sessions.completion;

import lombok.Data;

@Data
public abstract class CompletionInfo {

    protected final CompletionInfoType type;

    protected CompletionInfo(final CompletionInfoType type) {
        this.type = type;
    }

}
