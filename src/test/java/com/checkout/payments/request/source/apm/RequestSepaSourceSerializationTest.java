package com.checkout.payments.request.source.apm;

import com.checkout.GsonSerializer;
import com.checkout.common.AccountHolder;
import com.checkout.common.AccountHolderType;
import com.checkout.common.Address;
import com.checkout.common.CountryCode;
import com.checkout.common.Currency;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class RequestSepaSourceSerializationTest {

    private final GsonSerializer serializer = new GsonSerializer();

    @Test
    void shouldSerializeWithRequiredFields() {
        final RequestSepaSource source = RequestSepaSource.builder()
                .country(CountryCode.FR)
                .accountNumber("FR7630006000011234567890189")
                .currency(Currency.EUR)
                .accountHolder(AccountHolder.builder()
                        .firstName("John")
                        .lastName("Smith")
                        .billingAddress(Address.builder()
                                .addressLine1("123 Rue de la Paix")
                                .city("Paris")
                                .zip("75000")
                                .country(CountryCode.FR)
                                .build())
                        .build())
                .build();

        assertDoesNotThrow(() -> serializer.toJson(source));
    }

    @Test
    void shouldSerializeWithAllOptionalFields() {
        final RequestSepaSource source = RequestSepaSource.builder()
                .country(CountryCode.FR)
                .accountNumber("FR7630006000011234567890189")
                .currency(Currency.EUR)
                .mandateId("123456")
                .mandateType(MandateType.B2B)
                .dateOfSignature("2022-08-02")
                .accountHolder(AccountHolder.builder()
                        .type(AccountHolderType.CORPORATE)
                        .firstName("John")
                        .lastName("Smith")
                        .companyName("Acme Corp")
                        .billingAddress(Address.builder()
                                .addressLine1("123 Rue de la Paix")
                                .city("Paris")
                                .zip("75000")
                                .country(CountryCode.FR)
                                .build())
                        .build())
                .build();

        assertDoesNotThrow(() -> serializer.toJson(source));
    }

    @Test
    void shouldSerializeMandateTypeB2B() {
        final RequestSepaSource source = RequestSepaSource.builder()
                .country(CountryCode.FR)
                .accountNumber("FR7630006000011234567890189")
                .currency(Currency.EUR)
                .mandateType(MandateType.B2B)
                .accountHolder(AccountHolder.builder()
                        .firstName("John")
                        .lastName("Smith")
                        .billingAddress(Address.builder()
                                .country(CountryCode.FR).build())
                        .build())
                .build();

        final String json = serializer.toJson(source);

        assertTrue(json.contains("\"mandate_type\":\"B2B\""),
                "mandate_type should serialize as 'B2B'. Got: " + json);
    }

    @Test
    void shouldSerializeMandateTypeCore() {
        final RequestSepaSource source = RequestSepaSource.builder()
                .country(CountryCode.FR)
                .accountNumber("FR7630006000011234567890189")
                .currency(Currency.EUR)
                .mandateType(MandateType.CORE)
                .accountHolder(AccountHolder.builder()
                        .firstName("John")
                        .lastName("Smith")
                        .billingAddress(Address.builder()
                                .country(CountryCode.FR).build())
                        .build())
                .build();

        final String json = serializer.toJson(source);

        assertTrue(json.contains("\"mandate_type\":\"Core\""),
                "mandate_type should serialize as 'Core'. Got: " + json);
    }

    @Test
    void shouldRoundTripSerialize() {
        final RequestSepaSource original = RequestSepaSource.builder()
                .country(CountryCode.FR)
                .accountNumber("FR7630006000011234567890189")
                .currency(Currency.EUR)
                .mandateId("123456")
                .mandateType(MandateType.B2B)
                .dateOfSignature("2022-08-02")
                .accountHolder(AccountHolder.builder()
                        .firstName("John")
                        .lastName("Smith")
                        .billingAddress(Address.builder()
                                .country(CountryCode.FR).build())
                        .build())
                .build();

        final String json = serializer.toJson(original);
        final RequestSepaSource deserialized = serializer.fromJson(json, RequestSepaSource.class);

        assertNotNull(deserialized);
        assertEquals(CountryCode.FR, deserialized.getCountry());
        assertEquals("FR7630006000011234567890189", deserialized.getAccountNumber());
        assertEquals(MandateType.B2B, deserialized.getMandateType());
        assertEquals("123456", deserialized.getMandateId());
    }

    @Test
    void shouldDeserializeSwaggerExample() {
        final String swaggerJson = "{"
                + "\"type\":\"sepa\","
                + "\"country\":\"FR\","
                + "\"account_number\":\"FR7630006000011234567890189\","
                + "\"currency\":\"EUR\","
                + "\"mandate_id\":\"123456\","
                + "\"mandate_type\":\"B2B\","
                + "\"date_of_signature\":\"2022-08-02\","
                + "\"account_holder\":{"
                + "  \"first_name\":\"John\","
                + "  \"last_name\":\"Wick\","
                + "  \"type\":\"Corporate\","
                + "  \"company_name\":\"Checkout.com\","
                + "  \"billing_address\":{"
                + "    \"address_line1\":\"Evergreen Terrace\","
                + "    \"address_line2\":\"742\","
                + "    \"city\":\"Paris\","
                + "    \"zip\":\"75000\","
                + "    \"country\":\"FR\""
                + "  }"
                + "}"
                + "}";

        final RequestSepaSource source = serializer.fromJson(swaggerJson, RequestSepaSource.class);

        assertNotNull(source);
        assertEquals(CountryCode.FR, source.getCountry());
        assertEquals("FR7630006000011234567890189", source.getAccountNumber());
        assertEquals(MandateType.B2B, source.getMandateType());
        assertNotNull(source.getAccountHolder());
        assertEquals("John", source.getAccountHolder().getFirstName());
    }
}
