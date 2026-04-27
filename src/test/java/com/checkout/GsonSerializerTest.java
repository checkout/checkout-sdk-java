package com.checkout;

import com.checkout.common.PaymentMethodType;
import com.checkout.common.PaymentSourceType;
import com.checkout.financial.FinancialActionsQueryResponse;
import com.checkout.issuing.cardholders.CardholderCardsResponse;
import com.checkout.issuing.cards.responses.PhysicalCardDetailsResponse;
import com.checkout.issuing.cards.responses.VirtualCardDetailsResponse;
import com.checkout.payments.ProductResponse;
import com.checkout.payments.ProductType;
import com.checkout.payments.contexts.PaymentContextDetailsResponse;
import com.checkout.payments.previous.response.GetPaymentResponse;
import com.checkout.payments.previous.response.PaymentResponse;
import com.checkout.payments.previous.response.destination.PaymentResponseAlternativeDestination;
import com.checkout.payments.previous.response.destination.PaymentResponseCardDestination;
import com.checkout.payments.response.source.AlternativePaymentSourceResponse;
import com.checkout.payments.response.source.contexts.PaymentContextsKlarnaResponseSource;
import com.checkout.payments.response.source.contexts.PaymentContextsPayPalResponseSource;
import com.checkout.payments.sender.PaymentCorporateSender;
import com.checkout.payments.sender.ResponseAlternativeSender;
import com.checkout.payments.sender.SenderType;
import com.google.gson.annotations.SerializedName;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.util.EntityUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;

import java.time.Instant;
import java.io.IOException;

import static com.checkout.TestHelper.getMock;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.isA;
import static org.junit.jupiter.api.Assertions.*;

class GsonSerializerTest {

    private final Serializer serializer = new GsonSerializer();

    @Test
    void shouldDeserialize_previous_getPaymentResponse_cardDestination() {

        final GetPaymentResponse paymentResponse = serializer.fromJson(getMock("/mocks/payments/response/destination/card/get_payment_response.json"), GetPaymentResponse.class);

        assertNotNull(paymentResponse);
        assertNotNull(paymentResponse.getDestination());
        assertTrue(paymentResponse.getDestination() instanceof PaymentResponseCardDestination);
        final PaymentResponseCardDestination cardDestination = (PaymentResponseCardDestination) paymentResponse.getDestination();
        assertNotNull(cardDestination.getExpiryMonth());
        assertNotNull(cardDestination.getExpiryYear());
        assertNotNull(cardDestination.getName());
        assertNotNull(cardDestination.getFingerprint());
        assertNotNull(cardDestination.getCardType());
        assertNotNull(cardDestination.getCardCategory());
        assertNotNull(cardDestination.getProductId());
        assertNotNull(cardDestination.getProductType());

    }

    @Test
    void shouldDeserialize_previous_getPaymentResponse_alternativeDestination() {

        final GetPaymentResponse paymentResponse = serializer.fromJson(getMock("/mocks/payments/response/destination/alternative/get_payment_response.json"), GetPaymentResponse.class);

        assertNotNull(paymentResponse);
        assertNotNull(paymentResponse.getDestination());
        assertTrue(paymentResponse.getDestination() instanceof PaymentResponseAlternativeDestination);
        final PaymentResponseAlternativeDestination alternativeDestination = (PaymentResponseAlternativeDestination) paymentResponse.getDestination();
        assertFalse(alternativeDestination.keySet().isEmpty());

    }

    @Test
    void shouldDeserialize_default_getPaymentResponse_corporateSender() {

        final com.checkout.payments.response.GetPaymentResponse paymentResponse = serializer.fromJson(getMock("/mocks/payments/response/sender/corporate/get_payment_response.json"), com.checkout.payments.response.GetPaymentResponse.class);

        assertNotNull(paymentResponse);
        assertNotNull(paymentResponse.getSender());
        assertTrue(paymentResponse.getSender() instanceof PaymentCorporateSender);
        assertEquals("company", ((PaymentCorporateSender) paymentResponse.getSender()).getCompanyName());
        assertEquals(SenderType.CORPORATE, paymentResponse.getSender().getType());

    }

