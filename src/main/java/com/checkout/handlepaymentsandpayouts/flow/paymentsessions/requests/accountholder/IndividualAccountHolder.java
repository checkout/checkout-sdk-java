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
public final class IndividualAccountHolder extends AbstractAccountHolder {

    /**
     * The account holder's first name.
     * This must be a valid legal name. The following formats for the first_name value will return a field validation
     * error:
     * a single character
     * all numeric characters
     * all punctuation characters
     */
    @SerializedName("first_name")
    private String firstName;

    /**
     * The account holder's last name.
     * This must be a valid legal name. The following formats for the last_name value will return a field validation
     * error:
     * a single character
     * all numeric characters
     * all punctuation characters
     */
    @SerializedName("last_name")
    private String lastName;

    /**
     * The account holder's middle name.
     * This field is required if the issuer_country value returned in the card metadata response is ZA.
     */
    @SerializedName("middle_name")
    private String middleName;

    /**
     * Specifies whether to perform an account name inquiry (ANI) check with the scheme.
     * This is only valid for Visa and Mastercard payments.
     */
    @SerializedName("account_name_inquiry")
    private Boolean accountNameInquiry;

    @Builder
    private IndividualAccountHolder(
            final String firstName,
            final String lastName,
            final String middleName,
            final Boolean accountNameInquiry
    ) {
        super(AccountHolderType.INDIVIDUAL);
        this.firstName = firstName;
        this.lastName = lastName;
        this.middleName = middleName;
        this.accountNameInquiry = accountNameInquiry;
    }

    public IndividualAccountHolder() {
        super(AccountHolderType.INDIVIDUAL);
    }

}
