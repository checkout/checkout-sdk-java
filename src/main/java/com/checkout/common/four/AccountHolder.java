package com.checkout.common.four;

import com.checkout.common.Address;
import com.checkout.common.Phone;
import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AccountHolder {

    @SerializedName("first_name")
    private String firstName;

    @SerializedName("last_name")
    private String lastName;

    @SerializedName("billing_address")
    private Address billingAddress;

    private Phone phone;

}
