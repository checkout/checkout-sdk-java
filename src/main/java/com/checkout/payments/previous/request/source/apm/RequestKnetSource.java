package com.checkout.payments.previous.request.source.apm;

import com.checkout.common.PaymentSourceType;
import com.checkout.payments.PaymentMethodsDetails;
import com.checkout.payments.previous.request.source.AbstractRequestSource;
import com.checkout.tokens.ApplePayTokenData;
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
public final class RequestKnetSource extends AbstractRequestSource {

    private String language;

    @SerializedName("user_defined_field1")
    private String userDefinedField1;

    @SerializedName("user_defined_field2")
    private String userDefinedField2;

    @SerializedName("user_defined_field3")
    private String userDefinedField3;

    @SerializedName("user_defined_field4")
    private String userDefinedField4;

    @SerializedName("user_defined_field5")
    private String userDefinedField5;

    @SerializedName("card_token")
    private String cardToken;

    private String ptlf;

    @SerializedName("token_type")
    private String tokenType;

    @SerializedName("token_data")
    private ApplePayTokenData tokenData;

    @SerializedName("payment_method_details")
    private PaymentMethodsDetails paymentMethodsDetails;

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
                              final PaymentMethodsDetails paymentMethodsDetails) {
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
        this.paymentMethodsDetails = paymentMethodsDetails;
    }

    public RequestKnetSource() {
        super(PaymentSourceType.KNET);
    }

}
