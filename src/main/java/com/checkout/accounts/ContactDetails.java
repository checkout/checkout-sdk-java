package com.checkout.accounts;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public final class ContactDetails {

    private AccountPhone phone;

    @SerializedName("email_addresses")
    private EntityEmailAddresses emailAddresses;

}
