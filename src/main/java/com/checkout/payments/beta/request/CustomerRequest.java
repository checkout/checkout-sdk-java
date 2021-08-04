package com.checkout.payments.beta.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class CustomerRequest {

    private String id;

    private String email;

    private String name;

}