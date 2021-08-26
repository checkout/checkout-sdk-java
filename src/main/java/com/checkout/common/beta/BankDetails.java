package com.checkout.common.beta;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public final class BankDetails {

    private String name;

    private String branch;

    private Address address;

}
