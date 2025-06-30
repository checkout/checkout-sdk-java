package com.checkout.handlepaymentsandpayouts.flow.paymentsessions.requests.accountholder;

import com.checkout.handlepaymentsandpayouts.flow.paymentsessions.enums.AccountHolderType;
import com.google.gson.annotations.SerializedName;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public final class CorporateAccountHolder extends AbstractAccountHolder {

    /**
     * The account holder's company name.\nThis must be a valid legal name. The following formats for the company_name
     * value will return a field validation error:\n\na single character
     * all numeric characters
     * all punctuation characters
     */
    @SerializedName("company_name")
    private String companyName;

    /**
     * Specifies whether to perform an account name inquiry (ANI) check with the scheme.
     * This is only valid for Visa and Mastercard payments.
     */
    @SerializedName("account_name_inquiry")
    private Boolean accountNameInquiry;


    @Builder
    private CorporateAccountHolder(
            final String companyName,
            final Boolean accountNameInquiry
    ) {
        super(AccountHolderType.CORPORATE);
        this.companyName = companyName;
        this.accountNameInquiry = accountNameInquiry;
    }

    public CorporateAccountHolder() {
        super(AccountHolderType.CORPORATE);
    }
}
