package com.checkout.instruments;

import com.checkout.GsonSerializer;
import com.checkout.common.CountryCode;
import com.checkout.common.Currency;
import com.checkout.instruments.create.CreateBacsAccountHolder;
import com.checkout.instruments.create.CreateBacsBillingAddress;
import com.checkout.instruments.create.CreateBacsInstrumentAccount;
import com.checkout.instruments.create.CreateBacsInstrumentData;
import com.checkout.instruments.create.CreateCustomerInstrumentRequest;
import com.checkout.instruments.create.CreateInstrumentBacsRequest;
import com.checkout.instruments.create.CreateInstrumentBacsResponse;
import com.checkout.instruments.get.GetBacsInstrumentResponse;
import com.checkout.instruments.update.SepaPaymentType;
import com.checkout.instruments.update.UpdateBacsAccountHolder;
import com.checkout.instruments.update.UpdateBacsBillingAddress;
import com.checkout.instruments.update.UpdateBacsInstrumentData;
import com.checkout.instruments.update.UpdateInstrumentBacsRequest;
import com.checkout.instruments.update.UpdateInstrumentBacsResponse;
import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Schema validation tests for the Bacs Direct Debit variants of the instruments endpoints.
 */
class BacsInstrumentSerializationTest {

    private static final String FINGERPRINT_PATTERN = "^([a-z0-9]{26})$";

    private final GsonSerializer serializer = new GsonSerializer();

    // ------------------------------------------------------------------
    // StoreBacsInstrumentRequest - 20 properties including nested
    // ------------------------------------------------------------------

    @Test
    void shouldSerializeEveryStoreRequestProperty() {
        final String json = serializer.toJson(fullyPopulatedStoreRequest());

        assertTrue(json.contains("\"type\":\"bacs\""));
        assertTrue(json.contains("\"processing_channel_id\":\"pc_q4dbxom5jbgudnjzjpz7j2z6uq\""));
        assertTrue(json.contains("\"account_number\":\"86753246\""));
        assertTrue(json.contains("\"bank_code\":\"040004\""));
        assertTrue(json.contains("\"currency\":\"GBP\""));
        assertTrue(json.contains("\"payment_type\":\"Recurring\""));
        assertTrue(json.contains("\"allow_partial_match\":true"));
        assertTrue(json.contains("\"first_name\":\"John\""));
        assertTrue(json.contains("\"last_name\":\"Smith\""));
        assertTrue(json.contains("\"address_line1\":\"Cloverfield St.\""));
        assertTrue(json.contains("\"address_line2\":\"23A\""));
        assertTrue(json.contains("\"city\":\"London\""));
        assertTrue(json.contains("\"zip\":\"SW1A 1AA\""));
        assertTrue(json.contains("\"country\":\"GB\""));
        assertTrue(json.contains("\"email\":\"customer@example.com\""));
        assertTrue(json.contains("\"name\":\"John Smith\""));
        assertTrue(json.contains("\"id\":\"cus_udst2tfldj6upmye2reztkmm4i\""));
        assertTrue(json.contains("\"default\":true"));
    }

    @Test
    void shouldRoundTripEveryStoreRequestProperty() {
        final CreateInstrumentBacsRequest original = fullyPopulatedStoreRequest();

        final CreateInstrumentBacsRequest result =
                serializer.fromJson(serializer.toJson(original), CreateInstrumentBacsRequest.class);

        assertEquals(com.checkout.common.InstrumentType.BACS, result.getType());
        assertEquals("pc_q4dbxom5jbgudnjzjpz7j2z6uq", result.getAccount().getProcessingChannelId());
        assertEquals("86753246", result.getInstrumentData().getAccountNumber());
        assertEquals("040004", result.getInstrumentData().getBankCode());
        assertEquals(CountryCode.GB, result.getInstrumentData().getCountry());
        assertEquals(Currency.GBP, result.getInstrumentData().getCurrency());
        assertEquals(BacsPaymentType.RECURRING, result.getInstrumentData().getPaymentType());
        assertTrue(result.getInstrumentData().getAllowPartialMatch());
        assertEquals("John", result.getAccountHolder().getFirstName());
        assertEquals("Smith", result.getAccountHolder().getLastName());
        assertEquals("Cloverfield St.", result.getAccountHolder().getBillingAddress().getAddressLine1());
        assertEquals("23A", result.getAccountHolder().getBillingAddress().getAddressLine2());
        assertEquals("London", result.getAccountHolder().getBillingAddress().getCity());
        assertEquals("SW1A 1AA", result.getAccountHolder().getBillingAddress().getZip());
        assertEquals(CountryCode.GB, result.getAccountHolder().getBillingAddress().getCountry());
        assertEquals("customer@example.com", result.getCustomer().getEmail());
        assertEquals("John Smith", result.getCustomer().getName());
        assertEquals("cus_udst2tfldj6upmye2reztkmm4i", result.getCustomer().getId());
        assertTrue(result.getCustomer().isDefaultInstrument());
    }

