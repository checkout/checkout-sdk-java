package com.checkout.handlepaymentsandpayouts.payments.postpayments.requests.unreferencedrefundrequest.destination.common.accountholder.governmentaccountholder;

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
 * government account_holder Class
 * The unreferenced refund destination account holder.
 */
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public final class GovernmentAccountHolder extends AbstractAccountHolder {

    /**
     * The account holder's company name.
     * This must be a valid legal name. The following formats for the company_name value will return a field validation
     * error:
     * a single character
     * all numeric characters
     * all punctuation characters
     * [Required]
     * &lt;= 50
     */
    @SerializedName("company_name")
    private String companyName;

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
     * Initializes a new instance of the GovernmentAccountHolder class.
     */
    @Builder
    private GovernmentAccountHolder(
            final String companyName,
            final BillingAddress billingAddress,
            final Phone phone,
            final Identification identification,
            final String email
    ) {
        super(AccountHolderType.GOVERNMENT);
        this.companyName = companyName;
        this.billingAddress = billingAddress;
        this.phone = phone;
        this.identification = identification;
        this.email = email;
    }

    public GovernmentAccountHolder() {
        super(AccountHolderType.GOVERNMENT);
    }

}