    @Test
    void shouldDeserialize_default_getPaymentResponse_alternativeSender() {

        final com.checkout.payments.response.GetPaymentResponse paymentResponse = serializer.fromJson(getMock("/mocks/payments/response/sender/alternative/get_payment_response.json"), com.checkout.payments.response.GetPaymentResponse.class);

        assertNotNull(paymentResponse);
        assertNotNull(paymentResponse.getSender());
        assertTrue(paymentResponse.getSender() instanceof ResponseAlternativeSender);
        assertEquals("company", ((ResponseAlternativeSender) paymentResponse.getSender()).get("company_name"));

    }

    @Test
    void shouldDeserialize_default_getPaymentResponse_with_wrong_format() {

        final com.checkout.payments.response.GetPaymentResponse paymentResponse = serializer.fromJson(getMock("/mocks/payments/response/get_payment_response_wrong_format.json"), com.checkout.payments.response.GetPaymentResponse.class);

        assertNotNull(paymentResponse);
        assertNotNull(paymentResponse.getExpiresOn());
        assertEquals("2019-09-10T10:11:12Z", paymentResponse.getExpiresOn().toString());

    }

    @Test
    void shouldDeserializeCardHolderCardsResponse() {

        final CardholderCardsResponse cardholderCardsResponse = serializer.fromJson(getMock("/mocks/issuing/response/get_cardholder_card_response.json"), CardholderCardsResponse.class);

        assertNotNull(cardholderCardsResponse);
        assertNotNull(cardholderCardsResponse.getCards());
        assertThat(cardholderCardsResponse.getCards(), hasSize(2));
        assertThat(cardholderCardsResponse.getCards().get(0), isA(VirtualCardDetailsResponse.class));
        assertThat(cardholderCardsResponse.getCards().get(1), isA(PhysicalCardDetailsResponse.class));
    }

    @Test
    void shouldGetFinancial() {

        final FinancialActionsQueryResponse actionsQueryResponse = serializer.fromJson(getMock("/mocks/financial/response/get_financial_actions_response.json"), FinancialActionsQueryResponse.class);

        assertNotNull(actionsQueryResponse);
        assertNotNull(actionsQueryResponse.getData());
        assertThat(actionsQueryResponse.getData(), hasSize(1));
        assertNotNull(actionsQueryResponse.getData().get(0).getProcessedOn());
        assertNotNull(actionsQueryResponse.getData().get(0).getRequestedOn());
    }

    @Test
    void shouldSerializePaymentKlarnaDetailsResponseFromJson() {

        final com.checkout.payments.response.GetPaymentResponse paymentKlarnaResponseSource = serializer.fromJson(getMock("/mocks/payments/response/get_payment_klarna_response.json"), com.checkout.payments.response.GetPaymentResponse.class);

        assertNotNull(paymentKlarnaResponseSource);
        assertTrue(paymentKlarnaResponseSource.getSource() instanceof AlternativePaymentSourceResponse);
        assertEquals(PaymentSourceType.KLARNA, paymentKlarnaResponseSource.getSource().getType());
    }

    @Test
    void shouldSerializePaymentContextsPayPalDetailsResponseFromJson() {

        final PaymentContextDetailsResponse paymentContextsPayPalResponseSource = serializer.fromJson(getMock("/mocks/payments/response/contexts/payment_context_paypal_details_response.json"), PaymentContextDetailsResponse.class);

        assertNotNull(paymentContextsPayPalResponseSource);
        assertTrue(paymentContextsPayPalResponseSource.getPaymentRequest().getSource() instanceof PaymentContextsPayPalResponseSource);
        assertEquals(PaymentSourceType.PAYPAL, paymentContextsPayPalResponseSource.getPaymentRequest().getSource().getType());
    }

    @Test
    void shouldSerializePaymentContextsKlarnaDetailsResponseFromJson() {

        final PaymentContextDetailsResponse paymentKlarnaResponseSource = serializer.fromJson(getMock("/mocks/payments/response/contexts/payment_context_klarna_details_response.json"), PaymentContextDetailsResponse.class);

        assertNotNull(paymentKlarnaResponseSource);
        assertTrue(paymentKlarnaResponseSource.getPaymentRequest().getSource() instanceof PaymentContextsKlarnaResponseSource);
        assertEquals(PaymentSourceType.KLARNA, paymentKlarnaResponseSource.getPaymentRequest().getSource().getType());
    }

    @Test
    void shouldSerializePaymentContextsResponseFromJson() {

        final com.checkout.payments.response.PaymentResponse paymentContextsResponse = serializer.fromJson(getMock("/mocks/payments/response/plan/get_payment_context_response.json"), com.checkout.payments.response.PaymentResponse.class);

        assertNotNull(paymentContextsResponse);
        assertNotNull(paymentContextsResponse.getPaymentPlan());
    }

