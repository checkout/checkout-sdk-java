package com.checkout.handlepaymentsandpayouts.payments.postpayments.requests.unreferencedrefundrequest.destination.common.accountholder.individualaccountholder;

import com.checkout.handlepaymentsandpayouts.payments.postpayments.requests.unreferencedrefundrequest.destination.common.accountholder.AbstractAccountHolder;
import com.checkout.handlepaymentsandpayouts.payments.postpayments.requests.unreferencedrefundrequest.destination.common.accountholder.AccountHolderType;
import com.checkout.handlepaymentsandpayouts.payments.postpayments.requests.unreferencedrefundrequest.destination.common.accountholder.common.billingaddress.BillingAddress;
import com.checkout.handlepaymentsandpayouts.payments.postpayments.requests.unreferencedrefundrequest.destination.common.accountholder.common.identification.Identification;
import com.checkout.handlepaymentsandpayouts.payments.postpayments.requests.unreferencedrefundrequest.destination.common.accountholder.common.phone.Phone;
import com.google.gson.annotations.SerializedName;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * individual account_holder Class
 * The unreferenced refund destination account holder.
 */
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public final class IndividualAccountHolder extends AbstractAccountHolder {

    /**
     * The account holder's first name.
     * This must be a valid legal name. The following formats for the first_name value will return a field validation
     * error:
     * a single character
     * all numeric characters
     * all punctuation characters
     * [Required]
     * &lt;= 50
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
     * [Required]
     * &lt;= 50
     */
    @SerializedName("last_name")
    private String lastName;

    /**
     * The account holder's middle name.
     * [Optional]
     * &lt;= 50
     */
    @SerializedName("middle_name")
    private String middleName;

    /**
     * The account holder's billing address.
     * If your company is incorporated in the United States, this field is required for all unreferenced refunds you
     * perform.
     * [Optional]
     */
    @SerializedName("billing_address")
    private BillingAddress billingAddress;

    /**
     * The account holder's phone number.
     * [Optional]
     */
    private Phone phone;

    /**
     * The account holder's identification.
     * Providing identification details for the unreferenced refund recipient increases the likelihood of a successful
     * unreferenced refund.
     * [Optional]
     */
    private Identification identification;

    /**
     * The account holder's email address.
     * [Optional]
     * &lt;= 255
     */
    private String email;

    /**
     * The account holder's date of birth, in the format YYYY-MM-DD.
     * [Optional]
     * 10 characters
     */
    @SerializedName("date_of_birth")
    private String dateOfBirth;

    /**
     * The account holder's country of birth, as a two-letter ISO country code.
     * [Optional]
     * 2 characters
     */
    @SerializedName("country_of_birth")
    private String countryOfBirth;

    /**
     * Initializes a new instance of the IndividualAccountHolder class.
     */
    @Builder
    private IndividualAccountHolder(
        final String firstName,
        final String lastName,
        final String middleName,
        final BillingAddress billingAddress,
        final Phone phone,
        final Identification identification,
        final String email,
        final String dateOfBirth,
        final String countryOfBirth
    ) {
        super(AccountHolderType.INDIVIDUAL);
        this.firstName = firstName;
        this.lastName = lastName;
        this.middleName = middleName;
        this.billingAddress = billingAddress;
        this.phone = phone;
        this.identification = identification;
        this.email = email;
        this.dateOfBirth = dateOfBirth;
        this.countryOfBirth = countryOfBirth;
    }

    public IndividualAccountHolder() {
        super(AccountHolderType.INDIVIDUAL);
    }

}
