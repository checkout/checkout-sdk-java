package com.checkout.handlepaymentsandpayouts.flow.paymentsessionscomplete.requests;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class Customer {

    /**
     * The customer's email address. Required if source.type is tamara.
     */
    private String email;

    /**
     * The customer's name. Required if source.type is tamara.
     */
    private String name;

    /**
     * The unique identifier for an existing customer.
     */
    private String id;

    /**
     * The customer's phone number. Required if source.type is tamara.
     */
    private Phone phone;

    /**
     * The customer’s value-added tax (VAT) registration number.
     */
    @SerializedName("tax_number")
    private String taxNumber;

}
