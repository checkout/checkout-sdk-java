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
 * @deprecated Sofort was deprecated as a payment source type on 2024/12/03.
 */
@Deprecated
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public final class RequestSofortSource extends AbstractRequestSource {

    /**
     * The ISO 3166-1 alpha-2 country code for the Sofort payment.
     * [Optional]
     */
    private CountryCode countryCode;

    /**
     * The language code for the Sofort payment.
     * [Optional]
     */
    private String languageCode;

    @Builder
    private RequestSofortSource(final CountryCode countryCode, final String languageCode) {
        super(PaymentSourceType.SOFORT);
        this.countryCode = countryCode;
        this.languageCode = languageCode;
    }

    @Deprecated
    public RequestSofortSource() {
        super(PaymentSourceType.SOFORT);
    }

}
