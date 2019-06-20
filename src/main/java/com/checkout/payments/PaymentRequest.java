package com.checkout.payments;

import com.checkout.common.CheckoutUtils;
import com.google.gson.annotations.SerializedName;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

public class PaymentRequest<T extends RequestSource> {
    private final T source;
    private final Integer amount;
    private final String currency;
    private String paymentType;
    private String reference;
    private String description;
    private Boolean capture;
    private CustomerRequest customer;
    private Instant captureOn;
    private BillingDescriptor billingDescriptor;
    private ShippingDetails shipping;
    @SerializedName("3ds")
    private ThreeDSRequest threeDS;
    private String previousPaymentId;
    private RiskRequest risk;
    private String successUrl;
    private String failureUrl;
    private String paymentIp;
    private PaymentRecipient recipient;
    private Map<String, Object> metadata = new HashMap<>();

    public PaymentRequest(T source, String currency, Integer amount) {
        if (source == null) {
            throw new IllegalArgumentException("The payment source is required.");
        }
        if (CheckoutUtils.isNullOrWhitespace(currency)) {
            throw new IllegalArgumentException("The currency is required.");
        }
        if (amount != null && amount < 0) {
            throw new IllegalArgumentException("The amount cannot be negative");
        }
        this.source = source;
        this.amount = amount;
        this.currency = currency;
    }

    public T getSource() {
        return source;
    }

    public Integer getAmount() {
        return amount;
    }

    public String getCurrency() {
        return currency;
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

    public Boolean getCapture() {
        return capture;
    }

    public void setCapture(Boolean capture) {
        this.capture = capture;
    }

    public CustomerRequest getCustomer() {
        return customer;
    }

    public void setCustomer(CustomerRequest customer) {
        this.customer = customer;
    }

    public Instant getCaptureOn() {
        return captureOn;
    }

    public void setCaptureOn(Instant captureOn) {
        this.captureOn = captureOn;
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

    public ThreeDSRequest getThreeDS() {
        return threeDS;
    }

    public void setThreeDS(ThreeDSRequest threeDS) {
        this.threeDS = threeDS;
    }

    public String getPreviousPaymentId() {
        return previousPaymentId;
    }

    public void setPreviousPaymentId(String previousPaymentId) {
        this.previousPaymentId = previousPaymentId;
    }

    public RiskRequest getRisk() {
        return risk;
    }

    public void setRisk(RiskRequest risk) {
        this.risk = risk;
    }

    public String getSuccessUrl() {
        return successUrl;
    }

    public void setSuccessUrl(String successUrl) {
        this.successUrl = successUrl;
    }

    public String getFailureUrl() {
        return failureUrl;
    }

    public void setFailureUrl(String failureUrl) {
        this.failureUrl = failureUrl;
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
}