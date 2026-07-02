package com.checkout.payments.contexts;

import com.checkout.HttpMetadata;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public final class PaymentContextDetailsResponse extends HttpMetadata {

    /**
     * The unique identifier of the payment context.
     * [Optional]
     */
    private String id;

    private PaymentContextDetailsStatusType status;

    private PaymentContextsResponse paymentRequest;

    private PaymentContextsPartnerMetadata partnerMetadata;
}
