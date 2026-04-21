package com.checkout.payments.request.source.apm;

import com.checkout.common.PaymentSourceType;
import com.checkout.payments.PaymentMethodDetails;
import com.checkout.payments.request.source.AbstractRequestSource;
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

    /**
     * The language to display the payment page in.
     * [Optional]
     */
    private String language;

    /**
     * User-defined field 1.
     * [Optional]
     */
    @SerializedName("user_defined_field1")
    private String userDefinedField1;

    /**
     * User-defined field 2.
     * [Optional]
     */
    @SerializedName("user_defined_field2")
    private String userDefinedField2;

    /**
     * User-defined field 3.
     * [Optional]
     */
    @SerializedName("user_defined_field3")
    private String userDefinedField3;

    /**
     * User-defined field 4.
     * [Optional]
     */
    @SerializedName("user_defined_field4")
    private String userDefinedField4;

    /**
     * User-defined field 5.
     * [Optional]
     */
    @SerializedName("user_defined_field5")
    private String userDefinedField5;

    /**
     * The card token for the payment.
     * [Optional]
     */
    @SerializedName("card_token")
    private String cardToken;

    /**
     * The PTLF value.
     * [Optional]
     */
    private String ptlf;

    /**
     * The type of token.
     * [Optional]
     */
    @SerializedName("token_type")
    private String tokenType;

    /**
     * The token data for Apple Pay payments.
     * [Optional]
     */
    @SerializedName("token_data")
    private ApplePayTokenData tokenData;

    /**
     * Additional payment method details.
     * [Optional]
     */
    @SerializedName("payment_method_details")
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
