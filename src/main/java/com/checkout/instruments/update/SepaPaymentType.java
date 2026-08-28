package com.checkout.instruments.update;

import com.google.gson.annotations.SerializedName;

/**
 * The type of payment for a SEPA instrument.
 *
 * <p>The wire values are lowercase, and the specification allows these two values only. The
 * equivalent Bacs Direct Debit field is capitalized, so do not share one type between the two:
 * reusing {@link com.checkout.instruments.BacsPaymentType} sends a value the API rejects. Do not
 * replace this enum with {@link com.checkout.payments.PaymentType} either, whose constants
 * serialize capitalized and which also accepts MOTO, Installment, PayLater and Unscheduled, none of
 * which SEPA allows.
 */
public enum SepaPaymentType {

    @SerializedName("recurring")
    RECURRING,

    @SerializedName("regular")
    REGULAR

}
