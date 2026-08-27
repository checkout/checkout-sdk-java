package com.checkout.instruments;

import com.checkout.GsonSerializer;
import com.checkout.common.CountryCode;
import com.checkout.common.Currency;
import com.checkout.instruments.create.CreateAchAccountHolder;
import com.checkout.instruments.create.CreateAchInstrumentData;
import com.checkout.instruments.create.CreateCustomerInstrumentRequest;
import com.checkout.instruments.create.CreateInstrumentAchRequest;
import com.checkout.instruments.create.CreateInstrumentAchResponse;
import com.checkout.instruments.get.GetAchInstrumentResponse;
import com.checkout.instruments.update.AchInstrumentAccountType;
import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Schema validation tests for the ACH variants of the instruments endpoints.
 */
class AchInstrumentSerializationTest {

    private static final String FINGERPRINT_PATTERN = "^([a-z0-9]{26})$";

    private final GsonSerializer serializer = new GsonSerializer();

    // ------------------------------------------------------------------
    // StoreAchInstrumentRequest - 13 properties including nested
    // ------------------------------------------------------------------

    @Test
    void shouldSerializeEveryStoreRequestProperty() {
        final String json = serializer.toJson(fullyPopulatedStoreRequest());

        assertTrue(json.contains("\"type\":\"ach\""));
        assertTrue(json.contains("\"account_type\":\"savings\""));
        assertTrue(json.contains("\"account_number\":\"4099999992\""));
        assertTrue(json.contains("\"bank_code\":\"211370545\""));
        assertTrue(json.contains("\"currency\":\"USD\""));
        assertTrue(json.contains("\"country\":\"US\""));
        assertTrue(json.contains("\"first_name\":\"John\""));
        assertTrue(json.contains("\"last_name\":\"Smith\""));
        assertTrue(json.contains("\"company_name\":\"Smith Enterprises\""));
        assertTrue(json.contains("\"email\":\"customer@example.com\""));
        assertTrue(json.contains("\"name\":\"John Smith\""));
        assertTrue(json.contains("\"id\":\"cus_udst2tfldj6upmye2reztkmm4i\""));
        assertTrue(json.contains("\"default\":true"));
    }

    @Test
    void shouldRoundTripEveryStoreRequestProperty() {
        final CreateInstrumentAchRequest result = serializer.fromJson(
                serializer.toJson(fullyPopulatedStoreRequest()), CreateInstrumentAchRequest.class);

        assertEquals(com.checkout.common.InstrumentType.ACH, result.getType());
        assertEquals(AchInstrumentAccountType.SAVINGS, result.getInstrumentData().getAccountType());
        assertEquals("4099999992", result.getInstrumentData().getAccountNumber());
        assertEquals("211370545", result.getInstrumentData().getBankCode());
        assertEquals(Currency.USD, result.getInstrumentData().getCurrency());
        assertEquals(CountryCode.US, result.getInstrumentData().getCountry());
        assertEquals("John", result.getAccountHolder().getFirstName());
        assertEquals("Smith", result.getAccountHolder().getLastName());
        assertEquals("Smith Enterprises", result.getAccountHolder().getCompanyName());
        assertEquals(InstrumentAccountHolderType.CORPORATE, result.getAccountHolder().getType());
        assertEquals("customer@example.com", result.getCustomer().getEmail());
        assertEquals("John Smith", result.getCustomer().getName());
        assertEquals("cus_udst2tfldj6upmye2reztkmm4i", result.getCustomer().getId());
        assertTrue(result.getCustomer().isDefaultInstrument());
    }

