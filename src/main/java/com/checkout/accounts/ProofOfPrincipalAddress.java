package com.checkout.accounts;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public final class ProofOfPrincipalAddress {

    private ProofOfPrincipalAddressType type;

    private String front;

}
