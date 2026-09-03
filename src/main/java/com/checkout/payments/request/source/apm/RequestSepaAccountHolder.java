package com.checkout.payments.request.source.apm;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The account holder's personal information on a SEPA payment source.
 *
 * <p>Maps the account_holder object of PaymentRequestSEPAV4Source. Deliberately not
 * {@link com.checkout.common.AccountHolder}, which is a superset carrying a phone, an
 * identification, a date of birth and a tax ID that this position does not declare. The property
 * names match {@link com.checkout.instruments.create.CreateSepaAccountHolder}, but the positions
 * differ: only the billing address is required here, where the instrument requires the names too.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class RequestSepaAccountHolder {

    /**
     * The account holder's billing address.
     * [Required]
     */
    private RequestSepaBillingAddress billingAddress;

    /**
     * The account holder's first name.
     * [Optional]
     * max 50 characters
     */
    private String firstName;

    /**
     * The account holder's last name.
     * [Optional]
     * max 50 characters
     */
    private String lastName;

    /**
     * The account holder's company name.
     * [Optional]
     * max 50 characters
     */
    private String companyName;

    /**
     * The type of account holder.
     * [Optional]
     * Send this lowercase (individual, corporate). The specification declares it capitalized at this
     * one position, but every other account-holder-type position declares it lowercase and every
     * other Checkout.com SDK sends lowercase. Pending confirmation from the API owners.
     */
    private RequestSepaAccountHolderType type;

}
