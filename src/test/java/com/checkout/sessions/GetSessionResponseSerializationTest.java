package com.checkout.sessions;

import com.checkout.GsonSerializer;
import com.checkout.Serializer;
import com.checkout.common.CardCategory;
import com.checkout.common.CardType;
import com.checkout.common.CountryCode;
import com.checkout.common.Currency;
import com.checkout.common.Exemption;
import com.checkout.common.ThreeDSFlowType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Full-property deserialization coverage for {@link GetSessionResponse}, the schema returned by
 * {@code GET /sessions/{id}} and {@code PUT /sessions/{id}/collect-data}.
 * <p>
 * Every one of the class's 40 properties is populated and asserted, plus the {@code _links} it
 * inherits from {@link com.checkout.common.Resource}. Nested objects are covered transitively.
 */
class GetSessionResponseSerializationTest {

    private final Serializer serializer = new GsonSerializer();

    private GetSessionResponse response;

    private static String fullPayload() {
        return "{"
                + "\"id\":\"sid_y3oqhf46pyzuxjbcn2giaqnb44\","
                + "\"session_secret\":\"sek_Dal7UyiH8rIFXA4PfgiIk2jUyQkVDeEWgVBEL4TsRTE=\","
                + "\"transaction_id\":\"9aea641d-0549-4222-9ca9-d90b43a4f38c\","
                + "\"scheme\":\"visa\","
                + "\"amount\":6540,"
                + "\"currency\":\"USD\","
                + "\"completed\":true,"
                + "\"challenged\":true,"
                + "\"authentication_type\":\"regular\","
                + "\"authentication_category\":\"payment\","
                + "\"certificates\":{\"ds_public\":\"ds-public-key\",\"ca_public\":\"ca-public-key\"},"
                + "\"status\":\"challenged\","
                + "\"status_reason\":\"ares_status\","
                + "\"approved\":true,"
                + "\"protocol_version\":\"2.2.0\","
                + "\"account_info\":{\"purchase_count\":10,\"add_card_attempts\":5,\"transactions_today\":3},"
                + "\"merchant_risk_info\":{\"delivery_email\":\"bruce@wayne-enterprises.com\","
                + "\"is_preorder\":false,\"is_reorder\":false},"
                + "\"reference\":\"ORD-5023-4E89\","
                + "\"transaction_type\":\"goods_service\","
                + "\"next_actions\":[\"collect_channel_data\",\"challenge_cardholder\"],"
                + "\"ds\":{\"ds_id\":\"ds-id\",\"reference_number\":\"ds-ref\",\"transaction_id\":\"ds-txn\"},"
                + "\"acs\":{\"reference_number\":\"acs-ref\",\"transaction_id\":\"acs-txn\","
                + "\"operator_id\":\"acs-operator\",\"url\":\"https://acs.example.com/challenge\","
                + "\"signed_content\":\"signed-content\",\"challenge_mandated\":true,"
                + "\"authentication_type\":\"static\",\"challenge_cancel_reason\":\"cardholder_cancel\","
                + "\"interface\":\"html\",\"ui_template\":\"single_select\"},"
                + "\"response_code\":\"Y\","
                + "\"response_status_reason\":\"01\","
                + "\"pareq\":\"pareq-value\","
                + "\"cryptogram\":\"MTIzNDU2Nzg5MDA5ODc2NTQzMjE=\","
                + "\"eci\":\"05\","
                + "\"xid\":\"XSUErNftqkiTdlkpSk8p32GWOFA\","
                + "\"cardholder_info\":\"Card declined. Please contact your issuing bank.\","
                + "\"card\":{\"instrument_id\":\"src_w4jelhppmfiufdnatndh3wtsfq\",\"fingerprint\":\"fp-1\","
                + "\"metadata\":{\"card_type\":\"CREDIT\",\"card_category\":\"CONSUMER\","
                + "\"issuer_name\":\"Checkout\",\"issuer_country\":\"GB\","
                + "\"product_id\":\"MDS\",\"product_type\":\"Debit MasterCard Card\"}},"
                + "\"recurring\":{\"days_between_payments\":30,\"expiry\":\"99991231\"},"
                + "\"installment\":{\"number_of_payments\":3,\"days_between_payments\":30,\"expiry\":\"99991231\"},"
                + "\"initial_transaction\":{\"acs_transaction_id\":\"acs-txn-id\","
                + "\"authentication_method\":\"frictionless\","
                + "\"authentication_timestamp\":\"2026-08-03T10:11:12Z\","
                + "\"authentication_data\":\"auth-data\","
                + "\"initial_session_id\":\"sid_y3oqhf46pyzuxjbcn2giaqnb44\"},"
                + "\"customer_ip\":\"192.168.1.1\","
                + "\"authentication_date\":\"2026-08-03T10:11:12Z\","
                + "\"exemption\":{\"requested\":\"none\",\"applied\":\"low_value\",\"code\":\"cb-code\"},"
                + "\"flow_type\":\"challenged\","
                + "\"challenge_indicator\":\"trusted_listing\","
                + "\"optimization\":{\"optimized\":true,\"framework\":\"acceptance_rates\","
                + "\"optimized_properties\":[{\"field\":\"amount\",\"original_value\":\"1\",\"optimized_value\":\"2\"}]},"
                + "\"scheme_info\":{\"name\":\"visa\",\"score\":\"0.5\",\"avalgo\":\"1\"},"
                + "\"_links\":{\"self\":{\"href\":\"https://api.checkout.com/sessions/sid_y3oqhf46pyzuxjbcn2giaqnb44\"}}"
                + "}";
    }

