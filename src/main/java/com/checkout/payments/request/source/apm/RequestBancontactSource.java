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
public final class RequestBancontactSource extends AbstractRequestSource {

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

    /**
     * The language to display the payment page in.
     * [Optional]
     */
    private String language;

    @Builder
    private RequestBancontactSource(final CountryCode paymentCountry,
                                   final String accountHolderName,
                                   final String billingDescriptor,
                                   final String language) {
        super(PaymentSourceType.BANCONTACT);
        this.paymentCountry = paymentCountry;
        this.accountHolderName = accountHolderName;
        this.billingDescriptor = billingDescriptor;
        this.language = language;
    }

    public RequestBancontactSource() {
        super(PaymentSourceType.BANCONTACT);
    }

}
