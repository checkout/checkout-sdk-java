package com.checkout.accounts;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public final class ProofOfLegality {

    private ProofOfLegalityType type;

    private String front;

}
