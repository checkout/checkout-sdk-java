package com.checkout.handlepaymentsandpayouts.flow;

import com.checkout.GsonSerializer;
import com.checkout.common.Currency;
import com.checkout.handlepaymentsandpayouts.flow.entities.PaymentSessionStatus;
import com.checkout.handlepaymentsandpayouts.flow.requests.PaymentSessionCompleteRequest;
import com.checkout.handlepaymentsandpayouts.flow.requests.PaymentSessionCreateRequest;
import com.checkout.handlepaymentsandpayouts.flow.requests.PaymentSessionSubmitRequest;
import com.checkout.handlepaymentsandpayouts.flow.responses.ActionRequiredPaymentSubmissionResponse;
import com.checkout.handlepaymentsandpayouts.flow.responses.ApprovedPaymentSubmissionResponse;
import com.checkout.handlepaymentsandpayouts.flow.responses.DeclinedPaymentSubmissionResponse;
import com.checkout.handlepaymentsandpayouts.flow.responses.PaymentSubmissionResponse;
import com.checkout.payments.LocaleType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Regression tests for:
 *   1) locale must NOT be sent on submit requests (only create / complete)
 *   2) PaymentSubmissionResponse must deserialize API status values ("Approved", "Declined", "Action Required")
 */
class FlowLocaleAndStatusDeserializationTest {

    private final GsonSerializer serializer = new GsonSerializer();

    // ---------------------------------------------------------------
    //  Locale: submit request must never include locale
    // ---------------------------------------------------------------

    @Test
    void submitRequest_shouldNotContainLocale_whenBuiltWithDefaults() {
        PaymentSessionSubmitRequest request = PaymentSessionSubmitRequest.builder()
                .amount(5000L)
                .reference("order-001")
                .sessionData("sd_tok_abc123")
                .build();

        String json = serializer.toJson(request);

        assertFalse(json.contains("\"locale\""), "Submit request must not serialize a locale field");
    }

    @Test
    void submitRequest_shouldNotContainLocale_whenAllFieldsSet() {
        PaymentSessionSubmitRequest request = PaymentSessionSubmitRequest.builder()
                .amount(10000L)
                .currency(Currency.EUR)
                .reference("order-002")
                .sessionData("sd_tok_xyz789")
                .capture(true)
                .description("Full submit request")
                .build();

        String json = serializer.toJson(request);

        assertFalse(json.contains("\"locale\""), "Submit request must not serialize a locale field even when fully populated");
    }

    // ---------------------------------------------------------------
    //  Locale: create request must include locale (default en-GB)
    // ---------------------------------------------------------------

    @Test
    void createRequest_shouldContainDefaultLocale() {
        PaymentSessionCreateRequest request = PaymentSessionCreateRequest.builder()
                .amount(1000L)
                .currency(Currency.GBP)
                .reference("order-003")
                .build();

        String json = serializer.toJson(request);

        assertTrue(json.contains("\"locale\""), "Create request must include locale");
        assertTrue(json.contains("en-GB"), "Default locale must be en-GB");
    }

    @Test
    void createRequest_shouldSerializeExplicitLocale() {
        PaymentSessionCreateRequest request = PaymentSessionCreateRequest.builder()
                .amount(1000L)
                .currency(Currency.EUR)
                .reference("order-004")
                .locale(LocaleType.FR_FR)
                .build();

        String json = serializer.toJson(request);

        assertTrue(json.contains("fr-FR"), "Create request must serialize the explicit locale");
    }

    @Test
    void createRequest_shouldOmitLocale_whenSetToNull() {
        PaymentSessionCreateRequest request = PaymentSessionCreateRequest.builder()
                .amount(1000L)
                .currency(Currency.GBP)
                .reference("order-005")
                .locale(null)
                .build();

        String json = serializer.toJson(request);

        assertFalse(json.contains("\"locale\""), "Create request must omit locale when explicitly set to null");
    }

    // ---------------------------------------------------------------
    //  Locale: complete request must include locale (default en-GB)
    // ---------------------------------------------------------------

    @Test
    void completeRequest_shouldContainDefaultLocale() {
        PaymentSessionCompleteRequest request = PaymentSessionCompleteRequest.builder()
                .amount(2000L)
                .currency(Currency.USD)
                .reference("order-006")
                .sessionData("sd_tok_def456")
                .build();

        String json = serializer.toJson(request);

        assertTrue(json.contains("\"locale\""), "Complete request must include locale");
        assertTrue(json.contains("en-GB"), "Default locale must be en-GB");
    }

    // ---------------------------------------------------------------
    //  Status deserialization: all three API status values
    // ---------------------------------------------------------------

    @Test
    void shouldDeserializeApprovedStatus() {
        String json = "{"
                + "\"id\":\"pay_abc123\","
                + "\"status\":\"Approved\","
                + "\"type\":\"card\""
                + "}";

        PaymentSubmissionResponse response = serializer.fromJson(json, PaymentSubmissionResponse.class);

        assertNotNull(response);
        assertInstanceOf(ApprovedPaymentSubmissionResponse.class, response);
        assertEquals("pay_abc123", response.getId());
        assertEquals(PaymentSessionStatus.APPROVED, response.getStatus());
    }

