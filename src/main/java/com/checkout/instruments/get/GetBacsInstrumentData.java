package com.checkout.instruments.get;

import com.checkout.common.CountryCode;
import com.checkout.common.Currency;
import com.checkout.instruments.BacsPaymentType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The details of a stored Bacs Direct Debit account.
 *
 * <p>The retrieve shape adds four read-back properties that the store and update shapes do not
 * declare: status, matchStatus, description and mandateId.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class GetBacsInstrumentData {

    /**
     * The account number of the Bacs Direct Debit account.
     * [Required]
     * min 8 characters
     * max 8 characters
     */
    private String accountNumber;

    /**
     * The sort code of the Bacs Direct Debit account.
     * [Required]
     * min 6 characters
     * max 6 characters
     */
    private String bankCode;

    /**
     * The country of the account, as an ISO 3166-1 alpha-2 code.
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
    private BacsPaymentType paymentType;

    /**
     * Whether vault accepted a partial match when looking up the Bacs instrument for the supplied
     * account details.
     * [Optional]
     */
    private Boolean allowPartialMatch;

    /**
     * The validation status of the account. The specification declares no enum for this property.
     * [Optional]
     */
    private String status;

    /**
     * The result of matching the account holder name against the account owner. The specification
     * declares no enum for this property.
     * [Optional]
     */
    private String matchStatus;

    /**
     * A human-readable description of the validation result.
     * [Optional]
     */
    private String description;

    /**
     * The identifier of the Bacs Direct Debit mandate.
     * [Optional]
     */
    private String mandateId;

}
