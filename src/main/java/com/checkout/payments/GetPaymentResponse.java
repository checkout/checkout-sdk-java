package com.checkout.payments;

import com.checkout.common.Link;
import com.checkout.common.Resource;
import com.google.gson.annotations.SerializedName;

import java.time.Instant;
import java.util.List;
import java.util.Map;

public class GetPaymentResponse extends Resource {
    private String id;
    private Instant requestedOn;
    private ResponseSource source;
    private Integer amount;
    private String currency;
    private String paymentType;
    private String reference;
    private String description;
    private boolean approved;
    private String status;
    @SerializedName("3ds")
    private ThreeDSEnrollment threeDS;
    private RiskAssessment risk;
    private CustomerResponse customer;
    private BillingDescriptor billingDescriptor;
    private ShippingDetails shipping;
    private String paymentIp;
    private PaymentRecipient recipient;
    private Map<String, Object> metadata;
    private String eci;
    private List<PaymentActionSummary> actions;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Instant getRequestedOn() {
        return requestedOn;
    }

    public void setRequestedOn(Instant requestedOn) {
        this.requestedOn = requestedOn;
    }

    public ResponseSource getSource() {
        return source;
    }

    public void setSource(ResponseSource source) {
        this.source = source;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isApproved() {
        return approved;
    }

    public void setApproved(boolean approved) {
        this.approved = approved;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ThreeDSEnrollment getThreeDS() {
        return threeDS;
    }

    public void setThreeDS(ThreeDSEnrollment threeDS) {
        this.threeDS = threeDS;
    }

    public RiskAssessment getRisk() {
        return risk;
    }

    public void setRisk(RiskAssessment risk) {
        this.risk = risk;
    }

    public CustomerResponse getCustomer() {
        return customer;
    }

    public void setCustomer(CustomerResponse customer) {
        this.customer = customer;
    }

    public BillingDescriptor getBillingDescriptor() {
        return billingDescriptor;
    }

    public void setBillingDescriptor(BillingDescriptor billingDescriptor) {
        this.billingDescriptor = billingDescriptor;
    }

    public ShippingDetails getShipping() {
        return shipping;
    }

    public void setShipping(ShippingDetails shipping) {
        this.shipping = shipping;
    }

    public String getPaymentIp() {
        return paymentIp;
    }

    public void setPaymentIp(String paymentIp) {
        this.paymentIp = paymentIp;
    }

    public PaymentRecipient getRecipient() {
        return recipient;
    }

    public void setRecipient(PaymentRecipient recipient) {
        this.recipient = recipient;
    }

    public Map<String, Object> getMetadata() {
        return metadata;
    }

    public void setMetadata(Map<String, Object> metadata) {
        this.metadata = metadata;
    }

    public String getEci() {
        return eci;
    }

    public void setEci(String eci) {
        this.eci = eci;
    }

    public List<PaymentActionSummary> getActions() {
        return actions;
    }

    public void setActions(List<PaymentActionSummary> actions) {
        this.actions = actions;
    }

    public boolean requiresRedirect() {
        return hasLink("redirect");
    }

    public Link getRedirectLink() {
        return getLink("redirect");
    }

    public PaymentActionSummary getAuthorizationAction() {
        return actions.stream()
                .filter(it -> ActionType.AUTHORIZATION.equalsIgnoreCase(it.getType()))
                .findFirst()
                .get();
    }
}