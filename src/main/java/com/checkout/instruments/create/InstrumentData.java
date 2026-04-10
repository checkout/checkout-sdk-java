package com.checkout.instruments.create;

import com.checkout.common.CountryCode;
import com.checkout.common.Currency;
import com.checkout.payments.PaymentType;
import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public final class InstrumentData {

    /**
     * The SEPA account number.
     * [Optional]
     */
    @SerializedName("account_number")
    private String accoountNumber;

    /**
     * The country of the SEPA account.
     * [Optional]
     */
    private CountryCode country;

    /**
     * The currency of the SEPA account.
     * [Optional]
     */
    private Currency currency;

    /**
     * The payment type for this instrument.
     * [Optional]
     */
    @SerializedName("payment_type")
    private PaymentType paymentType;

    /**
     * The unique identifier of the SEPA mandate.
     * [Optional]
     */
    @SerializedName("mandate_id")
    private String mandateId;

    /**
     * The date the mandate was signed.
     * [Optional]
     * Format: yyyy-MM-dd
     */
    @SerializedName("date_of_signature")
    private LocalDate dateOfSignature;

}
