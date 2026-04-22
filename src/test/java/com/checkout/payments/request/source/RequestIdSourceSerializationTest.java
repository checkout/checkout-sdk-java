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

class RequestIdSourceSerializationTest {

    private final GsonSerializer serializer = new GsonSerializer();

    @Test
    void shouldSerializeWithRequiredId() {
        final RequestIdSource source = RequestIdSource.builder()
                .id("src_wmlfc3zyhqzehihu7giusaaawu")
                .build();

        final String json = serializer.toJson(source);

        assertNotNull(json);
        assertTrue(json.contains("\"type\":\"id\""));
        assertTrue(json.contains("\"id\":\"src_wmlfc3zyhqzehihu7giusaaawu\""));
    }

    @Test
    void shouldSerializeWithAllOptionalFields() {
        final RequestIdSource source = RequestIdSource.builder()
                .id("src_wmlfc3zyhqzehihu7giusaaawu")
                .cvv("100")
                .paymentMethod("visa")
                .stored(true)
                .storeForFutureUse(false)
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
        final RequestIdSource source = RequestIdSource.builder()
                .id("src_wmlfc3zyhqzehihu7giusaaawu")
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
        final RequestIdSource source = RequestIdSource.builder()
                .id("src_wmlfc3zyhqzehihu7giusaaawu")
                .allowUpdate(true)
                .build();

        final String json = serializer.toJson(source);

        assertTrue(json.contains("\"allow_update\":true"));
    }

    @Test
    void shouldSerializeStoreForFutureUse() {
        final RequestIdSource source = RequestIdSource.builder()
                .id("src_wmlfc3zyhqzehihu7giusaaawu")
                .storeForFutureUse(true)
                .build();

        final String json = serializer.toJson(source);

        assertTrue(json.contains("\"store_for_future_use\":true"));
    }

    @Test
    void shouldDeserializeSwaggerExample() {
        final String swaggerJson = "{"
                + "\"type\":\"id\","
                + "\"id\":\"src_wmlfc3zyhqzehihu7giusaaawu\","
                + "\"cvv\":\"100\","
                + "\"stored\":false,"
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
                + "\"allow_update\":false"
                + "}";

        final RequestIdSource source = serializer.fromJson(swaggerJson, RequestIdSource.class);

        assertNotNull(source);
        assertEquals("src_wmlfc3zyhqzehihu7giusaaawu", source.getId());
        assertEquals("100", source.getCvv());
        assertEquals(false, source.getStored());
        assertNotNull(source.getBillingAddress());
        assertEquals("123 High Street", source.getBillingAddress().getAddressLine1());
        assertEquals(CountryCode.GB, source.getBillingAddress().getCountry());
        assertNotNull(source.getPhone());
        assertEquals("+44", source.getPhone().getCountryCode());
        assertEquals(false, source.getAllowUpdate());
    }

    @Test
    void shouldRoundTripSerialize() {
        final RequestIdSource original = RequestIdSource.builder()
                .id("src_wmlfc3zyhqzehihu7giusaaawu")
                .cvv("100")
                .stored(true)
                .billingAddress(Address.builder()
                        .addressLine1("123 High Street")
                        .country(CountryCode.GB)
                        .build())
                .allowUpdate(true)
                .build();

        final String json = serializer.toJson(original);
        final RequestIdSource deserialized = serializer.fromJson(json, RequestIdSource.class);

        assertNotNull(deserialized);
        assertEquals("src_wmlfc3zyhqzehihu7giusaaawu", deserialized.getId());
        assertEquals("100", deserialized.getCvv());
        assertTrue(deserialized.getStored());
        assertNotNull(deserialized.getBillingAddress());
        assertTrue(deserialized.getAllowUpdate());
    }

    @Test
    void shouldHandleAbsentOptionalFields() {
        final String json = "{\"type\":\"id\",\"id\":\"src_wmlfc3zyhqzehihu7giusaaawu\"}";

        final RequestIdSource source = serializer.fromJson(json, RequestIdSource.class);

        assertNotNull(source);
        assertDoesNotThrow(() -> serializer.toJson(source));
        assertNull(source.getCvv());
        assertNull(source.getBillingAddress());
        assertNull(source.getPhone());
        assertNull(source.getAllowUpdate());
    }
}
