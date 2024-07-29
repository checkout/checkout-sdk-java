package com.checkout.sessions.channel;

import com.google.gson.annotations.SerializedName;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public final class MerchantInitiatedSession extends ChannelData {

    @SerializedName("request_type")
    private RequestType requestType;

    @Builder
    private MerchantInitiatedSession(final RequestType requestType) {
        super(ChannelType.MERCHANT_INITIATED);
        this.requestType = requestType;
    }

    public MerchantInitiatedSession() {
        super(ChannelType.MERCHANT_INITIATED);
    }

}

