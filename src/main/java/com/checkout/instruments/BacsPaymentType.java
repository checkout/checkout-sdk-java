package com.checkout.instruments;

import com.google.gson.annotations.SerializedName;

/**
 * The type of payment for a Bacs Direct Debit instrument.
 *
 * <p>The wire values are capitalized, and the specification allows these two values only. The
 * equivalent SEPA field is lowercase in the specification, so do not share one type between the
 * two: reusing {@link com.checkout.instruments.update.SepaPaymentType} sends a value the API
 * rejects. Do not replace this enum with {@link com.checkout.payments.PaymentType} either, because
 * that enum also accepts MOTO, Installment, PayLater and Unscheduled, which Bacs does not allow.
 */
public enum BacsPaymentType {

    @SerializedName("Recurring")
    RECURRING,

    @SerializedName("Regular")
    REGULAR

}
