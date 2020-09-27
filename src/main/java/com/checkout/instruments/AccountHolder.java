package com.checkout.instruments;

import com.checkout.common.Address;
import com.checkout.common.Phone;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccountHolder {
    private Address billingAddress;
    private Phone phone;
}
