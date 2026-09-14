package com.checkout;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
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

    /**
     * The scopes added when this enum was synced against the spec.
     *
     * <p>The last five are not declared in clientCredentials.scopes at all: they appear only in the
     * per-operation security requirements of GET/POST /compliance-requests/{payment_id}, the
     * /googlepay/enrollments operations and GET /tokens/{tokenId}/metadata. A client built from the
     * declared map alone would be missing them.</p>
     */
    @Test
    void shouldExposeDocumentedValuesForScopesAddedInSpecSync() {
        assertEquals("disputes:scheme-files", OAuthScope.DISPUTES_SCHEME_FILES.getScope());
        assertEquals("flow:reflow", OAuthScope.FLOW_REFLOW.getScope());
        assertEquals("issuing-disputes", OAuthScope.ISSUING_DISPUTES.getScope());
        assertEquals("issuing:disputes-read", OAuthScope.ISSUING_DISPUTES_READ.getScope());
        assertEquals("issuing:disputes-write", OAuthScope.ISSUING_DISPUTES_WRITE.getScope());
        assertEquals("vault:customers", OAuthScope.VAULT_CUSTOMERS.getScope());
        assertEquals("vault:real-time-account-updater", OAuthScope.VAULT_REAL_TIME_ACCOUNT_UPDATER.getScope());
        assertEquals("compliance-requests", OAuthScope.COMPLIANCE_REQUESTS.getScope());
        assertEquals("compliance-requests:read", OAuthScope.COMPLIANCE_REQUESTS_READ.getScope());
        assertEquals("compliance-requests:respond", OAuthScope.COMPLIANCE_REQUESTS_RESPOND.getScope());
        assertEquals("vault:gpayme-enrollment", OAuthScope.VAULT_GPAYME_ENROLLMENT.getScope());
        assertEquals("vault:tokens-metadata", OAuthScope.VAULT_TOKENS_METADATA.getScope());
    }

    /**
     * PAYMENT_CONTEXT and GATEWAY_PAYMENT_CONTEXTS are one letter apart as names but are unrelated
     * scopes, so this pins which is which. GATEWAY_PAYMENT_CONTEXTS was called PAYMENT_CONTEXTS
     * until the spec sync; had the new constant been added without that rename, a caller reaching
     * for one and landing on the other would be rejected at the token endpoint.
     *
     * <p>"Payment Context" is the only scope whose value contains a space and a capital letter,
     * which is almost certainly a spec authoring defect -- asserted verbatim because that is the
     * value GET /payment-contexts/{id} documents.</p>
     */
    @Test
    void shouldDistinguishTheTwoPaymentContextScopes() {
        assertEquals("Payment Context", OAuthScope.PAYMENT_CONTEXT.getScope());
        assertEquals("gateway:payment-contexts", OAuthScope.GATEWAY_PAYMENT_CONTEXTS.getScope());
    }

    /**
     * A blank wire value cannot be caught by the per-scope assertions above, which only read the
     * constants they name. It would be sent as an empty entry in the space-joined scope parameter,
     * which the token endpoint rejects for the whole request -- costing the caller every other
     * scope it asked for.
     */
    @Test
    void shouldExposeANonBlankWireValueForEveryConstant() {
        for (final OAuthScope scope : OAuthScope.values()) {
            assertFalse(scope.getScope() == null || scope.getScope().trim().isEmpty(),
                    scope.name() + " has a blank wire value");
        }
    }

    /**
     * Two constants sharing a wire value means one of them is a copy-paste error, and it cannot be
     * caught by the per-scope assertions above, which only ever read the constant they name. The
     * consequence is silent in both directions: a caller selecting the mistyped constant requests a
     * scope it did not ask for, and the scope that constant was supposed to carry is left with no
     * constant at all, so it becomes unreachable through this enum.
     */
    @Test
    void shouldNotReuseAWireValueAcrossConstants() {
        final List<String> duplicates = Arrays.stream(OAuthScope.values())
                .collect(Collectors.groupingBy(OAuthScope::getScope, Collectors.counting()))
                .entrySet().stream()
                .filter(entry -> entry.getValue() > 1)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());

        assertTrue(duplicates.isEmpty(), "wire values used by more than one constant: " + duplicates);
    }

    /**
     * Constants are maintained in alphabetical order so that the next spec sync produces a readable
     * diff instead of scattering additions through the file, and so the ordering matches the other
     * Checkout SDKs. Underscores are ignored when comparing, which is what makes PAYMENT_CONTEXT,
     * PAYMENT_SESSIONS and PAYMENTS_SEARCH sort in that order
     */
    @Test
    void shouldDeclareConstantsInAlphabeticalOrder() {
        final Function<OAuthScope, String> sortKey =
                scope -> scope.name().replace("_", "").toLowerCase();
        final List<String> declared = Arrays.stream(OAuthScope.values())
                .map(sortKey)
                .collect(Collectors.toList());

        assertEquals(declared.stream().sorted().collect(Collectors.toList()), declared);
    }

}
