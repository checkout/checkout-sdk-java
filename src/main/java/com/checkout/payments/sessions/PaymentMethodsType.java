package com.checkout.payments.sessions;

import com.google.gson.annotations.SerializedName;

public enum PaymentMethodsType {

    @SerializedName("applepay")
    APPLEPAY,

    @SerializedName("bancontact")
    BANCONTACT,

    @SerializedName("card")
    CARD,

    @SerializedName("eps")
    EPS,

    @SerializedName("giropay")
    GIROPAY,

    @SerializedName("googlepay")
    GOOGLEPAY,

    @SerializedName("ideal")
    IDEAL,

    @SerializedName("knet")
    KNET,

    @SerializedName("multibanco")
    MULTIBANCO,

    @SerializedName("p24")
    P24,

    @SerializedName("paypal")
    PAYPAL,

    @SerializedName("sofort")
    SOFORT,

}
