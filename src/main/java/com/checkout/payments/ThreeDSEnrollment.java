package com.checkout.payments;

public class ThreeDSEnrollment {
    private boolean downgraded;
    private String enrolled;
    private String signatureValid;
    private String authenticationResponse;
    private String cryptogram;
    private String xid;

    public boolean isDowngraded() {
        return downgraded;
    }

    public void setDowngraded(boolean downgraded) {
        this.downgraded = downgraded;
    }

    public String getEnrolled() {
        return enrolled;
    }

    public void setEnrolled(String enrolled) {
        this.enrolled = enrolled;
    }

    public String getSignatureValid() {
        return signatureValid;
    }

    public void setSignatureValid(String signatureValid) {
        this.signatureValid = signatureValid;
    }

    public String getAuthenticationResponse() {
        return authenticationResponse;
    }

    public void setAuthenticationResponse(String authenticationResponse) {
        this.authenticationResponse = authenticationResponse;
    }

    public String getCryptogram() {
        return cryptogram;
    }

    public void setCryptogram(String cryptogram) {
        this.cryptogram = cryptogram;
    }

    public String getXid() {
        return xid;
    }

    public void setXid(String xid) {
        this.xid = xid;
    }
}