package com.checkout.forward.requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public final class NetworkToken {

    /**
     * Specifies whether to use a network token (Optional)
     */
    private Boolean enabled;

    /**
     * Specifies whether to generate a cryptogram. For example, for customer-initiated transactions (CITs). If you
     * set network_token.enabled to true, you must provide this field (Optional)
     */
    private Boolean requestCryptogram;

}
