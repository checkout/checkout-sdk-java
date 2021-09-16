package com.checkout.apm.sepa;

import com.checkout.common.CountryCode;
import com.checkout.common.Resource;
import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public final class MandateResponse extends Resource {

    @SerializedName("mandate_reference")
    private String mandateReference;

    @SerializedName("customer_id")
    private String customerId;

    @SerializedName("first_name")
    private String firstName;

    @SerializedName("last_name")
    private String lastName;

    @SerializedName("address_line1")
    private String addressLine1;

    private String city;

    private String zip;

    private CountryCode country;

    @SerializedName("masked_account_iban")
    private String maskedAccountIban;

    @SerializedName("account_currency_code")
    private String accountCurrencyCode;

    @SerializedName("account_country_code")
    private CountryCode accountCountryCode;

    @SerializedName("mandate_state")
    private String mandateState;

    @SerializedName("billing_descriptor")
    private String billingDescriptor;

    @SerializedName("mandate_type")
    private String mandateType;

}
