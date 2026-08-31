package com.checkout.instruments.create;

import com.checkout.common.BankDetails;
import com.checkout.common.CustomerResponse;
import com.checkout.common.InstrumentType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * Store bank account instrument response.
 *
 * <p>The id and fingerprint are inherited from {@link CreateInstrumentResponse}. This is one of
 * only two store responses the specification declares a customer on, the other being
 * {@link CreateInstrumentTokenResponse}.
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public final class CreateInstrumentBankAccountResponse extends CreateInstrumentResponse {

    /**
     * The type of instrument.
     * [Required]
     */
    private final InstrumentType type = InstrumentType.BANK_ACCOUNT;

    /**
     * Details of the bank.
     * [Optional]
     */
    private BankDetails bank;

    /**
     * 8 or 11 character code which identifies the bank or bank branch.
     * [Optional]
     */
    private String swiftBic;

    /**
     * Number (which can contain letters) that identifies the account.
     * [Optional]
     */
    private String accountNumber;

    /**
     * Code that identifies the bank.
     * [Optional]
     */
    private String bankCode;

    /**
     * Internationally agreed standard for identifying bank account.
     * [Optional]
     */
    private String iban;

    /**
     * The customer that the instrument is associated with.
     * [Optional]
     */
    private CustomerResponse customer;

}
