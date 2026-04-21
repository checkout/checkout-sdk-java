package com.checkout.instruments;

import com.checkout.GsonSerializer;
import com.checkout.common.AccountHolder;
import com.checkout.common.Address;
import com.checkout.common.CountryCode;
import com.checkout.common.Currency;
import com.checkout.instruments.create.CreateCustomerInstrumentRequest;
import com.checkout.instruments.create.CreateInstrumentSepaRequest;
import com.checkout.instruments.create.InstrumentData;
import com.checkout.payments.PaymentType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CreateInstrumentSepaRequestSerializationTest {

    private final GsonSerializer serializer = new GsonSerializer();

    @Test
    void shouldSerializeTypeAsSepa() {
        final CreateInstrumentSepaRequest request = CreateInstrumentSepaRequest.builder()
                .instrumentData(InstrumentData.builder()
                        .accoountNumber("FR7630006000011234567890189")
                        .country(CountryCode.FR)
                        .currency(Currency.EUR)
                        .build())
                .accountHolder(AccountHolder.builder()
                        .firstName("Jane")
                        .lastName("Smith")
                        .build())
                .build();

        final String json = serializer.toJson(request);

        assertNotNull(json);
        assertTrue(json.contains("\"type\":\"sepa\""));
    }

    @Test
    void shouldSerializeInstrumentData() {
        final CreateInstrumentSepaRequest request = CreateInstrumentSepaRequest.builder()
                .instrumentData(InstrumentData.builder()
                        .accoountNumber("DE89370400440532013000")
                        .country(CountryCode.DE)
                        .currency(Currency.EUR)
                        .paymentType(PaymentType.RECURRING)
                        .build())
                .accountHolder(AccountHolder.builder()
                        .firstName("Hans")
                        .lastName("Muller")
                        .build())
                .build();

        final String json = serializer.toJson(request);

        assertTrue(json.contains("\"instrument_data\""));
        assertTrue(json.contains("\"account_number\":\"DE89370400440532013000\""));
        assertTrue(json.contains("\"country\":\"DE\""));
        assertTrue(json.contains("\"currency\":\"EUR\""));
    }

    @Test
    void shouldSerializeAccountHolder() {
        final CreateInstrumentSepaRequest request = CreateInstrumentSepaRequest.builder()
                .instrumentData(InstrumentData.builder().build())
                .accountHolder(AccountHolder.builder()
                        .firstName("Marie")
                        .lastName("Dupont")
                        .billingAddress(Address.builder()
                                .addressLine1("10 Rue de la Paix")
                                .city("Paris")
                                .country(CountryCode.FR)
                                .build())
                        .build())
                .build();

        final String json = serializer.toJson(request);

        assertTrue(json.contains("\"account_holder\""));
        assertTrue(json.contains("\"first_name\":\"Marie\""));
        assertTrue(json.contains("\"last_name\":\"Dupont\""));
        assertTrue(json.contains("\"billing_address\""));
    }

    @Test
    void shouldSerializeOptionalCustomer() {
        final CreateInstrumentSepaRequest request = CreateInstrumentSepaRequest.builder()
                .instrumentData(InstrumentData.builder().build())
                .accountHolder(AccountHolder.builder().build())
                .customer(CreateCustomerInstrumentRequest.builder()
                        .email("customer@example.com")
                        .name("Jane Smith")
                        .build())
                .build();

        final String json = serializer.toJson(request);

        assertTrue(json.contains("\"customer\""));
        assertTrue(json.contains("\"email\":\"customer@example.com\""));
        assertTrue(json.contains("\"name\":\"Jane Smith\""));
    }

    @Test
    void shouldDeserializeInstrumentData() {
        final String json = "{"
                + "\"type\":\"sepa\","
                + "\"instrument_data\":{"
                + "  \"account_number\":\"FR7630006000011234567890189\","
                + "  \"country\":\"FR\","
                + "  \"currency\":\"EUR\""
                + "},"
                + "\"account_holder\":{"
                + "  \"first_name\":\"Jean\","
                + "  \"last_name\":\"Martin\""
                + "}"
                + "}";

        final CreateInstrumentSepaRequest request = serializer.fromJson(json, CreateInstrumentSepaRequest.class);

        assertNotNull(request);
        assertNotNull(request.getInstrumentData());
        assertEquals("FR7630006000011234567890189", request.getInstrumentData().getAccoountNumber());
        assertEquals(CountryCode.FR, request.getInstrumentData().getCountry());
        assertEquals(Currency.EUR, request.getInstrumentData().getCurrency());
        assertNotNull(request.getAccountHolder());
        assertEquals("Jean", request.getAccountHolder().getFirstName());
        assertEquals("Martin", request.getAccountHolder().getLastName());
    }

    @Test
    void shouldRoundTripSerialize() {
        final CreateInstrumentSepaRequest original = CreateInstrumentSepaRequest.builder()
                .instrumentData(InstrumentData.builder()
                        .accoountNumber("ES9121000418450200051332")
                        .country(CountryCode.ES)
                        .currency(Currency.EUR)
                        .paymentType(PaymentType.RECURRING)
                        .build())
                .accountHolder(AccountHolder.builder()
                        .firstName("Carlos")
                        .lastName("García")
                        .billingAddress(Address.builder()
                                .addressLine1("Calle Mayor 1")
                                .city("Madrid")
                                .country(CountryCode.ES)
                                .build())
                        .build())
                .build();

        final String json = serializer.toJson(original);
        final CreateInstrumentSepaRequest deserialized = serializer.fromJson(json, CreateInstrumentSepaRequest.class);

        assertNotNull(deserialized);
        assertNotNull(deserialized.getInstrumentData());
        assertEquals("ES9121000418450200051332", deserialized.getInstrumentData().getAccoountNumber());
        assertEquals(CountryCode.ES, deserialized.getInstrumentData().getCountry());
        assertNotNull(deserialized.getAccountHolder());
        assertEquals("Carlos", deserialized.getAccountHolder().getFirstName());
        assertEquals("García", deserialized.getAccountHolder().getLastName());
    }

    @Test
    void shouldNotSerializeNullOptionalCustomer() {
        final CreateInstrumentSepaRequest request = CreateInstrumentSepaRequest.builder()
                .instrumentData(InstrumentData.builder().build())
                .accountHolder(AccountHolder.builder().build())
                .build();

        final String json = serializer.toJson(request);

        assertFalse(json.contains("\"customer\""));
    }

    @Test
    void shouldHandleAbsentOptionalCustomer() {
        final String json = "{"
                + "\"type\":\"sepa\","
                + "\"instrument_data\":{},"
                + "\"account_holder\":{}"
                + "}";

        final CreateInstrumentSepaRequest request = serializer.fromJson(json, CreateInstrumentSepaRequest.class);

        assertNotNull(request);
        assertDoesNotThrow(() -> serializer.toJson(request));
        assertNull(request.getCustomer());
    }
}
