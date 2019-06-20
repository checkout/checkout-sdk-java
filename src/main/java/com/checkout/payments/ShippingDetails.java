package com.checkout.payments;

import com.checkout.common.Address;
import com.checkout.common.Phone;

public class ShippingDetails {
    private Address address;
    private Phone phone;

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Phone getPhone() {
        return phone;
    }

    public void setPhone(Phone phone) {
        this.phone = phone;
    }
}