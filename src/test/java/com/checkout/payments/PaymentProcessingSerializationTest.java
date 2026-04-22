package com.checkout.payments;

import com.checkout.GsonSerializer;
import com.checkout.common.CountryCode;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

class PaymentProcessingSerializationTest {

    private final GsonSerializer serializer = new GsonSerializer();

    @Test
    void shouldDeserializeRetrievalReferenceNumber() {
        final String json = "{\"retrieval_reference_number\":\"RRN123456\"}";

        final PaymentProcessing processing = serializer.fromJson(json, PaymentProcessing.class);

        assertNotNull(processing);
        assertEquals("RRN123456", processing.getRetrievalReferenceNumber());
    }

    @Test
    void shouldDeserializeAcquirerFields() {
        final String json = "{"
                + "\"acquirer_transaction_id\":\"TXN001\","
                + "\"acquirer_name\":\"Test Acquirer\","
                + "\"acquirer_country_code\":\"US\""
                + "}";

        final PaymentProcessing processing = serializer.fromJson(json, PaymentProcessing.class);

        assertNotNull(processing);
        assertEquals("TXN001", processing.getAcquirerTransactionId());
        assertEquals("Test Acquirer", processing.getAcquirerName());
        assertEquals(CountryCode.US, processing.getAcquirerCountryCode());
    }

    @Test
    void shouldDeserializePanTypeProcessedFpan() {
        final String json = "{\"pan_type_processed\":\"fpan\"}";

        final PaymentProcessing processing = serializer.fromJson(json, PaymentProcessing.class);

        assertNotNull(processing);
        assertEquals(PanProcessedType.FPAN, processing.getPanTypeProcessed());
    }

    @Test
    void shouldDeserializePanTypeProcessedDpan() {
        final String json = "{\"pan_type_processed\":\"dpan\"}";

        final PaymentProcessing processing = serializer.fromJson(json, PaymentProcessing.class);

        assertNotNull(processing);
        assertEquals(PanProcessedType.DPAN, processing.getPanTypeProcessed());
    }

    @Test
    void shouldDeserializeCkoNetworkTokenAvailable() {
        final String json = "{\"cko_network_token_available\":true}";

        final PaymentProcessing processing = serializer.fromJson(json, PaymentProcessing.class);

        assertNotNull(processing);
        assertEquals(Boolean.TRUE, processing.getCkoNetworkTokenAvailable());
    }

    @Test
    void shouldDeserializeBizumPaymentId() {
        final String json = "{\"bizum_payment_id\":\"bizum_abc123\"}";

        final PaymentProcessing processing = serializer.fromJson(json, PaymentProcessing.class);

        assertNotNull(processing);
        assertEquals("bizum_abc123", processing.getBizumPaymentId());
    }

    @Test
    void shouldDeserializeReconciliationId() {
        final String json = "{\"reconciliation_id\":\"recon_001\"}";

        final PaymentProcessing processing = serializer.fromJson(json, PaymentProcessing.class);

        assertNotNull(processing);
        assertEquals("recon_001", processing.getReconciliationId());
    }

    @Test
    void shouldDeserializePartnerFields() {
        final String json = "{"
                + "\"partner_order_id\":\"po_001\","
                + "\"partner_session_id\":\"ps_001\","
                + "\"partner_client_token\":\"pct_001\","
                + "\"partner_payment_id\":\"pp_001\","
                + "\"partner_status\":\"APPROVED\","
                + "\"partner_transaction_id\":\"pt_001\","
                + "\"partner_merchant_advice_code\":\"01\","
                + "\"partner_response_code\":\"00\","
                + "\"partner_authorization_code\":\"auth_001\","
                + "\"partner_authorization_response_code\":\"resp_001\","
                + "\"partner_error_codes\":[\"ERR001\",\"ERR002\"],"
                + "\"partner_error_message\":\"Declined\""
                + "}";

        final PaymentProcessing processing = serializer.fromJson(json, PaymentProcessing.class);

        assertNotNull(processing);
        assertEquals("po_001", processing.getPartnerOrderId());
        assertEquals("ps_001", processing.getPartnerSessionId());
        assertEquals("pct_001", processing.getPartnerClientToken());
        assertEquals("pp_001", processing.getPartnerPaymentId());
        assertEquals("APPROVED", processing.getPartnerStatus());
        assertEquals("pt_001", processing.getPartnerTransactionId());
        assertEquals("01", processing.getPartnerMerchantAdviceCode());
        assertEquals("00", processing.getPartnerResponseCode());
        assertEquals("auth_001", processing.getPartnerAuthorizationCode());
        assertEquals("resp_001", processing.getPartnerAuthorizationResponseCode());
        assertEquals(Arrays.asList("ERR001", "ERR002"), processing.getPartnerErrorCodes());
        assertEquals("Declined", processing.getPartnerErrorMessage());
    }

