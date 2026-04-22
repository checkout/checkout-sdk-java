package com.checkout.payments.request.source.apm;

import com.checkout.common.CountryCode;
import com.checkout.common.PaymentSourceType;
import com.checkout.payments.request.source.AbstractRequestSource;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public final class RequestMultiBancoSource extends AbstractRequestSource {

    /**
     * The two-letter ISO country code of the payment.
     * [Optional]
     */
    private CountryCode paymentCountry;

    /**
     * The account holder's name.
     * [Optional]
     * min 3 max 100
     */
    private String accountHolderName;

    /**
     * A description of the purchase shown on the customer's statement.
     * [Optional]
     */
    private String billingDescriptor;

    @Builder
    private RequestMultiBancoSource(final CountryCode paymentCountry,
                                   final String accountHolderName,
                                   final String billingDescriptor) {
        super(PaymentSourceType.MULTIBANCO);
        this.paymentCountry = paymentCountry;
        this.accountHolderName = accountHolderName;
        this.billingDescriptor = billingDescriptor;
    }

    public RequestMultiBancoSource() {
        super(PaymentSourceType.MULTIBANCO);
    }

}
