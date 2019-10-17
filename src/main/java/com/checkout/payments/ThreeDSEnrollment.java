package com.checkout.payments;

import lombok.Data;

@Data
public class ThreeDSEnrollment {
    private boolean downgraded;
    private String enrolled;
    private String signatureValid;
    private String authenticationResponse;
    private String cryptogram;
    private String xid;
    private String version;
}