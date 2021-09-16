package com.checkout.common;

import com.google.gson.annotations.SerializedName;

public enum PaymentSourceType {

    @SerializedName("card")
    CARD,
    @SerializedName("id")
    ID,
    @SerializedName("network_token")
    NETWORK_TOKEN,
    @SerializedName("token")
    TOKEN,
    @SerializedName("customer")
    CUSTOMER,
    @SerializedName("dlocal")
    DLOCAL,
    @SerializedName("baloto")
    BALOTO,
    @SerializedName("boleto")
    BOLETO,
    @SerializedName("fawry")
    FAWRY,
    @SerializedName("giropay")
    GIROPAY,
    @SerializedName("ideal")
    IDEAL,
    @SerializedName("oxxo")
    OXXO,
    @SerializedName("pagofacil")
    PAGO_FACIL,
    @SerializedName("rapipago")
    RAPI_PAGO,
    @SerializedName("klarna")
    KLARNA,
    @SerializedName("id")
    SEPA

}
