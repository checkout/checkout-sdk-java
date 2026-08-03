package com.checkout.sessions;

import com.checkout.GsonSerializer;
import com.checkout.Serializer;
import com.checkout.common.Currency;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Full-property deserialization coverage for {@link CreateSessionAcceptedResponse}, the 202 schema
 * returned by {@code POST /sessions}.
 * <p>
 * Every one of the class's 22 properties is populated and asserted, plus the {@code _links} it
 * inherits from {@link com.checkout.common.Resource}.
 */
class CreateSessionAcceptedResponseSerializationTest {

    private final Serializer serializer = new GsonSerializer();

    private CreateSessionAcceptedResponse response;

    private static String fullPayload() {
        return "{"
                + "\"id\":\"sid_y3oqhf46pyzuxjbcn2giaqnb44\","
                + "\"session_secret\":\"sek_Dal7UyiH8rIFXA4PfgiIk2jUyQkVDeEWgVBEL4TsRTE=\","
                + "\"transaction_id\":\"9aea641d-0549-4222-9ca9-d90b43a4f38c\","
                + "\"scheme\":\"mastercard\","
                + "\"amount\":6540,"
                + "\"currency\":\"USD\","
                + "\"authentication_type\":\"regular\","
                + "\"authentication_category\":\"payment\","
                + "\"status\":\"pending\","
                + "\"status_reason\":\"ares_status\","
                + "\"next_actions\":[\"collect_channel_data\"],"
                + "\"protocol_version\":\"2.2.0\","
                + "\"account_info\":{\"purchase_count\":10,\"add_card_attempts\":5,\"transactions_today\":3},"
                + "\"merchant_risk_info\":{\"delivery_email\":\"bruce@wayne-enterprises.com\","
                + "\"is_preorder\":false,\"is_reorder\":false},"
                + "\"reference\":\"ORD-5023-4E89\","
                + "\"card\":{\"instrument_id\":\"src_w4jelhppmfiufdnatndh3wtsfq\",\"fingerprint\":\"fp-1\"},"
                + "\"recurring\":{\"days_between_payments\":30,\"expiry\":\"99991231\"},"
                + "\"installment\":{\"number_of_payments\":3,\"days_between_payments\":30,\"expiry\":\"99991231\"},"
                + "\"initial_transaction\":{\"acs_transaction_id\":\"acs-txn-id\","
                + "\"authentication_method\":\"frictionless\","
                + "\"authentication_timestamp\":\"2026-08-03T10:11:12Z\","
                + "\"authentication_data\":\"auth-data\","
                + "\"initial_session_id\":\"sid_y3oqhf46pyzuxjbcn2giaqnb44\"},"
                + "\"authentication_date\":\"2026-08-03T10:11:12Z\","
                + "\"challenge_indicator\":\"transaction_risk_assessment\","
                + "\"optimization\":{\"optimized\":true,\"framework\":\"acceptance_rates\","
                + "\"optimized_properties\":[{\"field\":\"amount\",\"original_value\":\"1\",\"optimized_value\":\"2\"}]},"
                + "\"_links\":{\"self\":{\"href\":\"https://api.checkout.com/sessions/sid_y3oqhf46pyzuxjbcn2giaqnb44\"}}"
                + "}";
    }

    @BeforeEach
    void setUp() {
        response = serializer.fromJson(fullPayload(), CreateSessionAcceptedResponse.class);
        assertNotNull(response);
    }

    @Test
    void shouldDeserializeIdentifiersAndAmounts() {
        assertEquals("sid_y3oqhf46pyzuxjbcn2giaqnb44", response.getId());
        assertEquals("sek_Dal7UyiH8rIFXA4PfgiIk2jUyQkVDeEWgVBEL4TsRTE=", response.getSessionSecret());
        assertEquals("9aea641d-0549-4222-9ca9-d90b43a4f38c", response.getTransactionId());
        assertEquals(6540L, response.getAmount());
        assertEquals(Currency.USD, response.getCurrency());
        assertEquals("2.2.0", response.getProtocolVersion());
        assertEquals("ORD-5023-4E89", response.getReference());
    }

