package com.checkout.payments.four;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class BillingDescriptor {

    private String name;

    private String city;

    private String reference;

}
