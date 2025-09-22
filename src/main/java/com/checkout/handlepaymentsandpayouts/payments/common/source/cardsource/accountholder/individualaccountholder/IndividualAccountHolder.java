package com.checkout.handlepaymentsandpayouts.payments.common.source.cardsource.accountholder.individualaccountholder;

import com.checkout.handlepaymentsandpayouts.payments.common.source.cardsource.accountholder.AbstractAccountHolder;
import com.checkout.handlepaymentsandpayouts.payments.common.source.cardsource.accountholder.AccountHolderType;
import com.checkout.handlepaymentsandpayouts.payments.common.source.cardsource.accountholder.individualaccountholder.accountnameinquirydetails.AccountNameInquiryDetails;
import com.google.gson.annotations.SerializedName;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * individual account_holder Class
 * Information about the account holder of the card
 */
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public final class IndividualAccountHolder extends AbstractAccountHolder {

    /**
     * The card account holder's first name
     * A valid and legal name must be populated in this field. The populated value cannot be only one character or all
     * numeric.
     * [Required]
     * &lt;= 35
     */
    @SerializedName("first_name")
    private String firstName;

    /**
     * The card account holder's last name
     * A valid and legal name must be populated in this field. The populated value cannot be only one character or all
     * numeric.
     * [Required]
     * &lt;= 35
     */
    @SerializedName("last_name")
    private String lastName;

    /**
     * The result of the Account Name Inquiry check.
     * [Required]
     */
    @SerializedName("account_name_inquiry")
    private AccountNameInquiryType accountNameInquiry;

    /**
     * The card account holder's middle name
     * Conditional - required when the card metadata issuer_country = ZA (South Africa)
     * [Optional]
     * &lt;= 35
     */
    @SerializedName("middle_name")
    private String middleName;

    /**
     * Details of the Account Name Inquiry check.
     * [Optional]
     */
    @SerializedName("account_name_inquiry_details")
    private AccountNameInquiryDetails accountNameInquiryDetails;

    /**
     * Initializes a new instance of the IndividualAccountHolder class.
     */
    @Builder
    private IndividualAccountHolder(
            final String firstName,
            final String lastName,
            final AccountNameInquiryType accountNameInquiry,
            final String middleName,
            final AccountNameInquiryDetails accountNameInquiryDetails
    ) {
        super(AccountHolderType.INDIVIDUAL);
        this.firstName = firstName;
        this.lastName = lastName;
        this.accountNameInquiry = accountNameInquiry;
        this.middleName = middleName;
        this.accountNameInquiryDetails = accountNameInquiryDetails;
    }

    public IndividualAccountHolder() {
        super(AccountHolderType.INDIVIDUAL);
    }

}