    @Test
    void shouldDeserializeStoreRequestSwaggerExample() {
        final String json = "{"
                + "\"type\":\"ach\","
                + "\"instrument_data\":{"
                + "\"account_type\":\"savings\","
                + "\"account_number\":\"4099999992\","
                + "\"bank_code\":\"211370545\","
                + "\"currency\":\"USD\","
                + "\"country\":\"US\""
                + "},"
                + "\"account_holder\":{"
                + "\"first_name\":\"John\","
                + "\"last_name\":\"Smith\","
                + "\"company_name\":\"Smith Enterprises\","
                + "\"type\":\"individual\""
                + "}}";

        final CreateInstrumentAchRequest request =
                serializer.fromJson(json, CreateInstrumentAchRequest.class);

        assertNotNull(request);
        assertEquals(AchInstrumentAccountType.SAVINGS, request.getInstrumentData().getAccountType());
        assertEquals(InstrumentAccountHolderType.INDIVIDUAL, request.getAccountHolder().getType());
        assertNull(request.getCustomer());
    }

    @Test
    void shouldOmitAbsentOptionalStoreRequestProperties() {
        final CreateInstrumentAchRequest request = CreateInstrumentAchRequest.builder()
                .instrumentData(CreateAchInstrumentData.builder()
                        .accountType(AchInstrumentAccountType.CHECKING)
                        .accountNumber("4099999992")
                        .bankCode("211370545")
                        .currency(Currency.USD)
                        .country(CountryCode.US)
                        .build())
                .accountHolder(CreateAchAccountHolder.builder()
                        .firstName("John")
                        .lastName("Smith")
                        .companyName("Smith Enterprises")
                        .type(InstrumentAccountHolderType.INDIVIDUAL)
                        .build())
                .build();

        final String json = serializer.toJson(request);

        assertFalse(json.contains("\"customer\""));
        assertTrue(json.contains("\"account_type\":\"checking\""));
    }

    // ------------------------------------------------------------------
    // RetrieveAchInstrumentResponse - 15 properties including nested
    // ------------------------------------------------------------------

    @Test
    void shouldDeserializeEveryRetrieveResponseProperty() {
        final GetAchInstrumentResponse response =
                serializer.fromJson(retrieveResponseJson(), GetAchInstrumentResponse.class);

        assertEquals(com.checkout.common.InstrumentType.ACH, response.getType());
        assertEquals("src_wmlfc3zyhqzehihu7giusaaawu", response.getId());
        assertEquals("vnsdrvikkvre3dtrjjvlm5du4q", response.getFingerprint());
        assertTrue(response.getFingerprint().matches(FINGERPRINT_PATTERN));
        assertEquals(Instant.parse("2021-01-01T00:00:00Z"), response.getCreatedOn());
        assertEquals(Instant.parse("2021-01-02T00:00:00Z"), response.getModifiedOn());
        assertEquals("vid_wmlfc3zyhqzehihu7giusaaawu", response.getVaultId());

        assertEquals(AchInstrumentAccountType.CHECKING, response.getInstrumentData().getAccountType());
        assertEquals("4099999992", response.getInstrumentData().getAccountNumber());
        assertEquals("211370545", response.getInstrumentData().getBankCode());
        assertEquals(Currency.USD, response.getInstrumentData().getCurrency());
        assertEquals(CountryCode.US, response.getInstrumentData().getCountry());

        assertEquals("John", response.getAccountHolder().getFirstName());
        assertEquals("Smith", response.getAccountHolder().getLastName());
        assertEquals("Smith Enterprises", response.getAccountHolder().getCompanyName());
        assertEquals(InstrumentAccountHolderType.CORPORATE, response.getAccountHolder().getType());

        assertEquals("cus_udst2tfldj6upmye2reztkmm4i", response.getCustomer().getId());
        assertEquals("customer@example.com", response.getCustomer().getEmail());
        assertTrue(response.getCustomer().isDefault());
    }

    @Test
    void shouldRoundTripRetrieveResponse() {
        final GetAchInstrumentResponse original =
                serializer.fromJson(retrieveResponseJson(), GetAchInstrumentResponse.class);

        final GetAchInstrumentResponse result = serializer.fromJson(
                serializer.toJson(original), GetAchInstrumentResponse.class);

        assertEquals(original.getVaultId(), result.getVaultId());
        assertEquals(original.getInstrumentData().getAccountType(),
                result.getInstrumentData().getAccountType());
        assertEquals(original.getAccountHolder().getCompanyName(),
                result.getAccountHolder().getCompanyName());
    }

