package com.checkout.instruments.create;

import com.checkout.common.CountryCode;
import com.checkout.common.Currency;
import com.checkout.payments.PaymentType;
import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public final class InstrumentData {

    @SerializedName("account_number")
    private String accoountNumber;

    private CountryCode country;

    private Currency currency;

    @SerializedName("payment_type")
    private PaymentType paymentType;

    @SerializedName("mandate_id")
    private String mandateId;

    @SerializedName("date_of_signature")
    private Instant dateOfSignature;

}
