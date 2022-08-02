package com.checkout.payments.request;

import com.checkout.common.CustomerRequest;
import com.checkout.common.Phone;
import com.google.gson.annotations.SerializedName;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class PaymentCustomerRequest extends CustomerRequest {

    @SerializedName("tax_number")
    private String taxNumber;

    @Builder
    private PaymentCustomerRequest(final String id,
                                   final String email,
                                   final String name,
                                   final Phone phone,
                                   final String taxNumber) {
        super(id, email, name, phone);
        this.taxNumber = taxNumber;
    }

}
