package com.checkout.handlepaymentsandpayouts.flow.paymentsessionscomplete.requests.accountholder;

import com.checkout.handlepaymentsandpayouts.flow.paymentsessionscomplete.enums.AccountHolderType;
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
public final class GovernmentAccountHolder extends AbstractAccountHolder {

    /**
     * The account holder's company name.\
     * This must be a valid legal name. The following formats for the company_name value will return a field validation
     * error:
     * a single character
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
    private GovernmentAccountHolder(
            final String companyName,
            final Boolean accountNameInquiry
    ) {
        super(AccountHolderType.GOVERNMENT);
        this.companyName = companyName;
        this.accountNameInquiry = accountNameInquiry;
    }

    public GovernmentAccountHolder() {
        super(AccountHolderType.GOVERNMENT);
    }


}
