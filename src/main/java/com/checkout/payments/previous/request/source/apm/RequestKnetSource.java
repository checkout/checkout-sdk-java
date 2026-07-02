package com.checkout.payments.previous.request.source.apm;

import com.checkout.common.PaymentSourceType;
import com.checkout.payments.PaymentMethodDetails;
import com.checkout.payments.previous.request.source.AbstractRequestSource;
import com.checkout.tokens.ApplePayTokenData;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public final class RequestKnetSource extends AbstractRequestSource {

    private String language;

    private String userDefinedField1;

    private String userDefinedField2;

    private String userDefinedField3;

    private String userDefinedField4;

    private String userDefinedField5;

    private String cardToken;

    private String ptlf;

    private String tokenType;

    private ApplePayTokenData tokenData;

    private PaymentMethodDetails paymentMethodDetails;

    @Builder
    private RequestKnetSource(final String language,
                              final String userDefinedField1,
                              final String userDefinedField2,
                              final String userDefinedField3,
                              final String userDefinedField4,
                              final String userDefinedField5,
                              final String cardToken,
                              final String ptlf,
                              final String tokenType,
                              final ApplePayTokenData tokenData,
                              final PaymentMethodDetails paymentMethodDetails) {
        super(PaymentSourceType.KNET);
        this.language = language;
        this.userDefinedField1 = userDefinedField1;
        this.userDefinedField2 = userDefinedField2;
        this.userDefinedField3 = userDefinedField3;
        this.userDefinedField4 = userDefinedField4;
        this.userDefinedField5 = userDefinedField5;
        this.cardToken = cardToken;
        this.ptlf = ptlf;
        this.tokenType = tokenType;
        this.tokenData = tokenData;
        this.paymentMethodDetails = paymentMethodDetails;
    }

    public RequestKnetSource() {
        super(PaymentSourceType.KNET);
    }

}
