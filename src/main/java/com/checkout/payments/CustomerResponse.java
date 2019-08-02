package com.checkout.payments;

import lombok.Data;

@Data
public class CustomerResponse {
    private String id;
    private String email;
    private String name;
}