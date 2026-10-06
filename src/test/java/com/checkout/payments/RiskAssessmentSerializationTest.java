package com.checkout.payments;

import com.checkout.GsonSerializer;
import com.checkout.payments.response.GetPaymentResponse;
import com.checkout.payments.response.PaymentResponse;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class RiskAssessmentSerializationTest {

    private final GsonSerializer serializer = new GsonSerializer();

    // ------------------------------------------------------------------------
    // RiskAssessment
    // Returns the payment's risk assessment results. Covers both properties
    // (flagged, score) against the risk object of the PaymentResponse and
    // PaymentDetails schemas, where score is type number, min 0, max 100.
    // ------------------------------------------------------------------------

    @Test
    void shouldDeserializeFractionalScoreWithoutRounding() {
        final RiskAssessment risk = serializer.fromJson("{\"flagged\":true,\"score\":22.5}", RiskAssessment.class);

        assertTrue(risk.getFlagged());
        assertEquals(22.5, risk.getScore());
    }

    @Test
    void shouldDeserializeWholeNumberScore() {
        final RiskAssessment risk = serializer.fromJson("{\"flagged\":false,\"score\":22}", RiskAssessment.class);

        assertFalse(risk.getFlagged());
        assertEquals(22.0, risk.getScore());
    }

    @Test
    void shouldDeserializeBoundaryScores() {
        assertEquals(0.0, serializer.fromJson("{\"score\":0}", RiskAssessment.class).getScore());
        assertEquals(100.0, serializer.fromJson("{\"score\":100}", RiskAssessment.class).getScore());
    }

    @Test
    void shouldDeserializeWithNullOptionalProperties() {
        final RiskAssessment risk = serializer.fromJson("{}", RiskAssessment.class);

        assertNull(risk.getFlagged());
        assertNull(risk.getScore());
    }

    @Test
    void shouldSerializeFractionalScore() {
        final RiskAssessment risk = new RiskAssessment();
        risk.setFlagged(true);
        risk.setScore(22.5);

        final String json = serializer.toJson(risk);

        assertTrue(json.contains("\"flagged\":true"), json);
        assertTrue(json.contains("\"score\":22.5"), json);
    }

    @Test
    void shouldRoundTripAllProperties() {
        final RiskAssessment original = new RiskAssessment();
        original.setFlagged(true);
        original.setScore(22.5);

        final RiskAssessment deserialized = serializer.fromJson(serializer.toJson(original), RiskAssessment.class);

        assertEquals(original, deserialized);
        assertEquals(22.5, deserialized.getScore());
    }

    @Test
    void shouldDeserializeSwaggerExampleInGetPaymentResponse() {
        final String json = "{\"id\":\"pay_mbabizu24mvu3mela5njyhpit4\",\"risk\":{\"flagged\":true,\"score\":22}}";

        final GetPaymentResponse response = serializer.fromJson(json, GetPaymentResponse.class);

        assertNotNull(response.getRisk());
        assertTrue(response.getRisk().getFlagged());
        assertEquals(22.0, response.getRisk().getScore());
    }

    @Test
    void shouldDeserializeFractionalScoreInGetPaymentResponse() {
        final String json = "{\"id\":\"pay_123\",\"risk\":{\"flagged\":true,\"score\":22.5}}";

        final GetPaymentResponse response = serializer.fromJson(json, GetPaymentResponse.class);

        assertEquals(22.5, response.getRisk().getScore());
    }

    @Test
    void shouldDeserializeFractionalScoreInPaymentResponse() {
        final String json = "{\"id\":\"pay_123\",\"risk\":{\"flagged\":false,\"score\":22.7}}";

        final PaymentResponse response = serializer.fromJson(json, PaymentResponse.class);

        assertEquals(22.7, response.getRisk().getScore());
    }
}
