package com.checkout.payments.request.source;

import com.checkout.GsonSerializer;
import com.checkout.common.AccountHolder;
import com.checkout.common.Address;
import com.checkout.common.CountryCode;
import com.checkout.common.Phone;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class RequestCustomerSourceSerializationTest {

    private final GsonSerializer serializer = new GsonSerializer();

    @Test
    void shouldSerializeWithCustomerId() {
        final RequestCustomerSource source = RequestCustomerSource.builder()
                .id("cus_y3oqhf46pyzuxjbcn2giaqnb44")
                .build();

        final String json = serializer.toJson(source);

        assertNotNull(json);
        assertTrue(json.contains("\"type\":\"customer\""));
        assertTrue(json.contains("\"id\":\"cus_y3oqhf46pyzuxjbcn2giaqnb44\""));
    }

    @Test
    void shouldSerializeWithAllOptionalFields() {
        final RequestCustomerSource source = RequestCustomerSource.builder()
                .id("cus_y3oqhf46pyzuxjbcn2giaqnb44")
                .billingAddress(Address.builder()
                        .addressLine1("123 High Street")
                        .city("London")
                        .zip("SW1A 1AA")
                        .country(CountryCode.GB)
                        .build())
                .phone(Phone.builder()
                        .countryCode("+44")
                        .number("7911123456")
                        .build())
                .accountHolder(AccountHolder.builder()
                        .firstName("John")
                        .lastName("Smith")
                        .build())
                .allowUpdate(true)
                .build();

        assertDoesNotThrow(() -> serializer.toJson(source));
    }

    @Test
    void shouldSerializeBillingAddress() {
        final RequestCustomerSource source = RequestCustomerSource.builder()
                .id("cus_y3oqhf46pyzuxjbcn2giaqnb44")
                .billingAddress(Address.builder()
                        .addressLine1("123 High Street")
                        .country(CountryCode.GB)
                        .build())
                .build();

        final String json = serializer.toJson(source);

        assertTrue(json.contains("\"billing_address\""));
        assertTrue(json.contains("\"address_line1\":\"123 High Street\""));
    }

    @Test
    void shouldSerializeAllowUpdate() {
        final RequestCustomerSource source = RequestCustomerSource.builder()
                .id("cus_y3oqhf46pyzuxjbcn2giaqnb44")
                .allowUpdate(true)
                .build();

        final String json = serializer.toJson(source);

        assertTrue(json.contains("\"allow_update\":true"));
    }

    @Test
    void shouldDeserializeSwaggerExample() {
        final String swaggerJson = "{"
                + "\"type\":\"customer\","
                + "\"id\":\"cus_y3oqhf46pyzuxjbcn2giaqnb44\","
                + "\"billing_address\":{"
                + "  \"address_line1\":\"123 High Street\","
                + "  \"city\":\"London\","
                + "  \"zip\":\"SW1A 1AA\","
                + "  \"country\":\"GB\""
                + "},"
                + "\"phone\":{"
                + "  \"country_code\":\"+44\","
                + "  \"number\":\"7911123456\""
                + "},"
                + "\"allow_update\":true"
                + "}";

        final RequestCustomerSource source = serializer.fromJson(swaggerJson, RequestCustomerSource.class);

        assertNotNull(source);
        assertEquals("cus_y3oqhf46pyzuxjbcn2giaqnb44", source.getId());
        assertNotNull(source.getBillingAddress());
        assertEquals("123 High Street", source.getBillingAddress().getAddressLine1());
        assertNotNull(source.getPhone());
        assertEquals("+44", source.getPhone().getCountryCode());
        assertTrue(source.getAllowUpdate());
    }

    @Test
    void shouldRoundTripSerialize() {
        final RequestCustomerSource original = RequestCustomerSource.builder()
                .id("cus_y3oqhf46pyzuxjbcn2giaqnb44")
                .billingAddress(Address.builder()
                        .addressLine1("123 High Street")
                        .country(CountryCode.GB)
                        .build())
                .allowUpdate(true)
                .build();

        final String json = serializer.toJson(original);
        final RequestCustomerSource deserialized = serializer.fromJson(json, RequestCustomerSource.class);

        assertNotNull(deserialized);
        assertEquals("cus_y3oqhf46pyzuxjbcn2giaqnb44", deserialized.getId());
        assertNotNull(deserialized.getBillingAddress());
        assertEquals("123 High Street", deserialized.getBillingAddress().getAddressLine1());
        assertTrue(deserialized.getAllowUpdate());
    }

    @Test
    void shouldHandleAbsentOptionalFields() {
        final String json = "{\"type\":\"customer\",\"id\":\"cus_y3oqhf46pyzuxjbcn2giaqnb44\"}";

        final RequestCustomerSource source = serializer.fromJson(json, RequestCustomerSource.class);

        assertNotNull(source);
        assertDoesNotThrow(() -> serializer.toJson(source));
        assertNull(source.getBillingAddress());
        assertNull(source.getPhone());
        assertNull(source.getAccountHolder());
        assertNull(source.getAllowUpdate());
    }
}
