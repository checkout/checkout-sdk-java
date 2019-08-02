package com.checkout.payments;

public class Processing {
    private Boolean aft;
    private String mid;

    public Processing() {}

    public Processing(Boolean aft) {
        this();
        this.aft = aft;
    }

    public Processing(String mid) {
        this();
        this.mid = mid;
    }

    public Boolean isAft() {
        return aft;
    }

    public void setAft(Boolean aft) {
        this.aft = aft;
    }

    public String getMid() {
        return mid;
    }

    public void setMid(String mid) {
        this.mid = mid;
    }
}
