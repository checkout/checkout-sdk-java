package com.checkout.payments;

public class Processing {
    private boolean aft;

    public Processing() {}

    public Processing(boolean aft) {
        this();
        this.aft = aft;
    }

    public boolean isAft() {
        return aft;
    }

    public void setAft(boolean aft) {
        this.aft = aft;
    }
}
