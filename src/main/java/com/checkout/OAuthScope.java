package com.checkout;

public enum OAuthScope {

    ACCOUNTS("accounts"),
    BALANCES("balances"),
    BALANCES_VIEW("balances:view"),
    CARD_MANAGEMENT("card-management"),
    DISPUTES("disputes"),
    DISPUTES_ACCEPT("disputes:accept"),
    DISPUTES_PROVIDE_EVIDENCE("disputes:provide-evidence"),
    DISPUTES_VIEW("disputes:view"),
    FILES("files"),
    FILES_DOWNLOAD("files:download"),
    FILES_RETRIEVE("files:retrieve"),
    FILES_UPLOAD("files:upload"),
    FINANCIAL_ACTIONS("financial-actions"),
    FINANCIAL_ACTIONS_VIEW("financial-actions:view"),
    FLOW("flow"),
    FLOW_EVENTS("flow:events"),
    FLOW_WORKFLOWS("flow:workflows"),
    FX("fx"),
    GATEWAY("gateway"),
    GATEWAY_PAYMENT("gateway:payment"),
    GATEWAY_PAYMENT_AUTHORIZATION("gateway:payment-authorizations"),
    GATEWAY_PAYMENT_CAPTURES("gateway:payment-captures"),
    GATEWAY_PAYMENT_CANCELLATIONS("gateway:payment-cancellations"),
    GATEWAY_PAYMENT_DETAILS("gateway:payment-details"),
    GATEWAY_PAYMENT_REFUNDS("gateway:payment-refunds"),
    GATEWAY_PAYMENT_VOIDS("gateway:payment-voids"),
    ISSUING_CARD_MANAGEMENT_READ("issuing:card-management-read"),
    ISSUING_CARD_MANAGEMENT_WRITE("issuing:card-management-write"),
    ISSUING_CARD_MGMT("issuing:card-mgmt"),
    ISSUING_CLIENT("issuing:client"),
    ISSUING_CONTROLS_READ("issuing:controls-read"),
    ISSUING_CONTROLS_WRITE("issuing:controls-write"),
    ISSUING_TRANSACTIONS_READ("issuing:transactions-read"),
    ISSUING_TRANSACTIONS_WRITE("issuing:transactions-write"),
    MARKETPLACE("marketplace"),
    MIDDLEWARE("middleware"),
    MIDDLEWARE_MERCHANTS_PUBLIC("middleware:merchants-public"),
    MIDDLEWARE_MERCHANTS_SECRET("middleware:merchants-secret"),
    PAYMENT_CONTEXTS("gateway:payment-contexts"),
    PAYMENT_SESSIONS("payment-sessions"),
    PAYOUTS_BANK_DETAILS("payouts:bank-details"),
    REPORTS("reports"),
    REPORTS_VIEW("reports:view"),
    SESSIONS_APP("sessions:app"),
    SESSIONS_BROWSER("sessions:browser"),
    TRANSACTIONS("transactions"),
    TRANSFERS("transfers"),
    TRANSFERS_CREATE("transfers:create"),
    TRANSFERS_VIEW("transfers:view"),
    VAULT("vault"),
    VAULT_CARD_METADATA("vault:card-metadata"),
    VAULT_INSTRUMENTS("vault:instruments"),
    VAULT_TOKENIZATION("vault:tokenization");

    private final String scope;

    OAuthScope(final String gateway) {
        this.scope = gateway;
    }

    public String getScope() {
        return scope;
    }

}