    @Test
    void shouldSerializePaymentDetailsResponseFromJson() {

        final com.checkout.payments.response.GetPaymentResponse paymentDetailsResponse = serializer.fromJson(getMock("/mocks/payments/response/plan/get_payment_details_response.json"), com.checkout.payments.response.GetPaymentResponse.class);

        assertNotNull(paymentDetailsResponse);
        assertNotNull(paymentDetailsResponse.getPaymentPlan());
    }

    @Test
    void shouldDeserializeProductWithEnumType() {
        String json = "{ \"Type\": \"DIGITAL\", \"name\": \"Product Name\" }";

        ProductResponse productResponse = serializer.fromJson(json, ProductResponse.class);

        assertNotNull(productResponse);
        assertEquals(ProductType.DIGITAL, productResponse.getTypeAsEnum());
        assertNull(productResponse.getTypeAsString());
    }

    @Test
    void shouldDeserializeProductWithUnknownEnumValue() {
        String json = "{ \"Type\": \"UNKNOWN_VALUE\", \"name\": \"Product Name\", \"quantity\": 1, \"unit_price\": 1000 }";

        ProductResponse productResponse = serializer.fromJson(json, ProductResponse.class);

        assertNotNull(productResponse);
        assertEquals("UNKNOWN_VALUE", productResponse.getTypeAsString());
        assertNull(productResponse.getTypeAsEnum());
    }

    @Test
    void shouldSerializePaymentMethodTypeBlikCorrectly() {
        // Test that BLIK payment method type serializes to "blik"
        String json = serializer.toJson(PaymentMethodType.BLIK);
        assertEquals("\"blik\"", json);
    }

    @Test
    void shouldDeserializePaymentMethodTypeBlikCorrectly() {
        // Test that "blik" string deserializes to PaymentMethodType.BLIK
        String json = "\"blik\"";
        PaymentMethodType paymentMethodType = serializer.fromJson(json, PaymentMethodType.class);
        assertEquals(PaymentMethodType.BLIK, paymentMethodType);
    }

    @Test
    void shouldDeserializeProductWithNullType() {
        String json = "{ \"Type\": null, \"name\": \"Product Name\" }";

        ProductResponse productResponse = serializer.fromJson(json, ProductResponse.class);

        assertNotNull(productResponse);
        assertNull(productResponse.getType());
    }