    @Test
    void shouldDeserializeStoreRequestSwaggerExample() {
        final String json = "{"
                + "\"type\":\"bacs\","
                + "\"account\":{\"processing_channel_id\":\"pc_q4dbxom5jbgudnjzjpz7j2z6uq\"},"
                + "\"instrument_data\":{"
                + "\"account_number\":\"86753246\","
                + "\"bank_code\":\"040004\","
                + "\"country\":\"GB\","
                + "\"currency\":\"GBP\","
                + "\"payment_type\":\"Recurring\""
                + "},"
                + "\"account_holder\":{"
                + "\"first_name\":\"John\","
                + "\"last_name\":\"Smith\","
                + "\"billing_address\":{"
                + "\"address_line1\":\"Cloverfield St.\","
                + "\"address_line2\":\"23A\","
                + "\"city\":\"London\","
                + "\"zip\":\"SW1A 1AA\","
                + "\"country\":\"GB\""
                + "}}}";

        final CreateInstrumentBacsRequest request =
                serializer.fromJson(json, CreateInstrumentBacsRequest.class);

        assertNotNull(request);
        assertEquals(BacsPaymentType.RECURRING, request.getInstrumentData().getPaymentType());
        assertNull(request.getInstrumentData().getAllowPartialMatch());
        assertNull(request.getCustomer());
    }

    @Test
    void shouldOmitAbsentOptionalStoreRequestProperties() {
        final CreateInstrumentBacsRequest request = CreateInstrumentBacsRequest.builder()
                .account(CreateBacsInstrumentAccount.builder()
                        .processingChannelId("pc_q4dbxom5jbgudnjzjpz7j2z6uq")
                        .build())
                .instrumentData(CreateBacsInstrumentData.builder()
                        .accountNumber("86753246")
                        .bankCode("040004")
                        .country(CountryCode.GB)
                        .currency(Currency.GBP)
                        .paymentType(BacsPaymentType.REGULAR)
                        .build())
                .accountHolder(CreateBacsAccountHolder.builder()
                        .firstName("John")
                        .lastName("Smith")
                        .billingAddress(CreateBacsBillingAddress.builder()
                                .country(CountryCode.GB)
                                .build())
                        .build())
                .build();

        final String json = serializer.toJson(request);

        assertFalse(json.contains("allow_partial_match"));
        assertFalse(json.contains("\"customer\""));
        assertTrue(json.contains("\"payment_type\":\"Regular\""));
    }

    // ------------------------------------------------------------------
    // UpdateBacsInstrumentRequest - 19 properties including nested
    // ------------------------------------------------------------------

    @Test
    void shouldSerializeEveryUpdateRequestProperty() {
        final String json = serializer.toJson(fullyPopulatedUpdateRequest());

        assertTrue(json.contains("\"type\":\"bacs\""));
        assertTrue(json.contains("\"account_number\":\"86753246\""));
        assertTrue(json.contains("\"bank_code\":\"040004\""));
        assertTrue(json.contains("\"payment_type\":\"Regular\""));
        assertTrue(json.contains("\"allow_partial_match\":true"));
        assertTrue(json.contains("\"first_name\":\"Hannah\""));
        assertTrue(json.contains("\"last_name\":\"Bret\""));
        assertTrue(json.contains("\"company_name\":\"Bret Holdings Ltd\""));
        assertTrue(json.contains("\"address_line1\":\"123 High St.\""));
        assertTrue(json.contains("\"address_line2\":\"Flat 456\""));
        assertTrue(json.contains("\"city\":\"London\""));
        assertTrue(json.contains("\"zip\":\"SW1A 1AA\""));
        assertTrue(json.contains("\"country\":\"GB\""));
        assertTrue(json.contains("\"type\":\"corporate\""));
    }

