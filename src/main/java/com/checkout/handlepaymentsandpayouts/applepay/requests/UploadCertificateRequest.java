package com.checkout.handlepaymentsandpayouts.applepay.requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class UploadCertificateRequest {

    /**
     * The certificate content
     */
    private String content;

}