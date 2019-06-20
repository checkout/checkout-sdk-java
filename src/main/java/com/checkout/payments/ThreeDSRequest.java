package com.checkout.payments;

public class ThreeDSRequest {
    private boolean enabled;
    private Boolean attemptN3D;
    private String eci;
    private String cryptogram;
    private String xid;

    public static ThreeDSRequest from(boolean enabled) {
        ThreeDSRequest request = new ThreeDSRequest();
        request.enabled = enabled;
        return request;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public Boolean getAttemptN3D() {
        return attemptN3D;
    }

    public void setAttemptN3D(Boolean attemptN3D) {
        this.attemptN3D = attemptN3D;
    }

    public String getEci() {
        return eci;
    }

    public void setEci(String eci) {
        this.eci = eci;
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