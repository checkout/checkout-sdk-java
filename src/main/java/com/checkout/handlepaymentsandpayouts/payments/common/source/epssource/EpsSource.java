package com.checkout.handlepaymentsandpayouts.payments.common.source.epssource;

import com.checkout.handlepaymentsandpayouts.payments.common.source.AbstractSource;
import com.checkout.handlepaymentsandpayouts.payments.common.source.SourceType;
import com.checkout.handlepaymentsandpayouts.payments.common.source.epssource.accountholder.AccountHolder;
import com.google.gson.annotations.SerializedName;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * eps source Class
 * The source of the payment
 */
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public final class EpsSource extends AbstractSource {

    /**
     * Purpose of the payment as appearing on customer's bank statement.
     * [Optional]
     * &lt; 27
     */
    private String purpose;

    /**
     * Bank Identifier Code (BIC). It can be exactly 8 characters or 11 characters long.
     * [Optional]
     * &lt; 11
     * 8 characters
     * 11 characters
     */
    private String bic;

    /**
     * International Bank Account Number (IBAN) without whitespaces.
     * [Optional]
     * &lt; 34
     */
    private String iban;

    /**
     * Account holder information.
     * [Optional]
     */
    @SerializedName("account_holder_name")
    private String accountHolderName;

    /**
     * The account holder details
     * [Optional]
     */
    @SerializedName("account_holder")
    private AccountHolder accountHolder;

    /**
     * Initializes a new instance of the EpsSource class.
     */
    @Builder
    private EpsSource(
            final String purpose,
            final String bic,
            final String iban,
            final String accountHolderName,
            final AccountHolder accountHolder
    ) {
        super(SourceType.EPS);
        this.purpose = purpose;
        this.bic = bic;
        this.iban = iban;
        this.accountHolderName = accountHolderName;
        this.accountHolder = accountHolder;
    }

    public EpsSource() {
        super(SourceType.EPS);
    }

}
