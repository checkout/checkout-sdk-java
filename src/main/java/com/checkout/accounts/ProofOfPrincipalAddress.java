package com.checkout.accounts;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class ProofOfPrincipalAddress {

    private ProofOfPrincipalAddressType type;

    private String front;

}
