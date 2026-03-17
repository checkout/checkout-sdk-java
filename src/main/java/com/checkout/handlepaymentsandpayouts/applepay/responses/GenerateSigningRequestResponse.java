package com.checkout.handlepaymentsandpayouts.applepay.responses;

import com.checkout.common.Resource;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class GenerateSigningRequestResponse extends Resource {

    /**
     * The signing request content
     */
    private String content;

}