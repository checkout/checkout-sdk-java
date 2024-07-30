package com.checkout.payments.contexts;

import com.checkout.HttpMetadata;
import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public final class PaymentContextDetailsResponse extends HttpMetadata {

    private PaymentContextDetailsStatusType status;

    @SerializedName("payment_request")
    private PaymentContextsResponse paymentRequest;

    @SerializedName("partner_metadata")
    private PaymentContextsPartnerMetadata partnerMetadata;
}
