package com.checkout;

public enum FourOAuthScope {

    VAULT("vault"),
    VAULT_INSTRUMENTS("vault:instruments"),
    VAULT_TOKENIZATION("vault:tokenization"),
    GATEWAY("gateway"),
    GATEWAY_PAYMENT("gateway:payment"),
    GATEWAY_PAYMENT_DETAILS("gateway:payment-details"),
    GATEWAY_PAYMENT_AUTHORIZATION("gateway:payment-authorizations"),
    GATEWAY_PAYMENT_VOIDS("gateway:payment-voids"),
    GATEWAY_PAYMENT_CAPTURES("gateway:payment-captures"),
    GATEWAY_PAYMENT_REFUNDS("gateway:payment-refunds"),
    FX("fx"),
    PAYOUTS_BANK_DETAILS("payouts:bank-details"),
    SESSIONS("sessions"),
    SESSIONS_APP("sessions:app"),
    SESSIONS_BROWSER("sessions:browser"),
    DISPUTES("disputes"),
    DISPUTES_VIEW("disputes:view"),
    DISPUTES_PROVIDE_EVIDENCE("disputes:provide-evidence"),
    DISPUTES_ACCEPT("disputes:accept"),
    MARKETPLACE("marketplace"),
    FLOW("flow"),
    FLOW_WORKFLOWS("flow:workflows"),
    FLOW_EVENTS("flow:events"),
    FILES("files"),
    FILES_RETRIEVE("files:retrieve"),
    FILES_UPLOAD("files:upload"),
    ISSUING_CLIENT("issuing:client"),
    ISSUING_PARTNER("issuing:partner"),
    RISK("risk"),
    RISK_ASSESSMENT("risk:assessment"),
    RISK_SETTINGS("risk:settings");

    private final String scope;

    FourOAuthScope(final String gateway) {
        this.scope = gateway;
    }

    public String getScope() {
        return scope;
    }

}
