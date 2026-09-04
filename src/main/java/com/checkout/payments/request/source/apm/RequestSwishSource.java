package com.checkout.payments.request.source.apm;

import com.checkout.common.CountryCode;
import com.checkout.common.PaymentSourceType;
import com.checkout.payments.request.source.AbstractRequestSource;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Swish source.
 */
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public final class RequestSwishSource extends AbstractRequestSource {

    /**
     * The two-letter ISO country code of the payment.
      * [Required]
      * Enum: "SE"
     */
     private CountryCode paymentCountry;

    /**
     * The account holder's details.
      * [Required]
     */
    private RequestSwishAccountHolder accountHolder;

    /**
     * A description of the purchase shown on the customer's statement.
     * [Optional]
     */
    private RequestSwishBillingDescriptor billingDescriptor;

    @Builder
    private RequestSwishSource(final CountryCode paymentCountry,
                                final RequestSwishAccountHolder accountHolder,
                                final RequestSwishBillingDescriptor billingDescriptor) {
        super(PaymentSourceType.SWISH);
        this.paymentCountry = paymentCountry;
        this.accountHolder = accountHolder;
        this.billingDescriptor = billingDescriptor;
    }

    public RequestSwishSource() {
        super(PaymentSourceType.SWISH);
    }
}