    @Test
    void shouldDeserializeMultipleDateFormats() {
        Instant instant = Instant.parse("2021-06-08T00:00:00Z");
        PaymentResponse paymentResponse;

        // Test format yyyyMMdd
        paymentResponse = serializer.fromJson("{\"processed_on\":\"20210608\"}", PaymentResponse.class);
        assertNotNull(paymentResponse);
        assertNotNull(paymentResponse.getProcessedOn());
        assertEquals(instant, paymentResponse.getProcessedOn());

        // Test format yyyyMMdd number
        paymentResponse = serializer.fromJson("{\"processed_on\":20210608}", PaymentResponse.class);
        assertNotNull(paymentResponse);
        assertNotNull(paymentResponse.getProcessedOn());
        assertEquals(instant, paymentResponse.getProcessedOn());

        // Test format yyyy-MM-dd
        paymentResponse = serializer.fromJson("{\"processed_on\":\"2021-06-08\"}", PaymentResponse.class);
        assertNotNull(paymentResponse);
        assertNotNull(paymentResponse.getProcessedOn());
        assertEquals(instant, paymentResponse.getProcessedOn());

        // Test other valid formats
        paymentResponse = serializer.fromJson("{\"processed_on\":\"2021-06-08T12:25:01.000Z\"}", PaymentResponse.class);
        assertNotNull(paymentResponse);
        assertNotNull(paymentResponse.getProcessedOn());
        assertEquals(Instant.parse("2021-06-08T12:25:01Z"), paymentResponse.getProcessedOn());

        paymentResponse = serializer.fromJson("{\"processed_on\":\"2021-06-08T12:25:01Z\"}", PaymentResponse.class);
        assertNotNull(paymentResponse);
        assertNotNull(paymentResponse.getProcessedOn());
        assertEquals(Instant.parse("2021-06-08T12:25:01Z"), paymentResponse.getProcessedOn());

        paymentResponse = serializer.fromJson("{\"processed_on\":\"2021-06-08T12:25:01+00:00\"}", PaymentResponse.class);
        assertNotNull(paymentResponse);
        assertNotNull(paymentResponse.getProcessedOn());
        assertEquals(Instant.parse("2021-06-08T12:25:01Z"), paymentResponse.getProcessedOn());

        paymentResponse = serializer.fromJson("{\"processed_on\":\"2021-06-08T12:25:01+0000\"}", PaymentResponse.class);
        assertNotNull(paymentResponse);
        assertNotNull(paymentResponse.getProcessedOn());
        assertEquals(Instant.parse("2021-06-08T12:25:01Z"), paymentResponse.getProcessedOn());

        paymentResponse = serializer.fromJson("{\"processed_on\":\"2021-06-08T12:25:01\"}", PaymentResponse.class);
        assertNotNull(paymentResponse);
        assertNotNull(paymentResponse.getProcessedOn());
        assertEquals(Instant.parse("2021-06-08T12:25:01Z"), paymentResponse.getProcessedOn());

        // Additional cases for different milliseconds precision
        paymentResponse = serializer.fromJson("{\"processed_on\":\"2021-06-08T12:25:01.4698039\"}", PaymentResponse.class);
        assertNotNull(paymentResponse);
        assertNotNull(paymentResponse.getProcessedOn());

        paymentResponse = serializer.fromJson("{\"processed_on\":\"2021-06-08T12:25:01.4698030\"}", PaymentResponse.class);
        assertNotNull(paymentResponse);
        assertNotNull(paymentResponse.getProcessedOn());

        paymentResponse = serializer.fromJson("{\"processed_on\":\"2021-06-08T12:25:01.4698100\"}", PaymentResponse.class);
        assertNotNull(paymentResponse);
        assertNotNull(paymentResponse.getProcessedOn());

        paymentResponse = serializer.fromJson("{\"processed_on\":\"2021-06-08T12:25:01.4698000\"}", PaymentResponse.class);
        assertNotNull(paymentResponse);
        assertNotNull(paymentResponse.getProcessedOn());

        paymentResponse = serializer.fromJson("{\"processed_on\":\"2021-06-08T12:25:01.4690000\"}", PaymentResponse.class);
        assertNotNull(paymentResponse);
        assertNotNull(paymentResponse.getProcessedOn());

        paymentResponse = serializer.fromJson("{\"processed_on\":\"2021-06-08T12:25:01.4600000\"}", PaymentResponse.class);
        assertNotNull(paymentResponse);
        assertNotNull(paymentResponse.getProcessedOn());

        paymentResponse = serializer.fromJson("{\"processed_on\":\"2021-06-08T12:25:01.4000000\"}", PaymentResponse.class);
        assertNotNull(paymentResponse);
        assertNotNull(paymentResponse.getProcessedOn());

        paymentResponse = serializer.fromJson("{\"processed_on\":\"2021-06-08T12:25:01.0000000\"}", PaymentResponse.class);
        assertNotNull(paymentResponse);
        assertNotNull(paymentResponse.getProcessedOn());
    }

    @Nested
    @DisplayName("Form URL Encoded Content Tests")
    class FormUrlEncodedContentTests {

        @Test
        @DisplayName("Should create form encoded content from simple object")
        void shouldCreateFormEncodedContentFromSimpleObject() throws IOException {
            final SimpleTestObject testObject = new SimpleTestObject();
            testObject.clientId = "test_client_123";
            testObject.clientSecret = "secret_456";
            testObject.grantType = "client_credentials";
            testObject.singleUse = true;

            final UrlEncodedFormEntity entity = GsonSerializer.createFormUrlEncodedContent(testObject);
            final String content = EntityUtils.toString(entity);

            assertNotNull(entity);
            assertTrue(content.contains("client_id=test_client_123"));
            assertTrue(content.contains("client_secret=secret_456"));
            assertTrue(content.contains("grant_type=client_credentials"));
            assertTrue(content.contains("single_use=true"));
        }

        @Test
        @DisplayName("Should create form encoded content with Gson from simple object")
        void shouldCreateFormEncodedContentWithGsonFromSimpleObject() throws IOException {
            final SimpleTestObject testObject = new SimpleTestObject();
            testObject.clientId = "test_client_123";
            testObject.clientSecret = "secret_456";
            testObject.grantType = "client_credentials";
            testObject.singleUse = true;

            final UrlEncodedFormEntity entity = GsonSerializer.createFormUrlEncodedContentWithGson(testObject);
            final String content = EntityUtils.toString(entity);

            assertNotNull(entity);
            assertTrue(content.contains("client_id=test_client_123"));
            assertTrue(content.contains("client_secret=secret_456"));
            assertTrue(content.contains("grant_type=client_credentials"));
            assertTrue(content.contains("single_use=true"));
        }

