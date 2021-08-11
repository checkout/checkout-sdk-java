package com.checkout.common.beta;

import lombok.Data;

@Data
public final class BankDetails {

    private String name;

    private String branch;

    private Address address;

}