    @Test
    void shouldRoundTripEveryUpdateRequestProperty() {
        final UpdateInstrumentBacsRequest result = serializer.fromJson(
                serializer.toJson(fullyPopulatedUpdateRequest()), UpdateInstrumentBacsRequest.class);

        assertEquals(com.checkout.common.InstrumentType.BACS, result.getType());
        assertEquals("86753246", result.getInstrumentData().getAccountNumber());
        assertEquals("040004", result.getInstrumentData().getBankCode());
        assertEquals(CountryCode.GB, result.getInstrumentData().getCountry());
        assertEquals(Currency.GBP, result.getInstrumentData().getCurrency());
        assertEquals(BacsPaymentType.REGULAR, result.getInstrumentData().getPaymentType());
        assertTrue(result.getInstrumentData().getAllowPartialMatch());
        assertEquals("Hannah", result.getAccountHolder().getFirstName());
        assertEquals("Bret", result.getAccountHolder().getLastName());
        assertEquals("Bret Holdings Ltd", result.getAccountHolder().getCompanyName());
        assertEquals(InstrumentAccountHolderType.CORPORATE, result.getAccountHolder().getType());
        assertEquals("123 High St.", result.getAccountHolder().getBillingAddress().getAddressLine1());
        assertEquals("Flat 456", result.getAccountHolder().getBillingAddress().getAddressLine2());
        assertEquals("London", result.getAccountHolder().getBillingAddress().getCity());
        assertEquals("SW1A 1AA", result.getAccountHolder().getBillingAddress().getZip());
        assertEquals(CountryCode.GB, result.getAccountHolder().getBillingAddress().getCountry());
    }

    @Test
    void shouldSerializeEmptyUpdateRequestWithTypeOnly() {
        final String json = serializer.toJson(new UpdateInstrumentBacsRequest());

        assertEquals("{\"type\":\"bacs\"}", json);
    }

    // ------------------------------------------------------------------
    // RetrieveBacsInstrumentResponse - 31 properties including nested
    // ------------------------------------------------------------------

    @Test
    void shouldDeserializeEveryRetrieveResponseProperty() {
        final GetBacsInstrumentResponse response =
                serializer.fromJson(retrieveResponseJson(), GetBacsInstrumentResponse.class);

        assertEquals(com.checkout.common.InstrumentType.BACS, response.getType());
        assertEquals("src_wmlfc3zyhqzehihu7giusaaawu", response.getId());
        assertEquals("vnsdrvikkvre3dtrjjvlm5du4q", response.getFingerprint());
        assertTrue(response.getFingerprint().matches(FINGERPRINT_PATTERN));
        assertEquals(Instant.parse("2021-01-01T00:00:00Z"), response.getCreatedOn());
        assertEquals(Instant.parse("2021-01-02T00:00:00Z"), response.getModifiedOn());
        assertEquals("vid_wmlfc3zyhqzehihu7giusaaawu", response.getVaultId());

        assertEquals("cli_memowvltf7aulpb3poehtiffei", response.getAccount().getClientId());
        assertEquals("pc_jcs4ufa6hrgepcrvhic4bfspay", response.getAccount().getProcessingChannelId());

        assertEquals(1, response.getValidations().size());
        assertEquals("account_number", response.getValidations().get(0).get("field"));

        assertEquals("86753246", response.getInstrumentData().getAccountNumber());
        assertEquals("040004", response.getInstrumentData().getBankCode());
        assertEquals(CountryCode.GB, response.getInstrumentData().getCountry());
        assertEquals(Currency.GBP, response.getInstrumentData().getCurrency());
        assertEquals(BacsPaymentType.RECURRING, response.getInstrumentData().getPaymentType());
        assertTrue(response.getInstrumentData().getAllowPartialMatch());
        assertEquals("INVALID", response.getInstrumentData().getStatus());
        assertEquals("no match", response.getInstrumentData().getMatchStatus());
        assertEquals("The name did not match with the account owner.",
                response.getInstrumentData().getDescription());
        assertEquals("6PZ6KFI3KW3UFHAM3J", response.getInstrumentData().getMandateId());

        assertEquals("Hannah", response.getAccountHolder().getFirstName());
        assertEquals("Bret", response.getAccountHolder().getLastName());
        assertEquals("Bret Holdings Ltd", response.getAccountHolder().getCompanyName());
        assertEquals(InstrumentAccountHolderType.CORPORATE, response.getAccountHolder().getType());
        assertEquals("123 High St.", response.getAccountHolder().getBillingAddress().getAddressLine1());
        assertEquals("Flat 456", response.getAccountHolder().getBillingAddress().getAddressLine2());
        assertEquals("London", response.getAccountHolder().getBillingAddress().getCity());
        assertEquals("SW1A 1AA", response.getAccountHolder().getBillingAddress().getZip());
        assertEquals(CountryCode.GB, response.getAccountHolder().getBillingAddress().getCountry());

        assertEquals("cus_udst2tfldj6upmye2reztkmm4i", response.getCustomer().getId());
        assertEquals("customer@example.com", response.getCustomer().getEmail());
        assertTrue(response.getCustomer().isDefault());
    }

