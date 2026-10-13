package com.checkout.issuing.controls.responses.create;

import com.checkout.GsonSerializer;
import com.checkout.issuing.controls.requests.ControlType;
import com.checkout.issuing.controls.requests.MccLimit;
import com.checkout.issuing.controls.requests.MidLimit;
import com.checkout.issuing.controls.requests.VelocityLimit;
import com.checkout.issuing.controls.requests.VelocityWindow;
import com.checkout.issuing.controls.requests.VelocityWindowType;
import com.checkout.issuing.controls.requests.create.VelocityCardControlRequest;
import com.checkout.issuing.controls.responses.query.CardControlsQueryResponse;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Serialization tests for the card control responses returned by create, get, list and update control
 * operations (update-control-response and its velocity, mcc and mid subtypes, discriminated on control_type).
 */
class CardControlResponseSerializationTest {

    private final GsonSerializer serializer = new GsonSerializer();

    private static final String BASE_FIELDS =
            "\"id\": \"ctr_gp7vkmxayztufjz6top5bjcdra\","
                    + "\"target_id\": \"crd_fa6psq42dcdd6fdn5gifcq1491\","
                    + "\"is_editable\": true,"
                    + "\"created_date\": \"2021-09-09T19:41:39Z\","
                    + "\"last_modified_date\": \"2021-09-09T19:41:39Z\",";

    private static final String VELOCITY_JSON = "{" + BASE_FIELDS
            + "\"control_type\": \"velocity_limit\","
            + "\"description\": \"Maximum spend of 500€ per week for restaurants\","
            + "\"velocity_limit\": {"
            + "\"amount_remaining\": 45000,"
            + "\"amount_limit\": 50000,"
            + "\"velocity_window\": {\"type\": \"weekly\"},"
            + "\"mcc_list\": [\"4121\", \"4582\"],"
            + "\"mid_list\": [\"1234567890\"]"
            + "}}";

    private static final String MCC_JSON = "{" + BASE_FIELDS
            + "\"control_type\": \"mcc_limit\","
            + "\"description\": \"Allow the card to be used only in restaurants and supermarkets\","
            + "\"mcc_limit\": {\"type\": \"allow\", \"mcc_list\": [\"5932\", \"5411\"]}"
            + "}";

    private static final String MID_JSON = "{" + BASE_FIELDS
            + "\"control_type\": \"mid_limit\","
            + "\"description\": \"Allow the card to be used only in AZ Pizza\","
            + "\"mid_limit\": {\"type\": \"allow\", \"mid_list\": [\"593278\", \"541114\"]}"
            + "}";

    private static void assertBaseFields(final CardControlResponse response) {
        assertEquals("ctr_gp7vkmxayztufjz6top5bjcdra", response.getId());
        assertEquals("crd_fa6psq42dcdd6fdn5gifcq1491", response.getTargetId());
        assertEquals(Boolean.TRUE, response.getIsEditable());
        assertEquals(Instant.parse("2021-09-09T19:41:39Z"), response.getCreatedDate());
        assertEquals(Instant.parse("2021-09-09T19:41:39Z"), response.getLastModifiedDate());
    }

    // ------------------------------------------------------------------------
    // VelocityCardControlResponse (update-control-velocity-limit-response,
    // velocity_limit is VelocityLimitWithRemainingAmount)
    // ------------------------------------------------------------------------

    @Test
    void shouldDeserializeSwaggerExampleForVelocityCardControlResponse() {
        final CardControlResponse response = serializer.fromJson(VELOCITY_JSON, CardControlResponse.class);

        final VelocityCardControlResponse velocity = assertInstanceOf(VelocityCardControlResponse.class, response);
        assertBaseFields(velocity);
        assertEquals(ControlType.VELOCITY_LIMIT, velocity.getControlType());
        assertEquals("Maximum spend of 500€ per week for restaurants", velocity.getDescription());
        assertEquals(Long.valueOf(45000L), velocity.getVelocityLimit().getAmountRemaining());
        assertEquals(Integer.valueOf(50000), velocity.getVelocityLimit().getAmountLimit());
        assertEquals(VelocityWindowType.WEEKLY, velocity.getVelocityLimit().getVelocityWindow().getType());
        assertEquals(Arrays.asList("4121", "4582"), velocity.getVelocityLimit().getMccList());
        assertEquals(Arrays.asList("1234567890"), velocity.getVelocityLimit().getMidList());
    }

    @Test
    void shouldRoundTripVelocityCardControlResponseWithAllFields() {
        final CardControlResponse original = serializer.fromJson(VELOCITY_JSON, CardControlResponse.class);

        final String json = serializer.toJson(original);
        assertTrue(json.contains("\"amount_remaining\":45000"));
        assertTrue(json.contains("\"is_editable\":true"));

        final CardControlResponse roundTripped = serializer.fromJson(json, CardControlResponse.class);
        assertEquals(original, roundTripped);
    }

