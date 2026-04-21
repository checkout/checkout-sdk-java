package com.checkout.payments.request.source.apm;

import com.checkout.common.AccountHolder;
import com.checkout.common.PaymentSourceType;
import com.checkout.payments.BillingDescriptor;
import com.checkout.payments.request.source.AbstractRequestSource;
import com.google.gson.annotations.SerializedName;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public final class RequestSwishSource extends AbstractRequestSource {

    /**
     * The two-letter ISO country code of the payment.
     * [Optional]
     */
    @SerializedName("payment_country")
    private String paymentCountry;

    /**
     * The account holder's details.
     * [Optional]
     */
    @SerializedName("account_holder")
    private AccountHolder accountHolder;

    /**
     * A description of the purchase shown on the customer's statement.
     * [Optional]
     */
    @SerializedName("billing_descriptor")
    private BillingDescriptor billingDescriptor;

    @Builder
    private RequestSwishSource(final String paymentCountry,
                                final AccountHolder accountHolder,
                                final BillingDescriptor billingDescriptor) {
        super(PaymentSourceType.SWISH);
        this.paymentCountry = paymentCountry;
        this.accountHolder = accountHolder;
        this.billingDescriptor = billingDescriptor;
    }

    public RequestSwishSource() {
        super(PaymentSourceType.SWISH);
    }
}
