package com.checkout.forward.requests.signatures;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public final class DlocalParameters {

    /**
     * The secret key used to generate the request signature. This is part of the dLocal API credentials.
     */
    private String SecretKey;

}
