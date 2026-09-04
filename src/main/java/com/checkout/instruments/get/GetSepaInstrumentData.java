package com.checkout.instruments.get;

import com.checkout.common.CountryCode;
import com.checkout.common.Currency;
import com.checkout.instruments.update.SepaPaymentType;
import com.checkout.payments.request.source.apm.MandateType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * The details of a stored SEPA mandate.
 *
 * <p>The payment type is the SEPA enum, whose wire values are lowercase. The equivalent Bacs
 * Direct Debit field is capitalized, so the two must not share a type.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class GetSepaInstrumentData {

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
    private String accountNumber;

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
     * The type of payment.
     * [Required]
     */
    private SepaPaymentType paymentType;

    /**
     * The mandate ID. If this value was not provided when the instrument was created, it may take
     * up to five seconds for the generated mandate ID to be available.
     * [Required]
     * min 1 characters
     * max 35 characters
     */
    private String mandateId;

    /**
     * The date the mandate was signed. If the mandate ID was not provided when the instrument was
     * created, it may take up to five seconds for the generated date of signature to be available.
     * [Required]
     * Format: yyyy-MM-dd
     * min 10 characters
     * max 10 characters
     */
    private LocalDate dateOfSignature;

}
