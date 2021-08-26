package com.checkout.common.beta;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CustomerRequest {

    private String id;

    private String email;

    private String name;

}