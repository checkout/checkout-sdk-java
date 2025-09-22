package com.checkout.handlepaymentsandpayouts.payments.common.source.cardsource.accountholder.corporateaccountholder;

import com.checkout.handlepaymentsandpayouts.payments.common.source.cardsource.accountholder.AbstractAccountHolder;
import com.checkout.handlepaymentsandpayouts.payments.common.source.cardsource.accountholder.AccountHolderType;
import com.checkout.handlepaymentsandpayouts.payments.common.source.cardsource.accountholder.common.AccountNameInquiryType;
import com.google.gson.annotations.SerializedName;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * corporate account_holder Class
 * Information about the account holder of the card
 */
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public final class CorporateAccountHolder extends AbstractAccountHolder {

    /**
     * The card account holder's company name
     * A valid and legal name must be populated in this field. The populated value cannot be only one character or all
     * numeric.
     * [Required]
     * &lt;= 35
     */
    @SerializedName("company_name")
    private String companyName;

    /**
     * The Account Name Inquiry check result.
     * [Required]
     */
    @SerializedName("account_name_inquiry")
    private AccountNameInquiryType accountNameInquiry;

    /**
     * Initializes a new instance of the CorporateAccountHolder class.
     */
    @Builder
    private CorporateAccountHolder(
        final String companyName,
        final AccountNameInquiryType accountNameInquiry
    ) {
        super(AccountHolderType.CORPORATE);
        this.companyName = companyName;
        this.accountNameInquiry = accountNameInquiry;
    }

    public CorporateAccountHolder() {
        super(AccountHolderType.CORPORATE);
    }

}
