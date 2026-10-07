package com.checkout.handlepaymentsandpayouts.setups.entities.paymentMethods.cashapp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The customer's Cash App profile that they consented to share. Included in the response when
 * customer_profile_sharing is enabled. Cash App releases this profile only once: it is present in
 * the first successful response when you get the payment setup after the customer authorizes the
 * payment, and every subsequent response omits it.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class CashAppCustomerProfile {

    /**
     * Cash App's identifier for the customer. This is not a Checkout.com customer identifier.
     * [Optional] readOnly
     */
    private String customerId;

    /**
     * The customer's $Cashtag.
     * [Optional] readOnly
     */
    private String cashtag;

    /**
     * Cash App's reference for the customer profile.
     * [Optional] readOnly
     */
    private String referenceId;

    /**
     * The customer's full name.
     * [Optional] readOnly
     */
    private String fullName;

    /**
     * The customer's given name.
     * [Optional] readOnly
     */
    private String givenName;

    /**
     * The customer's middle name.
     * [Optional] readOnly
     */
    private String middleName;

    /**
     * The customer's family name.
     * [Optional] readOnly
     */
    private String familyName;

    /**
     * The suffix of the customer's name.
     * [Optional] readOnly
     */
    private String suffix;

    /**
     * The customer's date of birth. Kept as the raw string returned by the API, because the
     * provider's value is not always a plain yyyy-MM-dd date.
     * [Optional] readOnly
     * Format: date
     */
    private String birthDate;

    /**
     * The customer's address.
     * [Optional] readOnly
     */
    private CashAppAddress address;

    /**
     * The customer's phone number.
     * [Optional] readOnly
     */
    private String phoneNumber;

    /**
     * The customer's email address.
     * [Optional] readOnly
     */
    private String emailAddress;

    /**
     * The date and time the customer's Cash App account was created. Kept as the raw string
     * returned by the API, so that a provider format the SDK does not expect cannot fail the
     * whole response and lose the profile, which is only returned once.
     * [Optional] readOnly
     * Format: date-time
     */
    private String customerSince;
}