    @Test
    void shouldDeserializeDeclinedStatus() {
        String json = "{"
                + "\"id\":\"pay_def456\","
                + "\"status\":\"Declined\","
                + "\"type\":\"card\","
                + "\"decline_reason\":\"Insufficient funds\""
                + "}";

        PaymentSubmissionResponse response = serializer.fromJson(json, PaymentSubmissionResponse.class);

        assertNotNull(response);
        assertInstanceOf(DeclinedPaymentSubmissionResponse.class, response);
        assertEquals("pay_def456", response.getId());
        assertEquals(PaymentSessionStatus.DECLINED, response.getStatus());
        assertEquals("Insufficient funds", ((DeclinedPaymentSubmissionResponse) response).getDeclineReason());
    }

    @Test
    void shouldDeserializeActionRequiredStatus() {
        String json = "{"
                + "\"id\":\"pay_ghi789\","
                + "\"status\":\"Action Required\","
                + "\"type\":\"card\","
                + "\"action\":{\"type\":\"redirect\",\"url\":\"https://3ds.example.com/challenge\"}"
                + "}";

        PaymentSubmissionResponse response = serializer.fromJson(json, PaymentSubmissionResponse.class);

        assertNotNull(response);
        assertInstanceOf(ActionRequiredPaymentSubmissionResponse.class, response);
        assertEquals("pay_ghi789", response.getId());
        assertEquals(PaymentSessionStatus.ACTION_REQUIRED, response.getStatus());

        ActionRequiredPaymentSubmissionResponse actionRequired = (ActionRequiredPaymentSubmissionResponse) response;
        assertNotNull(actionRequired.getAction());
        assertEquals("redirect", actionRequired.getAction().getType());
        assertEquals("https://3ds.example.com/challenge", actionRequired.getAction().getUrl());
    }

    @Test
    void shouldDeserializeActionRequiredWithPaymentSessionFields() {
        String json = "{"
                + "\"id\":\"pay_jkl012\","
                + "\"status\":\"Action Required\","
                + "\"type\":\"card\","
                + "\"action\":{\"type\":\"redirect\",\"url\":\"https://acs.example.com\"},"
                + "\"payment_session_id\":\"ps_sess_abc\","
                + "\"payment_session_secret\":\"pss_secret_xyz\""
                + "}";

        PaymentSubmissionResponse response = serializer.fromJson(json, PaymentSubmissionResponse.class);

        assertInstanceOf(ActionRequiredPaymentSubmissionResponse.class, response);
        ActionRequiredPaymentSubmissionResponse actionRequired = (ActionRequiredPaymentSubmissionResponse) response;
        assertEquals("ps_sess_abc", actionRequired.getPaymentSessionId());
        assertEquals("pss_secret_xyz", actionRequired.getPaymentSessionSecret());
    }

    @Test
    void shouldDeserializeApprovedWithPaymentSessionFields() {
        String json = "{"
                + "\"id\":\"pay_mno345\","
                + "\"status\":\"Approved\","
                + "\"type\":\"card\","
                + "\"payment_session_id\":\"ps_sess_def\","
                + "\"payment_session_secret\":\"pss_secret_abc\""
                + "}";

        PaymentSubmissionResponse response = serializer.fromJson(json, PaymentSubmissionResponse.class);

        assertInstanceOf(ApprovedPaymentSubmissionResponse.class, response);
        ApprovedPaymentSubmissionResponse approved = (ApprovedPaymentSubmissionResponse) response;
        assertEquals("ps_sess_def", approved.getPaymentSessionId());
        assertEquals("pss_secret_abc", approved.getPaymentSessionSecret());
    }

    // ---------------------------------------------------------------
    //  Parameterized: every PaymentSessionStatus value deserializes
    // ---------------------------------------------------------------

    static Stream<Arguments> allStatusValues() {
        return Stream.of(
                Arguments.of("Approved", ApprovedPaymentSubmissionResponse.class, PaymentSessionStatus.APPROVED),
                Arguments.of("Declined", DeclinedPaymentSubmissionResponse.class, PaymentSessionStatus.DECLINED),
                Arguments.of("Action Required", ActionRequiredPaymentSubmissionResponse.class, PaymentSessionStatus.ACTION_REQUIRED)
        );
    }

    @ParameterizedTest(name = "status \"{0}\" -> {1}")
    @MethodSource("allStatusValues")
    void shouldDeserializeAllStatusValues(String apiStatus, Class<? extends PaymentSubmissionResponse> expectedType, PaymentSessionStatus expectedEnum) {
        String json = "{"
                + "\"id\":\"pay_test\","
                + "\"status\":\"" + apiStatus + "\","
                + "\"type\":\"card\""
                + "}";

        PaymentSubmissionResponse response = serializer.fromJson(json, PaymentSubmissionResponse.class);

        assertNotNull(response);
        assertInstanceOf(expectedType, response);
        assertEquals(expectedEnum, response.getStatus());
    }
}
