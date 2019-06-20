package com.checkout.payments;

import com.checkout.common.Link;
import com.checkout.common.Resource;
import com.google.gson.annotations.SerializedName;

public class PaymentPending extends Resource {
    private String id;
    private String status;
    private String reference;
    private CustomerResponse customer;
    @SerializedName("3ds")
    private ThreeDSEnrollment threeDS;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public CustomerResponse getCustomer() {
        return customer;
    }

    public void setCustomer(CustomerResponse customer) {
        this.customer = customer;
    }

    public ThreeDSEnrollment getThreeDS() {
        return threeDS;
    }

    public void setThreeDS(ThreeDSEnrollment threeDS) {
        this.threeDS = threeDS;
    }

    public boolean requiresRedirect() {
        return hasLink("redirect");
    }

    public Link getRedirectLink() {
        return getLink("redirect");
    }
}