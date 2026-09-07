package com.checkout.balances;

import lombok.Data;

/**
 * The bank details for a single funding rail.
 * {@code beneficiaryAccountName} and {@code bankName} are the only fields always returned. The
 * remaining fields vary by rail and the receiving bank's jurisdiction, and are omitted when they
 * do not apply.
 */
@Data
public final class TopUpFundingDetails {

    /**
     * The name of the account that receives the funds.
     * [Required]
     */
    private String beneficiaryAccountName;

    /**
     * The address of the beneficiary, if the rail requires it.
     * [Optional]
     */
    private String beneficiaryAddress;

    /**
     * The name of the bank that receives the funds.
     * [Required]
     */
    private String bankName;

    /**
     * The address of the receiving bank, if the rail requires it.
     * [Optional]
     */
    private String bankAddress;

    /**
     * The account number of the receiving account.
     * [Optional]
     */
    private String accountNumber;

    /**
     * The sort code of the receiving bank. Returned for United Kingdom domestic transfers.
     * [Optional]
     */
    private String sortCode;

    /**
     * The routing number of the receiving bank. Returned for United States domestic transfers.
     * [Optional]
     */
    private String routingNumber;

    /**
     * The International Bank Account Number of the receiving account.
     * [Optional]
     */
    private String iban;

    /**
     * The SWIFT or BIC code of the receiving bank. Returned for international transfers.
     * [Optional]
     */
    private String swiftCode;

}
