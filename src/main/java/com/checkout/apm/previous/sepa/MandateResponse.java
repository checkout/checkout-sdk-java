package com.checkout.apm.previous.sepa;

import com.checkout.common.CountryCode;
import com.checkout.common.Resource;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public final class MandateResponse extends Resource {

    private String mandateReference;

    private String customerId;

    private String firstName;

    private String lastName;

    private String addressLine1;

    private String city;

    private String zip;

    private CountryCode country;

    private String maskedAccountIban;

    private String accountCurrencyCode;

    private CountryCode accountCountryCode;

    private String mandateState;

    private String billingDescriptor;

    private String mandateType;

}
