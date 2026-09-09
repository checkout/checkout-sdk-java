package com.checkout;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class OAuthScopeTest {

    /**
     * The enum constant's String is the only place a scope's wire value is written down, and
     * OAuthSdkCredentials builds the token request from getScope() rather than from the constant
     * name. A typo is therefore invisible at compile time and surfaces only at the token endpoint,
     * which rejects the whole request when one requested scope is undefined -- so a caller would
     * lose every scope it asked for alongside the bad one.
     *
     * Values come from components.securitySchemes.OAuth.flows.clientCredentials.scopes in
     * shared/swagger-latest.json.
     */
    @Test
    void shouldExposeDocumentedBalancesScopeValues() {
        assertEquals("balances", OAuthScope.BALANCES.getScope());
        assertEquals("balances:view", OAuthScope.BALANCES_VIEW.getScope());
        assertEquals("balances:top-up-instructions", OAuthScope.BALANCES_TOP_UP_INSTRUCTIONS.getScope());
    }

}
