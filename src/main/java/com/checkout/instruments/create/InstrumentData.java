package com.checkout.instruments.create;

import com.checkout.common.CountryCode;
import com.checkout.common.Currency;
import com.checkout.instruments.update.SepaPaymentType;
import com.checkout.payments.request.source.apm.MandateType;
import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * The details of the SEPA mandate being stored.
 *
 * <p>The payment type is the SEPA enum, whose wire values are lowercase. The equivalent Bacs Direct
 * Debit field is capitalized, so the two must not share a type: sending
 * {@link com.checkout.instruments.BacsPaymentType} values here produces a request the API rejects.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public final class InstrumentData {

    /**
     * The type of mandate.
     * [Optional]
     * Enum: "Core" "B2B"
     */
    private MandateType type;

    /**
     * The International Bank Account Number (IBAN) of the account.
     * [Required]
     * min 15 characters
     * max 34 characters
     */
    @SerializedName("account_number")
    private String accountNumber;

    /**
     * @deprecated Use {@link #getAccountNumber()}.
     */
    @Deprecated
    public String getAccoountNumber() {
        return accountNumber;
    }

    /**
     * @deprecated Use {@link #setAccountNumber(String)}.
     */
    @Deprecated
    public void setAccoountNumber(final String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public static class InstrumentDataBuilder {

        /**
         * @deprecated Use {@link #accountNumber(String)}.
         */
        @Deprecated
        public InstrumentDataBuilder accoountNumber(final String accountNumber) {
            return accountNumber(accountNumber);
        }
    }

    /**
     * The country of the account.
     * [Required]
     * min 2 characters
     * max 2 characters
     */
    private CountryCode country;

    /**
     * The currency of the account.
     * [Required]
     * min 3 characters
     * max 3 characters
     */
    private Currency currency;

    /**
     * The type of payment. recurring or regular.
     * [Required]
     */
    private SepaPaymentType paymentType;

    /**
     * The mandate ID. If a mandate ID is not provided, a new, random mandate ID will be generated.
     * [Optional]
     * min 1 characters
     * max 35 characters
     */
    private String mandateId;

    /**
     * The date on which the mandate was signed. Required if mandateId is provided. Ignored and set
     * as the current date if mandateId is not provided.
     * [Optional]
     * Format: yyyy-MM-dd
     */
    private LocalDate dateOfSignature;

}