    @BeforeEach
    void setUp() {
        response = serializer.fromJson(fullPayload(), GetSessionResponse.class);
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
        assertEquals(SessionScheme.VISA, response.getScheme());
        assertEquals(AuthenticationType.REGULAR, response.getAuthenticationType());
        assertEquals(Category.PAYMENT, response.getAuthenticationCategory());
        assertEquals(SessionStatus.CHALLENGED, response.getStatus());
        assertEquals(StatusReason.ARES_STATUS, response.getStatusReason());
        assertEquals(TransactionType.GOODS_SERVICE, response.getTransactionType());
        assertEquals(ResponseCode.Y, response.getResponseCode());
        assertEquals(ThreeDSFlowType.CHALLENGED, response.getFlowType());
        assertEquals(SessionChallengeIndicator.TRUSTED_LISTING, response.getChallengeIndicator());
        assertEquals(Arrays.asList(NextAction.COLLECT_CHANNEL_DATA, NextAction.CHALLENGE_CARDHOLDER),
                response.getNextActions());
    }

    @Test
    void shouldDeserializeBooleanFlags() {
        assertEquals(Boolean.TRUE, response.getCompleted());
        assertEquals(Boolean.TRUE, response.getChallenged());
        assertEquals(Boolean.TRUE, response.getApproved());
    }

    @Test
    void shouldDeserializeAuthenticationResultFields() {
        assertEquals("01", response.getResponseStatusReason());
        assertEquals("pareq-value", response.getPareq());
        assertEquals("MTIzNDU2Nzg5MDA5ODc2NTQzMjE=", response.getCryptogram());
        assertEquals("05", response.getEci());
        assertEquals("XSUErNftqkiTdlkpSk8p32GWOFA", response.getXid());
        assertEquals("Card declined. Please contact your issuing bank.", response.getCardholderInfo());
        assertEquals("192.168.1.1", response.getCustomerIp());
    }

    @Test
    void shouldDeserializeAuthenticationDateAsInstant() {
        assertEquals(Instant.parse("2026-08-03T10:11:12Z"), response.getAuthenticationDate());
    }

