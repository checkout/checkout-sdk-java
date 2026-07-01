package com.checkout.issuing.disputes;

import com.checkout.GsonSerializer;
import com.checkout.issuing.disputes.entities.DisputeEvidence;
import com.checkout.issuing.disputes.entities.IssuingDisputeFraudDetails;
import com.checkout.issuing.disputes.entities.IssuingDisputeFraudType;
import com.checkout.issuing.disputes.requests.AmendDisputeRequest;
import com.checkout.issuing.disputes.requests.CreateDisputeRequest;
import com.checkout.issuing.disputes.requests.EscalateDisputeRequest;
import com.checkout.issuing.disputes.responses.DisputeResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * GSON serialization / deserialization tests for the 2026-06-29 Issuing dispute changes:
 * amend request, fraud details, and action details.
 */
class IssuingDisputeSerializationTest {

    private final GsonSerializer serializer = new GsonSerializer();

    @Test
    void shouldSerializeAmendDisputeRequestWithAllProperties() {
        final AmendDisputeRequest request = AmendDisputeRequest.builder()
                .reason("4807")
                .amount(1500L)
                .evidence(Collections.singletonList(DisputeEvidence.builder()
                        .name("receipt.pdf").content("SGVsbG8=").description("Receipt").build()))
                .fraudDetails(IssuingDisputeFraudDetails.builder()
                        .fraudType(IssuingDisputeFraudType.CARD_NOT_PRESENT_FRAUD)
                        .description("No online purchases on this date.")
                        .build())
                .reasonChangeJustification("New evidence confirms an unauthorized transaction.")
                .actionResponse("Updated the reason code as requested.")
                .build();

        final String json = serializer.toJson(request);

        assertTrue(json.contains("\"reason\""));
        assertTrue(json.contains("\"amount\""));
        assertTrue(json.contains("\"fraud_details\""));
        assertTrue(json.contains("\"fraud_type\""));
        assertTrue(json.contains("card_not_present_fraud"));
        assertTrue(json.contains("\"reason_change_justification\""));
        assertTrue(json.contains("\"action_response\""));
    }

    @Test
    void shouldRoundTripAmendDisputeRequest() {
        final AmendDisputeRequest original = AmendDisputeRequest.builder()
                .reason("4837")
                .amount(2000L)
                .fraudDetails(IssuingDisputeFraudDetails.builder()
                        .fraudType(IssuingDisputeFraudType.ACCOUNT_TAKEOVER).build())
                .reasonChangeJustification("round-trip")
                .actionResponse("round-trip response")
                .build();

        final AmendDisputeRequest deserialized =
                serializer.fromJson(serializer.toJson(original), AmendDisputeRequest.class);

        assertEquals(original.getReason(), deserialized.getReason());
        assertEquals(original.getAmount(), deserialized.getAmount());
        assertEquals(original.getFraudDetails().getFraudType(), deserialized.getFraudDetails().getFraudType());
        assertEquals(original.getReasonChangeJustification(), deserialized.getReasonChangeJustification());
        assertEquals(original.getActionResponse(), deserialized.getActionResponse());
    }

    @Test
    void shouldDeserializeAmendDisputeRequestFromSwaggerExample() {
        final String json = "{"
                + "\"reason\":\"4807\","
                + "\"amount\":1500,"
                + "\"evidence\":[{\"name\":\"receipt.pdf\",\"content\":\"SGVsbG8=\",\"description\":\"Receipt\"}],"
                + "\"fraud_details\":{\"fraud_type\":\"card_not_present_fraud\",\"description\":\"context\"},"
                + "\"reason_change_justification\":\"justification\","
                + "\"action_response\":\"Updated as requested\""
                + "}";

        final AmendDisputeRequest request = serializer.fromJson(json, AmendDisputeRequest.class);

        assertNotNull(request);
        assertEquals("4807", request.getReason());
        assertEquals(1500L, request.getAmount());
        assertEquals(1, request.getEvidence().size());
        assertEquals(IssuingDisputeFraudType.CARD_NOT_PRESENT_FRAUD, request.getFraudDetails().getFraudType());
        assertEquals("context", request.getFraudDetails().getDescription());
        assertEquals("justification", request.getReasonChangeJustification());
        assertEquals("Updated as requested", request.getActionResponse());
    }

    @ParameterizedTest
    @CsvSource({
            "CARD_LOST,card_lost",
            "CARD_STOLEN,card_stolen",
            "CARD_NEVER_RECEIVED,card_never_received",
            "FRAUDULENT_ACCOUNT,fraudulent_account",
            "COUNTERFEIT_CARD,counterfeit_card",
            "ACCOUNT_TAKEOVER,account_takeover",
            "CARD_NOT_PRESENT_FRAUD,card_not_present_fraud",
            "MERCHANT_MISREPRESENTATION,merchant_misrepresentation",
            "CARDHOLDER_MANIPULATION,cardholder_manipulation",
            "INCORRECT_PROCESSING,incorrect_processing",
            "OTHER,other"
    })
    void shouldSerializeEachFraudTypeValue(final IssuingDisputeFraudType fraudType, final String expected) {
        final IssuingDisputeFraudDetails details = IssuingDisputeFraudDetails.builder()
                .fraudType(fraudType).build();

        final String json = serializer.toJson(details);

        assertTrue(json.contains("\"" + expected + "\""));
    }

    @Test
    void shouldSerializeCreateDisputeRequestWithFraudDetails() {
        final CreateDisputeRequest request = CreateDisputeRequest.builder()
                .transactionId("trx_test")
                .reason("4837")
                .fraudDetails(IssuingDisputeFraudDetails.builder()
                        .fraudType(IssuingDisputeFraudType.CARD_NOT_PRESENT_FRAUD).build())
                .build();

        final String json = serializer.toJson(request);

        assertTrue(json.contains("\"fraud_details\""));
        assertTrue(json.contains("card_not_present_fraud"));
    }

    @Test
    void shouldSerializeEscalateDisputeRequestWithFraudDetails() {
        final EscalateDisputeRequest request = EscalateDisputeRequest.builder()
                .justification("Escalating")
                .fraudDetails(IssuingDisputeFraudDetails.builder()
                        .fraudType(IssuingDisputeFraudType.ACCOUNT_TAKEOVER).build())
                .build();

        final String json = serializer.toJson(request);

        assertTrue(json.contains("\"fraud_details\""));
        assertTrue(json.contains("account_takeover"));
    }

    @Test
    void shouldDeserializeActionDetailsOnDisputeResponse() {
        final String json = "{"
                + "\"id\":\"idsp_test\","
                + "\"action_details\":{\"instructions\":\"Provide a reason code.\",\"last_action_response\":\"none\"}"
                + "}";

        final DisputeResponse response = serializer.fromJson(json, DisputeResponse.class);

        assertNotNull(response.getActionDetails());
        assertEquals("Provide a reason code.", response.getActionDetails().getInstructions());
        assertEquals("none", response.getActionDetails().getLastActionResponse());
    }
}
