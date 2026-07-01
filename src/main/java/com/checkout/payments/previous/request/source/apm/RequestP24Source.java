package com.checkout.payments.previous.request.source.apm;

import com.checkout.common.CountryCode;
import com.checkout.common.PaymentSourceType;
import com.checkout.payments.previous.request.source.AbstractRequestSource;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public final class RequestP24Source extends AbstractRequestSource {

    private CountryCode paymentCountry;

    private String accountHolderName;

    private String accountHolderEmail;

    private String billingDescriptor;

    @Builder
    private RequestP24Source(final CountryCode paymentCountry,
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
