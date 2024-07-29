package com.checkout.sessions;

import lombok.Data;

@Data
public class TrustedBeneficiary {

    private TrustedBeneficiaryStatusType status;

    private String source;
}