    @Test
    void shouldDeserializeSchemeMerchantId() {
        final String json = "{"
                + "\"scheme_merchant_id\":\"sm_001\","
                + "\"recommendation_code\":\"RC001\","
                + "\"merchant_category_code\":\"5411\","
                + "\"scheme\":\"Visa\","
                + "\"pun\":\"PUN123456\""
                + "}";

        final PaymentProcessing processing = serializer.fromJson(json, PaymentProcessing.class);

        assertNotNull(processing);
        assertEquals("sm_001", processing.getSchemeMerchantId());
        assertEquals("RC001", processing.getRecommendationCode());
        assertEquals("5411", processing.getMerchantCategoryCode());
        assertEquals("Visa", processing.getScheme());
        assertEquals("PUN123456", processing.getPun());
    }

    @Test
    void shouldDeserializeForeignRetailerAmount() {
        final String json = "{"
                + "\"foreign_retailer_amount\":750,"
                + "\"surcharge_amount\":50,"
                + "\"provision_network_token\":true,"
                + "\"aft\":true"
                + "}";

        final PaymentProcessing processing = serializer.fromJson(json, PaymentProcessing.class);

        assertNotNull(processing);
        assertEquals(750L, processing.getForeignRetailerAmount());
        assertEquals(50L, processing.getSurchargeAmount());
        assertEquals(Boolean.TRUE, processing.getProvisionNetworkToken());
        assertEquals(Boolean.TRUE, processing.getAft());
    }

    @Test
    void shouldDeserializeFullSwaggerExample() {
        final String json = "{"
                + "\"retrieval_reference_number\":\"RRN001\","
                + "\"acquirer_transaction_id\":\"ACQ001\","
                + "\"acquirer_name\":\"Acquirer Bank\","
                + "\"acquirer_country_code\":\"GB\","
                + "\"recommendation_code\":\"R001\","
                + "\"scheme\":\"Mastercard\","
                + "\"pan_type_processed\":\"fpan\","
                + "\"cko_network_token_available\":false,"
                + "\"purchase_country\":\"GB\","
                + "\"scheme_merchant_id\":\"SMI001\","
                + "\"foreign_retailer_amount\":100,"
                + "\"provision_network_token\":false,"
                + "\"merchant_category_code\":\"5812\","
                + "\"aft\":false,"
                + "\"bizum_payment_id\":\"biz_001\","
                + "\"reconciliation_id\":\"rec_001\""
                + "}";

        final PaymentProcessing processing = serializer.fromJson(json, PaymentProcessing.class);

        assertNotNull(processing);
        assertEquals("RRN001", processing.getRetrievalReferenceNumber());
        assertEquals("ACQ001", processing.getAcquirerTransactionId());
        assertEquals("Acquirer Bank", processing.getAcquirerName());
        assertEquals(CountryCode.GB, processing.getAcquirerCountryCode());
        assertEquals(PanProcessedType.FPAN, processing.getPanTypeProcessed());
        assertEquals(Boolean.FALSE, processing.getCkoNetworkTokenAvailable());
        assertEquals(CountryCode.GB, processing.getPurchaseCountry());
        assertEquals("SMI001", processing.getSchemeMerchantId());
        assertEquals(100L, processing.getForeignRetailerAmount());
        assertEquals("biz_001", processing.getBizumPaymentId());
        assertEquals("rec_001", processing.getReconciliationId());
    }

    @Test
    void shouldHandleAbsentOptionalFields() {
        final String json = "{}";

        final PaymentProcessing processing = serializer.fromJson(json, PaymentProcessing.class);

        assertNotNull(processing);
        assertDoesNotThrow(() -> serializer.toJson(processing));
        assertNull(processing.getRetrievalReferenceNumber());
        assertNull(processing.getPanTypeProcessed());
        assertNull(processing.getCkoNetworkTokenAvailable());
        assertNull(processing.getBizumPaymentId());
    }

    @Test
    void shouldNotSerializeNullFields() {
        final PaymentProcessing processing = new PaymentProcessing();

        final String json = serializer.toJson(processing);

        assertFalse(json.contains("\"retrieval_reference_number\""));
        assertFalse(json.contains("\"pan_type_processed\""));
        assertFalse(json.contains("\"cko_network_token_available\""));
    }
}
