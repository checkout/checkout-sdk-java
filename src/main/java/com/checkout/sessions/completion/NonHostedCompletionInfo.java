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
public final class NonHostedCompletionInfo extends CompletionInfo {

    private final String callbackUrl;

    private final String challengeNotificationUrl;

    @Builder
    private NonHostedCompletionInfo(final String callbackUrl, final String challengeNotificationUrl) {
        super(CompletionInfoType.NON_HOSTED);
        this.callbackUrl = callbackUrl;
        this.challengeNotificationUrl = challengeNotificationUrl;
    }

}
