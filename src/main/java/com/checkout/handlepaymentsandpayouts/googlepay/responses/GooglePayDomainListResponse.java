package com.checkout.handlepaymentsandpayouts.googlepay.responses;

import lombok.Data;

import java.util.List;

/**
 * Response containing the list of registered domains for a Google Pay enrolled entity.
 */
@Data
public final class GooglePayDomainListResponse {

    /**
     * The list of domains registered for the entity.
     */
    private List<String> domains;

}
