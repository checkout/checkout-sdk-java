package com.checkout.handlepaymentsandpayouts.applepay.requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EnrollDomainRequest {

    /**
     * The domain to enroll
     */
    private String domain;

}