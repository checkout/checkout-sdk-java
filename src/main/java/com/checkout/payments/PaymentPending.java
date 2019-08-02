package com.checkout.payments;

import com.checkout.common.Link;
import com.checkout.common.Resource;
import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class PaymentPending extends Resource {
    private String id;
    private String status;
    private String reference;
    private CustomerResponse customer;
    @SerializedName("3ds")
    private ThreeDSEnrollment threeDS;

    public boolean requiresRedirect() {
        return hasLink("redirect");
    }

    public Link getRedirectLink() {
        return getLink("redirect");
    }
}