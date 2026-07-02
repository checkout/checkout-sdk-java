package com.checkout.sessions.completion;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public final class HostedCompletionInfo extends CompletionInfo {

    private String callbackUrl;

    private String successUrl;

    private String failureUrl;

    @Builder
    private HostedCompletionInfo(final String callbackUrl,
                                 final String successUrl,
                                 final String failureUrl) {
        super(CompletionInfoType.HOSTED);
        this.callbackUrl = callbackUrl;
        this.successUrl = successUrl;
        this.failureUrl = failureUrl;
    }

    public HostedCompletionInfo() {
        super(CompletionInfoType.NON_HOSTED);
    }

}