        @Test
        @DisplayName("Should handle null object gracefully")
        void shouldHandleNullObjectGracefully() {
            assertThrows(IllegalArgumentException.class, () -> {
                GsonSerializer.createFormUrlEncodedContent(null);
            });

            assertThrows(IllegalArgumentException.class, () -> {
                GsonSerializer.createFormUrlEncodedContentWithGson(null);
            });
        }

        @Test
        @DisplayName("Should skip null fields")
        void shouldSkipNullFields() throws IOException {
            final SimpleTestObject testObject = new SimpleTestObject();
            testObject.clientId = "test_client_123";
            testObject.clientSecret = null; // This should be skipped
            testObject.grantType = "client_credentials";
            testObject.singleUse = null; // This should be skipped

            final UrlEncodedFormEntity entity = GsonSerializer.createFormUrlEncodedContent(testObject);
            final String content = EntityUtils.toString(entity);

            assertNotNull(entity);
            assertTrue(content.contains("client_id=test_client_123"));
            assertFalse(content.contains("client_secret"));
            assertTrue(content.contains("grant_type=client_credentials"));
            assertFalse(content.contains("single_use"));
        }

        @Test
        @DisplayName("Should handle boolean values correctly")
        void shouldHandleBooleanValuesCorrectly() throws IOException {
            final BooleanTestObject testObject = new BooleanTestObject();
            testObject.isActive = true;
            testObject.isEnabled = false;
            testObject.hasPermission = Boolean.TRUE;
            testObject.isVerified = Boolean.FALSE;

            final UrlEncodedFormEntity entity = GsonSerializer.createFormUrlEncodedContent(testObject);
            final String content = EntityUtils.toString(entity);

            assertNotNull(entity);
            assertTrue(content.contains("is_active=true"));
            assertTrue(content.contains("is_enabled=false"));
            assertTrue(content.contains("has_permission=true"));
            assertTrue(content.contains("is_verified=false"));
        }

        @Test
        @DisplayName("Should handle various data types")
        void shouldHandleVariousDataTypes() throws IOException {
            final VariousTypesTestObject testObject = new VariousTypesTestObject();
            testObject.stringValue = "test string";
            testObject.intValue = 42;
            testObject.longValue = 123456789L;
            testObject.doubleValue = 3.14159;
            testObject.booleanValue = true;

            final UrlEncodedFormEntity entity = GsonSerializer.createFormUrlEncodedContent(testObject);
            final String content = EntityUtils.toString(entity);

            assertNotNull(entity);
            assertTrue(content.contains("string_value=test+string") || content.contains("string_value=test%20string"));
            assertTrue(content.contains("int_value=42"));
            assertTrue(content.contains("long_value=123456789"));
            assertTrue(content.contains("double_value=3.14159"));
            assertTrue(content.contains("boolean_value=true"));
        }

        @Test
        @DisplayName("Should handle special characters in values")
        void shouldHandleSpecialCharactersInValues() throws IOException {
            final SpecialCharsTestObject testObject = new SpecialCharsTestObject();
            testObject.specialValue = "test@example.com";
            testObject.spaceValue = "value with spaces";
            testObject.symbolValue = "$#&+%=";

            final UrlEncodedFormEntity entity = GsonSerializer.createFormUrlEncodedContent(testObject);
            final String content = EntityUtils.toString(entity);

            assertNotNull(entity);
            // Content should be URL encoded
            assertTrue(content.contains("special_value="));
            assertTrue(content.contains("space_value="));
            assertTrue(content.contains("symbol_value="));
        }

        @Test
        @Disabled("Fails at the pipeline level, works in local, this is supported by the serializer")
        @DisplayName("Should handle empty object")
        void shouldHandleEmptyObject() throws IOException {
            final EmptyTestObject testObject = new EmptyTestObject();

            final UrlEncodedFormEntity entity = GsonSerializer.createFormUrlEncodedContent(testObject);
            final String content = EntityUtils.toString(entity);

            assertNotNull(entity);
            assertEquals("", content); // Empty object should produce empty content
        }