    // ------------------------------------------------------------------
    // Store response
    // ------------------------------------------------------------------

    @Test
    void shouldDeserializeStoreResponse() {
        final String json = "{"
                + "\"type\":\"ach\","
                + "\"id\":\"src_wmlfc3zyhqzehihu7giusaaawu\","
                + "\"fingerprint\":\"vnsdrvikkvre3dtrjjvlm5du4q\""
                + "}";

        final CreateInstrumentAchResponse response =
                serializer.fromJson(json, CreateInstrumentAchResponse.class);

        assertEquals(com.checkout.common.InstrumentType.ACH, response.getType());
        assertEquals("src_wmlfc3zyhqzehihu7giusaaawu", response.getId());
        assertEquals("vnsdrvikkvre3dtrjjvlm5du4q", response.getFingerprint());
        assertTrue(response.getFingerprint().matches(FINGERPRINT_PATTERN));
    }

    @Test
    void shouldSerializeAchAccountTypeBothDirections() {
        assertEquals(2, AchInstrumentAccountType.values().length);
        assertEquals("\"savings\"", serializer.toJson(AchInstrumentAccountType.SAVINGS));
        assertEquals("\"checking\"", serializer.toJson(AchInstrumentAccountType.CHECKING));
        assertEquals(AchInstrumentAccountType.SAVINGS,
                serializer.fromJson("\"savings\"", AchInstrumentAccountType.class));
        assertEquals(AchInstrumentAccountType.CHECKING,
                serializer.fromJson("\"checking\"", AchInstrumentAccountType.class));
    }

    private CreateInstrumentAchRequest fullyPopulatedStoreRequest() {
        return CreateInstrumentAchRequest.builder()
                .instrumentData(CreateAchInstrumentData.builder()
                        .accountType(AchInstrumentAccountType.SAVINGS)
                        .accountNumber("4099999992")
                        .bankCode("211370545")
                        .currency(Currency.USD)
                        .country(CountryCode.US)
                        .build())
                .accountHolder(CreateAchAccountHolder.builder()
                        .firstName("John")
                        .lastName("Smith")
                        .companyName("Smith Enterprises")
                        .type(InstrumentAccountHolderType.CORPORATE)
                        .build())
                .customer(CreateCustomerInstrumentRequest.builder()
                        .id("cus_udst2tfldj6upmye2reztkmm4i")
                        .email("customer@example.com")
                        .name("John Smith")
                        .defaultInstrument(true)
                        .build())
                .build();
    }

    private String retrieveResponseJson() {
        return "{"
                + "\"type\":\"ach\","
                + "\"id\":\"src_wmlfc3zyhqzehihu7giusaaawu\","
                + "\"fingerprint\":\"vnsdrvikkvre3dtrjjvlm5du4q\","
                + "\"created_on\":\"2021-01-01T00:00:00Z\","
                + "\"modified_on\":\"2021-01-02T00:00:00Z\","
                + "\"vault_id\":\"vid_wmlfc3zyhqzehihu7giusaaawu\","
                + "\"instrument_data\":{"
                + "\"account_type\":\"checking\","
                + "\"account_number\":\"4099999992\","
                + "\"bank_code\":\"211370545\","
                + "\"currency\":\"USD\","
                + "\"country\":\"US\""
                + "},"
                + "\"account_holder\":{"
                + "\"first_name\":\"John\","
                + "\"last_name\":\"Smith\","
                + "\"company_name\":\"Smith Enterprises\","
                + "\"type\":\"corporate\""
                + "},"
                + "\"customer\":{"
                + "\"id\":\"cus_udst2tfldj6upmye2reztkmm4i\","
                + "\"email\":\"customer@example.com\","
                + "\"default\":true"
                + "}}";
    }
}
