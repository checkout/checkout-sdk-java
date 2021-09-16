package com.checkout.payments.links;

import com.checkout.common.Resource;
import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public final class PaymentLinkResponse extends Resource {

    private String id;

    @SerializedName("expires_on")
    private String expiresOn;

    private String reference;

}
