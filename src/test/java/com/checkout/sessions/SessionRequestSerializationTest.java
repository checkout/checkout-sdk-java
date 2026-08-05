package com.checkout.sessions;

import com.checkout.GsonSerializer;
import com.checkout.Serializer;
import com.checkout.common.CountryCode;
import com.checkout.common.Currency;
import com.checkout.common.Phone;
import com.checkout.sessions.channel.BrowserSession;
import com.checkout.sessions.channel.ThreeDsMethodCompletion;
import com.checkout.sessions.completion.NonHostedCompletionInfo;
import com.checkout.sessions.source.SessionCardSource;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Serialization coverage for {@link SessionRequest}. The class is serialize-only: {@code source},
 * {@code completion} and {@code channel_data} are abstract types with no registered Gson adapter, so
 * a deserialization roundtrip is not possible. Every property is therefore asserted on the emitted
 * JSON.
 */
class SessionRequestSerializationTest {

    private final Serializer serializer = new GsonSerializer();

    private static SessionRequest fullyPopulated() {
        return SessionRequest.builder()
                .source(SessionCardSource.builder()
                        .email("bruce@wayne-enterprises.com")
                        .expiryMonth(1)
                        .expiryYear(2030)
                        .number("4485040371536584")
                        .name("Bruce Wayne")
                        .billingAddress(SessionAddress.builderSessionAddress()
                                .addressLine1("Checkout.com")
                                .city("London")
                                .country(CountryCode.GB)
                                .zip("W1T 4TJ")
                                .build())
                        .homePhone(Phone.builder().number("0204567895").countryCode("234").build())
                        .build())
                .amount(6540L)
                .currency(Currency.USD)
                .processingChannelId("pc_q4dbxom5jbgudnjzjpz7iw4d0u")
                .marketplace(SessionMarketplaceData.builder()
                        .subEntityId("ent_ocw5i74vowfg2edpy66izhts2u")
                        .build())
                .authenticationType(AuthenticationType.REGULAR)
                .authenticationCategory(Category.PAYMENT)
                .cardholderAccountInfo(CardholderAccountInfo.builder()
                        .purchaseCount(10L)
                        .addCardAttempts(5L)
                        .transactionsToday(3L)
                        .build())
                .challengeIndicator(SessionChallengeIndicator.TRUSTED_LISTING_PROMPT)
                .billingDescriptor(SessionsBillingDescriptor.builder().name("SUPERHEROES.COM").build())
                .reference("ORD-5023-4E89")
                .merchantRiskInfo(MerchantRiskInfo.builder()
                        .deliveryEmail("bruce@wayne-enterprises.com")
                        .isPreorder(false)
                        .isReorder(false)
                        .build())
                .priorTransactionReference("prior-txn-ref")
                .transactionType(TransactionType.GOODS_SERVICE)
                .shippingAddress(SessionAddress.builderSessionAddress()
                        .addressLine1("Checkout.com")
                        .addressLine2("ABC building")
                        .city("London")
                        .country(CountryCode.GB)
                        .state("ENG")
                        .zip("W1T 4TJ")
                        .build())
                .shippingAddressMatchesBilling(Boolean.TRUE)
                .completion(NonHostedCompletionInfo.builder()
                        .callbackUrl("https://merchant.com/callback")
                        .build())
                .channelData(BrowserSession.builder()
                        .acceptHeader("Accept:  *.*, q=0.1")
                        .javaEnabled(true)
                        .javascriptEnabled(true)
                        .language("FR-fr")
                        .colorDepth("16")
                        .screenWidth("1920")
                        .screenHeight("1080")
                        .timezone("60")
                        .userAgent("Mozilla/5.0")
                        .threeDsMethodCompletion(ThreeDsMethodCompletion.Y)
                        .ipAddress("1.12.123.255")
                        .build())
                .recurring(Recurring.builder().daysBetweenPayments(30L).expiry("99991231").build())
                .installment(Installment.builder()
                        .numberOfPayments(3L)
                        .daysBetweenPayments(30L)
                        .expiry("99991231")
                        .build())
                .optimization(Optimization.builder().optimized(true).framework("acceptance_rates").build())
                .initialTransaction(InitialTransaction.builder()
                        .acsTransactionId("acs-txn-id")
                        .authenticationMethod("frictionless")
                        .authenticationTimestamp("2026-08-03T10:11:12Z")
                        .authenticationData("auth-data")
                        .initialSessionId("sid_y3oqhf46pyzuxjbcn2giaqnb44")
                        .build())
                .deviceInformation(DeviceInformation.builder()
                        .deviceId("device-id")
                        .deviceSessionId("device-session-id")
                        .build())
                .googleSpa(GoogleSpa.builder()
                        .continueUrl("https://merchant.com/continue")
                        .build())
                .preferredExperiences(Arrays.asList(Experience.THREE_DS, Experience.GOOGLE_SPA))
                .build();
    }

