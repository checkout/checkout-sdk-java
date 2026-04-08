package com.checkout.agenticcommerce;

import com.checkout.PlatformType;
import com.checkout.SandboxTestFixture;
import com.checkout.agenticcommerce.request.AllowanceReason;
import com.checkout.agenticcommerce.request.CardFundingType;
import com.checkout.agenticcommerce.request.CardNumberType;
import com.checkout.agenticcommerce.request.DelegatePaymentAllowance;
import com.checkout.agenticcommerce.request.DelegatePaymentBillingAddress;
import com.checkout.agenticcommerce.request.DelegatePaymentMethod;
import com.checkout.agenticcommerce.request.DelegatePaymentRequest;
import com.checkout.agenticcommerce.request.DelegatePaymentHeaders;
import com.checkout.agenticcommerce.request.RiskSignal;
import com.checkout.agenticcommerce.response.DelegatePaymentResponse;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Integration tests for the Agentic Commerce Protocol (Beta).
 *
 * <p>These tests are {@link Disabled} because the endpoint requires specific
 * merchant configuration and live credentials to be enabled for Agentic Commerce.
 * Enable them individually once sandbox access is provisioned.</p>
 */
public class AgenticCommerceTestIT extends SandboxTestFixture {

    public AgenticCommerceTestIT() {
        super(PlatformType.DEFAULT);
    }

    @Disabled("Requires Agentic Commerce to be enabled on the sandbox account")
    @Test
    void shouldCreateDelegatePaymentToken() {
        final DelegatePaymentRequest request = buildFullRequest();

        final DelegatePaymentResponse response =
                blocking(() -> checkoutApi.agenticCommerceClient().delegatePayment(request, buildHeaders()));

        validateResponse(response);
    }

    @Disabled("Requires Agentic Commerce to be enabled on the sandbox account")
    @Test
    void shouldCreateDelegatePaymentTokenWithNetworkToken() {
        final DelegatePaymentMethod paymentMethod = DelegatePaymentMethod.builder()
                .type("card")
                .cardNumberType(CardNumberType.NETWORK_TOKEN)
                .number("4242424242424242")
                .expMonth("12")
                .expYear("2027")
                .cryptogram("gXc5UCLnM6ckD7pjM1TdPA==")
                .eciValue("07")
                .iin("424242")
                .metadata(Collections.singletonMap("issuing_bank", "test"))
                .build();

        final DelegatePaymentRequest request = DelegatePaymentRequest.builder()
                .paymentMethod(paymentMethod)
                .allowance(buildAllowance())
                .riskSignals(Collections.singletonList(buildRiskSignal()))
                .build();

        final DelegatePaymentResponse response =
                blocking(() -> checkoutApi.agenticCommerceClient().delegatePayment(request, buildHeaders()));

        validateResponse(response);
    }

    @Disabled("Requires Agentic Commerce to be enabled on the sandbox account")
    @Test
    void shouldCreateDelegatePaymentTokenSync() {
        final DelegatePaymentRequest request = buildFullRequest();

        final DelegatePaymentResponse response =
                checkoutApi.agenticCommerceClient().delegatePaymentSync(request, buildHeaders());

        validateResponse(response);
    }

    // --- helpers ---

    private static DelegatePaymentHeaders buildHeaders() {
        return DelegatePaymentHeaders.builder()
                .signature("sha256=test_signature")
                .timestamp(Instant.now().toString())
                .build();
    }

    private static DelegatePaymentRequest buildFullRequest() {
        final DelegatePaymentMethod paymentMethod = DelegatePaymentMethod.builder()
                .type("card")
                .cardNumberType(CardNumberType.FPAN)
                .number("4242424242424242")
                .expMonth("11")
                .expYear("2026")
                .name("Jane Doe")
                .cvc("100")
                .iin("424242")
                .displayCardFundingType(CardFundingType.CREDIT)
                .displayBrand("Visa")
                .displayLast4("4242")
                .checksPerformed(Arrays.asList("avs", "cvv"))
                .metadata(Collections.singletonMap("issuing_bank", "test"))
                .build();

        final DelegatePaymentBillingAddress billingAddress = DelegatePaymentBillingAddress.builder()
                .name("John Doe")
                .lineOne("123 Fake St.")
                .city("San Francisco")
                .postalCode("12345")
                .country("US")
                .build();

        return DelegatePaymentRequest.builder()
                .paymentMethod(paymentMethod)
                .allowance(buildAllowance())
                .billingAddress(billingAddress)
                .riskSignals(Collections.singletonList(buildRiskSignal()))
                .metadata(Collections.singletonMap("campaign", "q4"))
                .build();
    }

    private static DelegatePaymentAllowance buildAllowance() {
        return DelegatePaymentAllowance.builder()
                .reason(AllowanceReason.ONE_TIME)
                .maxAmount(10000L)
                .currency("USD")
                .merchantId("cli_vkuhvk4vjn2edkps7dfsq6emqm")
                .checkoutSessionId("1PQrsT")
                .expiresAt(Instant.now().plus(1, ChronoUnit.HOURS))
                .build();
    }

    private static RiskSignal buildRiskSignal() {
        return RiskSignal.builder()
                .type("card_testing")
                .score(10)
                .action("blocked")
                .build();
    }

    private static void validateResponse(final DelegatePaymentResponse response) {
        assertNotNull(response);
        assertNotNull(response.getId());
        assertNotNull(response.getCreated());
        assertNotNull(response.getMetadata());
    }
}
