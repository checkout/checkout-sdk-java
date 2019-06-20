package com.checkout.sources;

import com.checkout.common.Address;
import com.checkout.common.CheckoutUtils;
import com.checkout.common.Phone;

public class SourceRequest {
    private String type;
    private Address billingAddress;
    private String reference;
    private Phone phone;
    private CustomerRequest customer;
    private SourceData sourceData;

    public SourceRequest(String type, Address billingAddress) {
        if (CheckoutUtils.isNullOrWhitespace(type)) {
            throw new IllegalArgumentException("The payment source type is required.");
        }
        if (billingAddress == null) {
            throw new IllegalArgumentException("The payment source owner's billing address is required.");
        }

        this.type = type;
        this.billingAddress = billingAddress;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Address getBillingAddress() {
        return billingAddress;
    }

    public void setBillingAddress(Address billingAddress) {
        this.billingAddress = billingAddress;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public Phone getPhone() {
        return phone;
    }

    public void setPhone(Phone phone) {
        this.phone = phone;
    }

    public CustomerRequest getCustomer() {
        return customer;
    }

    public void setCustomer(CustomerRequest customer) {
        this.customer = customer;
    }

    public SourceData getSourceData() {
        return sourceData;
    }

    public void setSourceData(SourceData sourceData) {
        this.sourceData = sourceData;
    }
}