package com.checkout.handlepaymentsandpayouts.googlepay.responses;

import com.checkout.HttpMetadata;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.List;

/**
 * Response containing the list of registered domains for a Google Pay enrolled entity.
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public final class GooglePayDomainListResponse extends HttpMetadata {

    /**
     * The list of domains registered for the entity.
     */
    private List<String> domains;

}
