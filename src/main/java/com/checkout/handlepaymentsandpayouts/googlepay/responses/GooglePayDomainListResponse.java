package com.checkout.handlepaymentsandpayouts.googlepay.responses;

import com.checkout.HttpMetadata;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public final class GooglePayDomainListResponse extends HttpMetadata {

    /**
     * The list of domains registered for the entity.
     * <p>
     * [Required]
     * </p>
     * Items format: hostname
     */
    private List<String> domains;

}