    // ------------------------------------------------------------------------
    // MccCardControlResponse (update-control-mcc-limit-response)
    // ------------------------------------------------------------------------

    @Test
    void shouldDeserializeSwaggerExampleForMccCardControlResponse() {
        final CardControlResponse response = serializer.fromJson(MCC_JSON, CardControlResponse.class);

        final MccCardControlResponse mcc = assertInstanceOf(MccCardControlResponse.class, response);
        assertBaseFields(mcc);
        assertEquals(ControlType.MCC_LIMIT, mcc.getControlType());
        assertEquals("Allow the card to be used only in restaurants and supermarkets", mcc.getDescription());
        assertEquals("allow", mcc.getMccLimit().getType());
        assertEquals(Arrays.asList("5932", "5411"), mcc.getMccLimit().getMccList());
    }

    @Test
    void shouldRoundTripMccCardControlResponseWithAllFields() {
        final CardControlResponse original = serializer.fromJson(MCC_JSON, CardControlResponse.class);
        final CardControlResponse roundTripped = serializer.fromJson(serializer.toJson(original), CardControlResponse.class);
        assertEquals(original, roundTripped);
    }

    // ------------------------------------------------------------------------
    // MidCardControlResponse (update-control-mid-limit-response).
    // Regression: the mid_limit subtype was not registered, so this body could not
    // be deserialized into CardControlResponse.
    // ------------------------------------------------------------------------

    @Test
    void shouldDeserializeSwaggerExampleForMidCardControlResponse() {
        final CardControlResponse response = serializer.fromJson(MID_JSON, CardControlResponse.class);

        final MidCardControlResponse mid = assertInstanceOf(MidCardControlResponse.class, response);
        assertBaseFields(mid);
        assertEquals(ControlType.MID_LIMIT, mid.getControlType());
        assertEquals("Allow the card to be used only in AZ Pizza", mid.getDescription());
        assertEquals("allow", mid.getMidLimit().getType());
        assertEquals(Arrays.asList("593278", "541114"), mid.getMidLimit().getMidList());
    }

    @Test
    void shouldRoundTripMidCardControlResponseWithAllFields() {
        final CardControlResponse original = serializer.fromJson(MID_JSON, CardControlResponse.class);
        final CardControlResponse roundTripped = serializer.fromJson(serializer.toJson(original), CardControlResponse.class);
        assertEquals(original, roundTripped);
    }

    // ------------------------------------------------------------------------
    // CardControlsQueryResponse (list controls by target) with every subtype
    // ------------------------------------------------------------------------

    @Test
    void shouldDeserializeCardControlsQueryResponseWithAllSubtypes() {
        final String json = "{\"controls\": [" + VELOCITY_JSON + "," + MCC_JSON + "," + MID_JSON + "]}";

        final CardControlsQueryResponse response = serializer.fromJson(json, CardControlsQueryResponse.class);

        assertEquals(3, response.getControls().size());
        assertInstanceOf(VelocityCardControlResponse.class, response.getControls().get(0));
        assertInstanceOf(MccCardControlResponse.class, response.getControls().get(1));
        assertInstanceOf(MidCardControlResponse.class, response.getControls().get(2));
    }

    // ------------------------------------------------------------------------
    // VelocityLimit on requests: amount_remaining is response only and never sent
    // ------------------------------------------------------------------------

    @Test
    void shouldNotSendAmountRemainingOnVelocityControlRequest() {
        final VelocityCardControlRequest request = VelocityCardControlRequest.builder()
                .description("Maximum spend of 500€ per week for restaurants")
                .targetId("crd_fa6psq42dcdd6fdn5gifcq1491")
                .velocityLimit(VelocityLimit.builder()
                        .amountLimit(50000)
                        .velocityWindow(VelocityWindow.builder().type(VelocityWindowType.WEEKLY).build())
                        .mccList(Arrays.asList("4121", "4582"))
                        .build())
                .build();

        assertNull(request.getVelocityLimit().getAmountRemaining());
        final String json = serializer.toJson(request);
        assertFalse(json.contains("amount_remaining"));
        assertTrue(json.contains("\"amount_limit\":50000"));
    }

    @Test
    void shouldBuildMccAndMidLimits() {
        final MccCardControlResponse mcc = MccCardControlResponse.builder()
                .mccLimit(MccLimit.builder().type("block").mccList(Arrays.asList("5411")).build())
                .build();
        final MidCardControlResponse mid = MidCardControlResponse.builder()
                .midLimit(MidLimit.builder().type("block").midList(Arrays.asList("593278")).build())
                .build();

        assertInstanceOf(MccCardControlResponse.class,
                serializer.fromJson(serializer.toJson(mcc), CardControlResponse.class));
        assertInstanceOf(MidCardControlResponse.class,
                serializer.fromJson(serializer.toJson(mid), CardControlResponse.class));
    }
}