    @Test
    void shouldDeserializeAccountInfoUnderTheAccountInfoKey() {
        assertNotNull(response.getCardholderAccountInfo());
        assertEquals(10L, response.getCardholderAccountInfo().getPurchaseCount());
        assertEquals(5L, response.getCardholderAccountInfo().getAddCardAttempts());
        assertEquals(3L, response.getCardholderAccountInfo().getTransactionsToday());
    }

    @Test
    void shouldDeserializeNestedObjects() {
        assertNotNull(response.getCertificates());
        assertEquals("ds-public-key", response.getCertificates().getDsPublic());
        assertEquals("ca-public-key", response.getCertificates().getCaPublic());

        assertNotNull(response.getMerchantRiskInfo());
        assertEquals("bruce@wayne-enterprises.com", response.getMerchantRiskInfo().getDeliveryEmail());

        assertNotNull(response.getDs());
        assertEquals("ds-id", response.getDs().getDsId());

        assertNotNull(response.getCard());
        assertEquals("src_w4jelhppmfiufdnatndh3wtsfq", response.getCard().getInstrumentId());
        assertEquals("fp-1", response.getCard().getFingerprint());

        assertNotNull(response.getRecurring());
        assertEquals(30L, response.getRecurring().getDaysBetweenPayments());

        assertNotNull(response.getInstallment());
        assertEquals(3L, response.getInstallment().getNumberOfPayments());

        assertNotNull(response.getInitialTransaction());
        assertEquals("acs-txn-id", response.getInitialTransaction().getAcsTransactionId());

        assertNotNull(response.getSchemeInfo());
        assertEquals(SessionScheme.VISA, response.getSchemeInfo().getName());

        assertNotNull(response.getOptimization());
        assertEquals(Boolean.TRUE, response.getOptimization().getOptimized());
        assertNotNull(response.getOptimization().getOptimizedProperties());
        assertEquals(1, response.getOptimization().getOptimizedProperties().size());
    }

    @Test
    void shouldDeserializeAcsWithReservedInterfaceKey() {
        assertNotNull(response.getAcs());
        assertEquals("acs-ref", response.getAcs().getReferenceNumber());
        assertEquals("https://acs.example.com/challenge", response.getAcs().getUrl());
        assertEquals(Boolean.TRUE, response.getAcs().getChallengeMandated());
        assertEquals(ChallengeCancelReason.CARDHOLDER_CANCEL, response.getAcs().getChallengeCancelReason());
        assertEquals(SessionInterface.HTML, response.getAcs().getSessionInterface());
        assertEquals(UIElements.SINGLE_SELECT, response.getAcs().getUiTemplate());
    }

    /**
     * Covers all six properties of {@link SessionsCardMetadataResponse}. The spec sends the card
     * type and category in upper case, which the shared enums accept via {@code alternate} values.
     */
    @Test
    void shouldDeserializeEveryCardMetadataProperty() {
        final SessionsCardMetadataResponse metadata = response.getCard().getMetadata();

        assertNotNull(metadata);
        assertEquals(CardType.CREDIT, metadata.getCardType());
        assertEquals(CardCategory.CONSUMER, metadata.getCardCategory());
        assertEquals("Checkout", metadata.getIssuerName());
        assertEquals(CountryCode.GB, metadata.getIssuerCountry());
        assertEquals("MDS", metadata.getProductId());
        assertEquals("Debit MasterCard Card", metadata.getProductType());
    }

    @Test
    void shouldDeserializeExemption() {
        assertNotNull(response.getExemption());
        assertEquals("none", response.getExemption().getRequested());
        assertEquals(Exemption.LOW_VALUE, response.getExemption().getApplied());
        assertEquals("cb-code", response.getExemption().getCode());
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
        final java.lang.reflect.Field[] fields = GetSessionResponse.class.getDeclaredFields();
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
        assertTrue(fields.length >= 40, "expected at least 40 declared properties, found " + fields.length);
    }

}
