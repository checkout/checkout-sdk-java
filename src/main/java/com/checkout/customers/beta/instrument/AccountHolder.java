package com.checkout.customers.beta.instrument;

import com.checkout.common.beta.Address;
import com.checkout.common.beta.Phone;
import com.google.gson.annotations.SerializedName;

public class AccountHolder {

    @SerializedName("first_name")
    private String firstName;

    @SerializedName("last_name")
    private String lastName;

    @SerializedName("billing_address")
    private Address billingAddress;

    private Phone phone;

}