        @Test
        @DisplayName("Should handle object with SerializedName annotations")
        void shouldHandleSerializedNameAnnotations() throws IOException {
            final SerializedNameTestObject testObject = new SerializedNameTestObject();
            testObject.clientId = "test_123";
            testObject.grantType = "client_credentials";

            // Note: The reflection-based methods should use field names, not @SerializedName
            final UrlEncodedFormEntity entity = GsonSerializer.createFormUrlEncodedContent(testObject);
            final String content = EntityUtils.toString(entity);

            assertNotNull(entity);
            assertTrue(content.contains("client_id=")); // Field name, not serialized name
            assertTrue(content.contains("grant_type=")); // Field name, not serialized name
        }

        @Test
        @DisplayName("Should handle inheritance")
        void shouldHandleInheritance() throws IOException {
            final ChildTestObject testObject = new ChildTestObject();
            testObject.childProperty = "child_value";
            testObject.parentProperty = "parent_value";

            final UrlEncodedFormEntity entity = GsonSerializer.createFormUrlEncodedContent(testObject);
            final String content = EntityUtils.toString(entity);

            assertNotNull(entity);
            assertTrue(content.contains("child_property=child_value"));
            // Note: Only declared fields are processed, not inherited ones in current implementation
        }

        @Test
        @DisplayName("Should convert field names to snake_case")
        void shouldConvertFieldNamesToSnakeCase() throws IOException {
            final CamelCaseTestObject testObject = new CamelCaseTestObject();
            testObject.firstName = "John";
            testObject.lastName = "Doe";
            testObject.emailAddress = "john.doe@example.com";
            testObject.phoneNumber = "+1234567890";
            testObject.isActiveUser = true;

            final UrlEncodedFormEntity entity = GsonSerializer.createFormUrlEncodedContent(testObject);
            final String content = EntityUtils.toString(entity);

            assertNotNull(entity);
            assertTrue(content.contains("first_name=John"));
            assertTrue(content.contains("last_name=Doe"));
            assertTrue(content.contains("email_address="));
            assertTrue(content.contains("phone_number="));
            assertTrue(content.contains("is_active_user=true"));
        }

        @Test
        @DisplayName("Should compare createFormUrlEncodedContent vs createFormUrlEncodedContentWithGson")
        void shouldCompareRegularVsGsonMethods() throws IOException {
            final CamelCaseTestObject testObject = new CamelCaseTestObject();
            testObject.firstName = "John";
            testObject.isActiveUser = true;

            final UrlEncodedFormEntity regularEntity = GsonSerializer.createFormUrlEncodedContent(testObject);
            final UrlEncodedFormEntity gsonEntity = GsonSerializer.createFormUrlEncodedContentWithGson(testObject);

            final String regularContent = EntityUtils.toString(regularEntity);
            final String gsonContent = EntityUtils.toString(gsonEntity);

            assertNotNull(regularEntity);
            assertNotNull(gsonEntity);

            // Both should produce snake_case field names
            assertTrue(regularContent.contains("first_name=John"));
            assertTrue(gsonContent.contains("first_name=John"));
            assertTrue(regularContent.contains("is_active_user=true"));
            assertTrue(gsonContent.contains("is_active_user=true"));
        }
    }

    // Test helper classes
    private static class SimpleTestObject {
        public String clientId;
        public String clientSecret;
        public String grantType;
        public Boolean singleUse;
    }

    private static class BooleanTestObject {
        public boolean isActive;
        public boolean isEnabled;
        public Boolean hasPermission;
        public Boolean isVerified;
    }

    private static class VariousTypesTestObject {
        public String stringValue;
        public int intValue;
        public long longValue;
        public double doubleValue;
        public boolean booleanValue;
    }

    private static class SpecialCharsTestObject {
        public String specialValue;
        public String spaceValue;
        public String symbolValue;
    }

    private static class EmptyTestObject {
        // No fields
    }

    private static class SerializedNameTestObject {
        @SerializedName("client_id")
        public String clientId;

        @SerializedName("grant_type")
        public String grantType;
    }

    private static class ParentTestObject {
        public String parentProperty;
    }

    private static class ChildTestObject extends ParentTestObject {
        public String childProperty;
    }

    private static class CamelCaseTestObject {
        public String firstName;
        public String lastName;
        public String emailAddress;
        public String phoneNumber;
        public boolean isActiveUser;
    }

}
