package com.checkout.payments.request.source.apm;

import com.checkout.common.CountryCode;
import com.checkout.common.PaymentSourceType;
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
public class RequestP24Source extends AbstractRequestSource {

    @SerializedName("payment_country")
    private CountryCode paymentCountry;

    @SerializedName("account_holder_name")
    private String accountHolderName;

    @SerializedName("account_holder_email")
    private String accountHolderEmail;

    @SerializedName("billing_descriptor")
    private String billingDescriptor;

    @Builder
    public RequestP24Source(final CountryCode paymentCountry,
                            final String accountHolderName,
                            final String accountHolderEmail,
                            final String billingDescriptor) {
        super(PaymentSourceType.P24);
        this.paymentCountry = paymentCountry;
        this.accountHolderName = accountHolderName;
        this.accountHolderEmail = accountHolderEmail;
        this.billingDescriptor = billingDescriptor;
    }

    public RequestP24Source() {
        super(PaymentSourceType.P24);
    }

}
