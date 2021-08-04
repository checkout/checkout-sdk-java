package com.checkout.payments;

import com.checkout.common.Address;
import com.checkout.common.Phone;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
@Builder
@AllArgsConstructor
public final class BillingInformation {
    @NotEmpty
    private Address address;
    private Phone phone;
}