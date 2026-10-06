package com.checkout.handlepaymentsandpayouts.setups.entities.paymentMethods.cashapp;

import com.google.gson.annotations.SerializedName;
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
    @SerializedName("customer_id")
    private String customerId;

    /**
     * The customer's Cashtag.
     * [Optional] readOnly
     */
    private String cashtag;

    /**
     * Cash App's reference for the customer profile.
     * [Optional] readOnly
     */
    @SerializedName("reference_id")
    private String referenceId;

    /**
     * The customer's full name.
     * [Optional] readOnly
     */
    @SerializedName("full_name")
    private String fullName;

    /**
     * The customer's given name.
     * [Optional] readOnly
     */
    @SerializedName("given_name")
    private String givenName;

    /**
     * The customer's middle name.
     * [Optional] readOnly
     */
    @SerializedName("middle_name")
    private String middleName;

    /**
     * The customer's family name.
     * [Optional] readOnly
     */
    @SerializedName("family_name")
    private String familyName;

    /**
     * The suffix of the customer's name.
     * [Optional] readOnly
     */
    private String suffix;

    /**
     * The customer's date of birth, kept as the raw string returned by the API.
     * [Optional] readOnly, format date
     */
    @SerializedName("birth_date")
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
    @SerializedName("phone_number")
    private String phoneNumber;

    /**
     * The customer's email address.
     * [Optional] readOnly
     */
    @SerializedName("email_address")
    private String emailAddress;

    /**
     * The date and time the customer's Cash App account was created, kept as the raw string
     * returned by the API.
     * [Optional] readOnly, format date-time
     */
    @SerializedName("customer_since")
    private String customerSince;
}
