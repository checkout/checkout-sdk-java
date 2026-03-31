package com.checkout.sessions;

import lombok.Data;

@Data
public final class TrustedBeneficiary {

    private TrustedBeneficiaryStatusType status;

    private String source;
}