    @Test
    void shouldRoundTripRetrieveResponseValidations() {
        final GetBacsInstrumentResponse original =
                serializer.fromJson(retrieveResponseJson(), GetBacsInstrumentResponse.class);

        final GetBacsInstrumentResponse result = serializer.fromJson(
                serializer.toJson(original), GetBacsInstrumentResponse.class);

        assertEquals(1, result.getValidations().size());
        assertEquals("no match", result.getValidations().get(0).get("result"));
    }

    // ------------------------------------------------------------------
    // Store and update responses
    // ------------------------------------------------------------------

    @Test
    void shouldDeserializeStoreResponse() {
        final String json = "{"
                + "\"type\":\"bacs\","
                + "\"id\":\"src_wmlfc3zyhqzehihu7giusaaawu\","
                + "\"fingerprint\":\"vnsdrvikkvre3dtrjjvlm5du4q\""
                + "}";

        final CreateInstrumentBacsResponse response =
                serializer.fromJson(json, CreateInstrumentBacsResponse.class);

        assertEquals(com.checkout.common.InstrumentType.BACS, response.getType());
        assertEquals("src_wmlfc3zyhqzehihu7giusaaawu", response.getId());
        assertEquals("vnsdrvikkvre3dtrjjvlm5du4q", response.getFingerprint());
        assertTrue(response.getFingerprint().matches(FINGERPRINT_PATTERN));
    }

    @Test
    void shouldDeserializeUpdateResponse() {
        final String json = "{"
                + "\"type\":\"bacs\","
                + "\"id\":\"src_wmlfc3zyhqzehihu7giusaaawu\","
                + "\"fingerprint\":\"vnsdrvikkvre3dtrjjvlm5du4q\""
                + "}";

        final UpdateInstrumentBacsResponse response =
                serializer.fromJson(json, UpdateInstrumentBacsResponse.class);

        assertEquals(com.checkout.common.InstrumentType.BACS, response.getType());
        assertEquals("src_wmlfc3zyhqzehihu7giusaaawu", response.getId());
        assertEquals("vnsdrvikkvre3dtrjjvlm5du4q", response.getFingerprint());
        assertTrue(response.getFingerprint().matches(FINGERPRINT_PATTERN));
    }

    // ------------------------------------------------------------------
    // BacsPaymentType - the casing regression guard for T1
    // ------------------------------------------------------------------

    @Test
    void shouldSerializeBacsPaymentTypeCapitalized() {
        assertEquals("\"Recurring\"", serializer.toJson(BacsPaymentType.RECURRING));
        assertEquals("\"Regular\"", serializer.toJson(BacsPaymentType.REGULAR));
        assertEquals(BacsPaymentType.RECURRING,
                serializer.fromJson("\"Recurring\"", BacsPaymentType.class));
        assertEquals(BacsPaymentType.REGULAR,
                serializer.fromJson("\"Regular\"", BacsPaymentType.class));
    }

    @Test
    void shouldKeepSepaPaymentTypeLowercase() {
        assertEquals("\"recurring\"", serializer.toJson(SepaPaymentType.RECURRING));
        assertEquals("\"regular\"", serializer.toJson(SepaPaymentType.REGULAR));
        assertEquals(SepaPaymentType.RECURRING,
                serializer.fromJson("\"recurring\"", SepaPaymentType.class));
        assertEquals(SepaPaymentType.REGULAR,
                serializer.fromJson("\"regular\"", SepaPaymentType.class));
    }

    @Test
    void shouldSerializeInstrumentAccountHolderTypeBothDirections() {
        assertEquals("\"individual\"", serializer.toJson(InstrumentAccountHolderType.INDIVIDUAL));
        assertEquals("\"corporate\"", serializer.toJson(InstrumentAccountHolderType.CORPORATE));
        assertEquals(InstrumentAccountHolderType.INDIVIDUAL,
                serializer.fromJson("\"individual\"", InstrumentAccountHolderType.class));
        assertEquals(InstrumentAccountHolderType.CORPORATE,
                serializer.fromJson("\"corporate\"", InstrumentAccountHolderType.class));
    }