    @Test
    void shouldSerializeWithRequiredFieldsOnly() {
        final SessionRequest request = SessionRequest.builder()
                .source(SessionCardSource.builder().number("4485040371536584").build())
                .currency(Currency.USD)
                .completion(NonHostedCompletionInfo.builder()
                        .callbackUrl("https://merchant.com/callback")
                        .build())
                .build();

        assertDoesNotThrow(() -> serializer.toJson(request));
    }

    @Test
    void shouldSerializeEveryProperty() {
        final String json = serializer.toJson(fullyPopulated());

        assertNotNull(json);

        final String[] expectedKeys = {
                "source",
                "amount",
                "currency",
                "processing_channel_id",
                "marketplace",
                "authentication_type",
                "authentication_category",
                "account_info",
                "challenge_indicator",
                "billing_descriptor",
                "reference",
                "merchant_risk_info",
                "transaction_type",
                "shipping_address",
                "shipping_address_matches_billing",
                "completion",
                "channel_data",
                "recurring",
                "installment",
                "optimization",
                "initial_transaction",
                "device_information",
                "google_spa",
                "preferred_experiences"
        };

        for (final String key : expectedKeys) {
            assertTrue(json.contains("\"" + key + "\""), "missing property " + key + " in " + json);
        }

        // Reflection guard: a field added to SessionRequest without being classified as either a spec
        // property or a known non-spec property fails here. Synthetic fields are skipped because
        // JaCoCo injects $jacocoData under Gradle.
        final long declared = java.util.Arrays.stream(SessionRequest.class.getDeclaredFields())
                .filter(field -> !field.isSynthetic())
                .count();
        assertEquals(expectedKeys.length, declared,
                "SessionRequest declares a property this test classifies as neither spec nor known-extra");
    }

    @Test
    void shouldSerializeScalarValuesAndEnumsAsSnakeCase() {
        final String json = serializer.toJson(fullyPopulated());

        assertTrue(json.contains("\"amount\":6540"), json);
        assertTrue(json.contains("\"currency\":\"USD\""), json);
        assertTrue(json.contains("\"processing_channel_id\":\"pc_q4dbxom5jbgudnjzjpz7iw4d0u\""), json);
        assertTrue(json.contains("\"authentication_type\":\"regular\""), json);
        assertTrue(json.contains("\"authentication_category\":\"payment\""), json);
        assertTrue(json.contains("\"challenge_indicator\":\"trusted_listing_prompt\""), json);
        assertTrue(json.contains("\"reference\":\"ORD-5023-4E89\""), json);
        assertTrue(json.contains("\"transaction_type\":\"goods_service\""), json);
        assertTrue(json.contains("\"shipping_address_matches_billing\":true"), json);
    }

    @Test
    void shouldSerializeNestedObjectContents() {
        final String json = serializer.toJson(fullyPopulated());

        assertTrue(json.contains("\"sub_entity_id\":\"ent_ocw5i74vowfg2edpy66izhts2u\""), json);
        assertTrue(json.contains("\"purchase_count\":10"), json);
        assertTrue(json.contains("\"name\":\"SUPERHEROES.COM\""), json);
        assertTrue(json.contains("\"delivery_email\":\"bruce@wayne-enterprises.com\""), json);
        assertTrue(json.contains("\"callback_url\":\"https://merchant.com/callback\""), json);
        assertTrue(json.contains("\"number_of_payments\":3"), json);
        assertTrue(json.contains("\"framework\":\"acceptance_rates\""), json);
        assertTrue(json.contains("\"acs_transaction_id\":\"acs-txn-id\""), json);
        assertTrue(json.contains("\"device_session_id\":\"device-session-id\""), json);
    }

}
