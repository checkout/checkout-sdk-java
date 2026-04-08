package com.checkout.handlepaymentsandpayouts.googlepay.requests;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class GooglePayRegisterDomainRequest {

    /**
     * The web domain to register for an actively enrolled entity.
     * <p>
     * [Required]
     * </p>
     * Format: hostname
     */
    @SerializedName("web_domain")
    private String webDomain;

}
