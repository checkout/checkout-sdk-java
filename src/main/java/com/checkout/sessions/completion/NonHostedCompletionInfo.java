package com.checkout.sessions.completion;

import com.google.gson.annotations.SerializedName;
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

    @SerializedName("callback_url")
    private final String callbackUrl;

    @Builder
    private NonHostedCompletionInfo(final String callbackUrl) {
        super(CompletionInfoType.NON_HOSTED);
        this.callbackUrl = callbackUrl;
    }

}
