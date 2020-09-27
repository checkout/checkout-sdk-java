package com.checkout.instruments;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateInstrumentRequest {
    private int expiryMonth;
    private int expiryYear;
    private String name;
    private AccountHolder accountHolder;
    private CustomerRequest customer;
}
