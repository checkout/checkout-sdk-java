package com.checkout.payments;

import com.checkout.common.Link;
import com.checkout.common.Resource;
import com.google.gson.annotations.SerializedName;

import java.time.Instant;

public class PaymentProcessed extends Resource {
    private String id;
    private String actionId;
    private int amount;
    private String currency;
    private boolean approved;
    private String status;
    private String authCode;
    private String responseCode;
    private String responseSummary;
    @SerializedName("3ds")
    private ThreeDSEnrollment threeDS;
    private RiskAssessment risk;
    private ResponseSource source;
    private CardDestinationResponse destination;
    private CustomerResponse customer;
    private Instant processedOn;
    private String reference;
    private String eci;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getActionId() {
        return actionId;
    }

    public void setActionId(String actionId) {
        this.actionId = actionId;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
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

    public String getAuthCode() {
        return authCode;
    }

    public void setAuthCode(String authCode) {
        this.authCode = authCode;
    }

    public String getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }

    public String getResponseSummary() {
        return responseSummary;
    }

    public void setResponseSummary(String responseSummary) {
        this.responseSummary = responseSummary;
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

    public ResponseSource getSource() {
        return source;
    }

    public void setSource(ResponseSource source) {
        this.source = source;
    }

    public CustomerResponse getCustomer() {
        return customer;
    }

    public void setCustomer(CustomerResponse customer) {
        this.customer = customer;
    }

    public Instant getProcessedOn() {
        return processedOn;
    }

    public void setProcessedOn(Instant processedOn) {
        this.processedOn = processedOn;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getEci() {
        return eci;
    }

    public void setEci(String eci) {
        this.eci = eci;
    }

    public Link getActionsLink() {
        return getLink("actions");
    }

    public boolean canCapture() {
        return hasLink("capture");
    }

    public Link getCaptureLink() {
        return getLink("capture");
    }

    public boolean canVoid() {
        return hasLink("void");
    }

    public Link getVoidLink() {
        return getLink("void");
    }

    public CardDestinationResponse getDestination() {
        return destination;
    }

    public void setDestination(CardDestinationResponse destination) {
        this.destination = destination;
    }
}