    private CreateInstrumentBacsRequest fullyPopulatedStoreRequest() {
        return CreateInstrumentBacsRequest.builder()
                .account(CreateBacsInstrumentAccount.builder()
                        .processingChannelId("pc_q4dbxom5jbgudnjzjpz7j2z6uq")
                        .build())
                .instrumentData(CreateBacsInstrumentData.builder()
                        .accountNumber("86753246")
                        .bankCode("040004")
                        .country(CountryCode.GB)
                        .currency(Currency.GBP)
                        .paymentType(BacsPaymentType.RECURRING)
                        .allowPartialMatch(true)
                        .build())
                .accountHolder(CreateBacsAccountHolder.builder()
                        .firstName("John")
                        .lastName("Smith")
                        .billingAddress(CreateBacsBillingAddress.builder()
                                .addressLine1("Cloverfield St.")
                                .addressLine2("23A")
                                .city("London")
                                .zip("SW1A 1AA")
                                .country(CountryCode.GB)
                                .build())
                        .build())
                .customer(CreateCustomerInstrumentRequest.builder()
                        .id("cus_udst2tfldj6upmye2reztkmm4i")
                        .email("customer@example.com")
                        .name("John Smith")
                        .defaultInstrument(true)
                        .build())
                .build();
    }

    private UpdateInstrumentBacsRequest fullyPopulatedUpdateRequest() {
        return UpdateInstrumentBacsRequest.builder()
                .instrumentData(UpdateBacsInstrumentData.builder()
                        .accountNumber("86753246")
                        .bankCode("040004")
                        .country(CountryCode.GB)
                        .currency(Currency.GBP)
                        .paymentType(BacsPaymentType.REGULAR)
                        .allowPartialMatch(true)
                        .build())
                .accountHolder(UpdateBacsAccountHolder.builder()
                        .firstName("Hannah")
                        .lastName("Bret")
                        .companyName("Bret Holdings Ltd")
                        .type(InstrumentAccountHolderType.CORPORATE)
                        .billingAddress(UpdateBacsBillingAddress.builder()
                                .addressLine1("123 High St.")
                                .addressLine2("Flat 456")
                                .city("London")
                                .zip("SW1A 1AA")
                                .country(CountryCode.GB)
                                .build())
                        .build())
                .build();
    }

    private String retrieveResponseJson() {
        return "{"
                + "\"type\":\"bacs\","
                + "\"id\":\"src_wmlfc3zyhqzehihu7giusaaawu\","
                + "\"fingerprint\":\"vnsdrvikkvre3dtrjjvlm5du4q\","
                + "\"created_on\":\"2021-01-01T00:00:00Z\","
                + "\"modified_on\":\"2021-01-02T00:00:00Z\","
                + "\"vault_id\":\"vid_wmlfc3zyhqzehihu7giusaaawu\","
                + "\"account\":{"
                + "\"client_id\":\"cli_memowvltf7aulpb3poehtiffei\","
                + "\"processing_channel_id\":\"pc_jcs4ufa6hrgepcrvhic4bfspay\""
                + "},"
                + "\"validations\":[{\"field\":\"account_number\",\"result\":\"no match\"}],"
                + "\"instrument_data\":{"
                + "\"account_number\":\"86753246\","
                + "\"bank_code\":\"040004\","
                + "\"country\":\"GB\","
                + "\"currency\":\"GBP\","
                + "\"payment_type\":\"Recurring\","
                + "\"allow_partial_match\":true,"
                + "\"status\":\"INVALID\","
                + "\"match_status\":\"no match\","
                + "\"description\":\"The name did not match with the account owner.\","
                + "\"mandate_id\":\"6PZ6KFI3KW3UFHAM3J\""
                + "},"
                + "\"account_holder\":{"
                + "\"first_name\":\"Hannah\","
                + "\"last_name\":\"Bret\","
                + "\"company_name\":\"Bret Holdings Ltd\","
                + "\"type\":\"corporate\","
                + "\"billing_address\":{"
                + "\"address_line1\":\"123 High St.\","
                + "\"address_line2\":\"Flat 456\","
                + "\"city\":\"London\","
                + "\"zip\":\"SW1A 1AA\","
                + "\"country\":\"GB\""
                + "}},"
                + "\"customer\":{"
                + "\"id\":\"cus_udst2tfldj6upmye2reztkmm4i\","
                + "\"email\":\"customer@example.com\","
                + "\"default\":true"
                + "}}";
    }
}
