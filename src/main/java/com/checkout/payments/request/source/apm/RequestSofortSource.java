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
public final class RequestSofortSource extends AbstractRequestSource {

    @SerializedName("countryCode")
    private CountryCode countryCode;

    @SerializedName("languageCode")
    private String languageCode;

    @Builder
    private RequestSofortSource(final CountryCode countryCode, final String languageCode) {
        super(PaymentSourceType.SOFORT);
        this.countryCode = countryCode;
        this.languageCode = languageCode;
    }

    public RequestSofortSource() {
        super(PaymentSourceType.SOFORT);
    }

}
