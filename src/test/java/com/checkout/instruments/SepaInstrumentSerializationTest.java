package com.checkout.instruments;

import com.checkout.instruments.create.CreateSepaAccountHolder;
import com.checkout.instruments.create.CreateSepaBillingAddress;
import com.checkout.GsonSerializer;
import com.checkout.common.CountryCode;
import com.checkout.common.Currency;
import com.checkout.instruments.create.CreateCustomerInstrumentRequest;
import com.checkout.instruments.create.CreateInstrumentSepaRequest;
import com.checkout.instruments.create.InstrumentData;
import com.checkout.instruments.update.SepaPaymentType;
import com.checkout.instruments.update.UpdateInstrumentSepaResponse;
import com.checkout.payments.request.source.apm.MandateType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Schema validation tests for the SEPA variants of the instruments endpoints.
 */
class SepaInstrumentSerializationTest {

    private static final String FINGERPRINT_PATTERN = "^([a-z0-9]{26})$";

    private final GsonSerializer serializer = new GsonSerializer();

    // ------------------------------------------------------------------
    // StoreSepaInstrumentRequest
    // ------------------------------------------------------------------

    @Test
    void shouldSerializeTypeAsSepa() {
        final CreateInstrumentSepaRequest request = CreateInstrumentSepaRequest.builder()
                .instrumentData(InstrumentData.builder()
                        .accoountNumber("FR7630006000011234567890189")
                        .country(CountryCode.FR)
                        .currency(Currency.EUR)
                        .build())
                .accountHolder(CreateSepaAccountHolder.builder()
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
                        .paymentType(SepaPaymentType.RECURRING)
                        .build())
                .accountHolder(CreateSepaAccountHolder.builder()
                        .firstName("Hans")
                        .lastName("Muller")
                        .build())
                .build();

        final String json = serializer.toJson(request);

        assertTrue(json.contains("\"instrument_data\""));
        assertTrue(json.contains("\"account_number\":\"DE89370400440532013000\""));
        assertTrue(json.contains("\"country\":\"DE\""));
        assertTrue(json.contains("\"currency\":\"EUR\""));
        assertTrue(json.contains("\"payment_type\":\"recurring\""));
    }

    /**
     * The store request once typed paymentType as the generic payments payment type, whose
     * constants serialize capitalized, so a SEPA instrument could not be created at all: the
     * specification pins this field to recurring or regular in lowercase. No test asserted the wire
     * value, which is why the defect survived.
     */
    @Test
    void shouldSerializePaymentTypeLowercaseOnTheStoreRequest() {
        assertTrue(serializer.toJson(storeRequestWithPaymentType(SepaPaymentType.RECURRING))
                .contains("\"payment_type\":\"recurring\""));
        assertTrue(serializer.toJson(storeRequestWithPaymentType(SepaPaymentType.REGULAR))
                .contains("\"payment_type\":\"regular\""));
    }

    @Test
    void shouldSerializeMandateType() {
        final CreateInstrumentSepaRequest request = CreateInstrumentSepaRequest.builder()
                .instrumentData(InstrumentData.builder()
                        .type(MandateType.B2B)
                        .accoountNumber("DE89370400440532013000")
                        .country(CountryCode.DE)
                        .currency(Currency.EUR)
                        .paymentType(SepaPaymentType.REGULAR)
                        .build())
                .build();

        final String json = serializer.toJson(request);
        final CreateInstrumentSepaRequest result =
                serializer.fromJson(json, CreateInstrumentSepaRequest.class);

        assertTrue(json.contains("\"type\":\"B2B\""));
        assertEquals(MandateType.B2B, result.getInstrumentData().getType());
    }

    private CreateInstrumentSepaRequest storeRequestWithPaymentType(final SepaPaymentType paymentType) {
        return CreateInstrumentSepaRequest.builder()
                .instrumentData(InstrumentData.builder()
                        .accoountNumber("DE89370400440532013000")
                        .country(CountryCode.DE)
                        .currency(Currency.EUR)
                        .paymentType(paymentType)
                        .build())
                .build();
    }

    @Test
    void shouldSerializeAccountHolder() {
        final CreateInstrumentSepaRequest request = CreateInstrumentSepaRequest.builder()
                .instrumentData(InstrumentData.builder().build())
                .accountHolder(CreateSepaAccountHolder.builder()
                        .firstName("Marie")
                        .lastName("Dupont")
                        .billingAddress(CreateSepaBillingAddress.builder()
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
                .accountHolder(CreateSepaAccountHolder.builder().build())
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
                        .paymentType(SepaPaymentType.RECURRING)
                        .build())
                .accountHolder(CreateSepaAccountHolder.builder()
                        .firstName("Carlos")
                        .lastName("García")
                        .billingAddress(CreateSepaBillingAddress.builder()
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
                .accountHolder(CreateSepaAccountHolder.builder().build())
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

    // ------------------------------------------------------------------
    // Update response
    // ------------------------------------------------------------------

    @Test
    void shouldDeserializeUpdateResponse() {
        final String json = "{"
                + "\"type\":\"sepa\","
                + "\"id\":\"src_wmlfc3zyhqzehihu7giusaaawu\","
                + "\"fingerprint\":\"vnsdrvikkvre3dtrjjvlm5du4q\""
                + "}";

        final UpdateInstrumentSepaResponse response =
                serializer.fromJson(json, UpdateInstrumentSepaResponse.class);

        assertEquals(com.checkout.common.InstrumentType.SEPA, response.getType());
        assertEquals("src_wmlfc3zyhqzehihu7giusaaawu", response.getId());
        assertEquals("vnsdrvikkvre3dtrjjvlm5du4q", response.getFingerprint());
        assertTrue(response.getFingerprint().matches(FINGERPRINT_PATTERN));
    }
}