    @Test
    void shouldDeserializeEveryEnumTypedProperty() {
        assertEquals(SessionScheme.MASTERCARD, response.getScheme());
        assertEquals(AuthenticationType.REGULAR, response.getAuthenticationType());
        assertEquals(Category.PAYMENT, response.getAuthenticationCategory());
        assertEquals(SessionStatus.PENDING, response.getStatus());
        assertEquals(StatusReason.ARES_STATUS, response.getStatusReason());
        assertEquals(SessionChallengeIndicator.TRANSACTION_RISK_ASSESSMENT, response.getChallengeIndicator());
        assertEquals(Arrays.asList(NextAction.COLLECT_CHANNEL_DATA), response.getNextActions());
    }

    @Test
    void shouldDeserializeAuthenticationDateAsInstant() {
        assertEquals(Instant.parse("2026-08-03T10:11:12Z"), response.getAuthenticationDate());
    }

    /**
     * This class names the field {@code accountInfo} and relies on the global
     * {@code LOWER_CASE_WITH_UNDERSCORES} policy, whereas {@link GetSessionResponse} names it
     * {@code cardholderAccountInfo} with an explicit {@code @SerializedName}. Both must map the same
     * {@code account_info} wire key.
     */
    @Test
    void shouldDeserializeAccountInfoUnderTheAccountInfoKey() {
        assertNotNull(response.getAccountInfo());
        assertEquals(10L, response.getAccountInfo().getPurchaseCount());
        assertEquals(5L, response.getAccountInfo().getAddCardAttempts());
        assertEquals(3L, response.getAccountInfo().getTransactionsToday());
    }

    @Test
    void shouldDeserializeNestedObjects() {
        assertNotNull(response.getMerchantRiskInfo());
        assertEquals("bruce@wayne-enterprises.com", response.getMerchantRiskInfo().getDeliveryEmail());

        assertNotNull(response.getCard());
        assertEquals("src_w4jelhppmfiufdnatndh3wtsfq", response.getCard().getInstrumentId());
        assertEquals("fp-1", response.getCard().getFingerprint());

        assertNotNull(response.getRecurring());
        assertEquals(30L, response.getRecurring().getDaysBetweenPayments());

        assertNotNull(response.getInstallment());
        assertEquals(3L, response.getInstallment().getNumberOfPayments());

        assertNotNull(response.getInitialTransaction());
        assertEquals("acs-txn-id", response.getInitialTransaction().getAcsTransactionId());

        assertNotNull(response.getOptimization());
        assertEquals(Boolean.TRUE, response.getOptimization().getOptimized());
        assertEquals("acceptance_rates", response.getOptimization().getFramework());
        assertNotNull(response.getOptimization().getOptimizedProperties());
        assertEquals(1, response.getOptimization().getOptimizedProperties().size());
    }

    @Test
    void shouldDeserializeInheritedLinks() {
        assertNotNull(response.getSelfLink());
        assertEquals("https://api.checkout.com/sessions/sid_y3oqhf46pyzuxjbcn2giaqnb44",
                response.getSelfLink().getHref());
    }

    /**
     * Guards against a property being silently dropped: every field declared on the class must be
     * non-null after deserializing a payload that populates all of them.
     */
    @Test
    void shouldLeaveNoDeclaredPropertyNull() {
        final java.lang.reflect.Field[] fields = CreateSessionAcceptedResponse.class.getDeclaredFields();
        for (final java.lang.reflect.Field field : fields) {
            if (field.isSynthetic()) {
                continue;
            }
            field.setAccessible(true);
            try {
                assertNotNull(field.get(response), "property was not deserialized: " + field.getName());
            } catch (final IllegalAccessException e) {
                throw new AssertionError(e);
            }
        }
        assertTrue(fields.length >= 22, "expected at least 22 declared properties, found " + fields.length);
    }

}
