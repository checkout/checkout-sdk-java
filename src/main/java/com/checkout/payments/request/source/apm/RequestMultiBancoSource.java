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
public class RequestMultiBancoSource extends AbstractRequestSource {

    @SerializedName("payment_country")
    private CountryCode paymentCountry;

    @SerializedName("account_holder_name")
    private String accountHolderName;

    @SerializedName("billing_descriptor")
    private String billingDescriptor;

    @Builder
    public RequestMultiBancoSource(final CountryCode paymentCountry,
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
