package com.checkout.payments;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import com.checkout.TestHelper;
import com.checkout.payments.request.PaymentCustomerRequest;
import com.checkout.payments.response.PaymentResponse;

@Disabled("unavailable")
class CaptureTestIT extends AbstractPaymentsTestIT {

    @Test
    void shouldCaptureCardPayment() {
        final PaymentResponse paymentResponse = makeCardPayment(false);
        assertNotNull(paymentResponse.getLink("capture"));

        final CaptureRequest captureRequest = createComplexCaptureRequest();
        final CaptureResponse captureResponse = blocking(() -> checkoutApi.paymentsClient().capturePayment(paymentResponse.getId(), captureRequest));
        
        validateCaptureResponse(captureResponse, captureRequest);
    }

    @Test
    void shouldCaptureTokenPayment() {
        final PaymentResponse paymentResponse = makeTokenPayment();
        assertNotNull(paymentResponse.getLink("capture"));

        final CaptureRequest captureRequest = createSimpleCaptureRequest("Full Capture", "shouldCaptureTokenPayment");
        final CaptureResponse captureResponse = blocking(() -> checkoutApi.paymentsClient().capturePayment(paymentResponse.getId(), captureRequest));
        
        validateCaptureResponse(captureResponse, captureRequest);
    }

    @Test
    void shouldCapturePaymentPartially() {
        final PaymentResponse paymentResponse = makeCardPayment(false);
        assertNotNull(paymentResponse.getLink("capture"));

        final CaptureRequest captureRequest = createPartialCaptureRequest();
        final CaptureResponse captureResponse = blocking(() -> checkoutApi.paymentsClient().capturePayment(paymentResponse.getId(), captureRequest));
        
        validateCaptureResponse(captureResponse, captureRequest);
    }

    // Synchronous methods
    @Test
    void shouldCaptureCardPaymentSync() {
        final PaymentResponse paymentResponse = makeCardPayment(false);
        assertNotNull(paymentResponse.getLink("capture"));

        final CaptureRequest captureRequest = createComplexCaptureRequest();
        final CaptureResponse captureResponse = checkoutApi.paymentsClient().capturePaymentSync(paymentResponse.getId(), captureRequest);
        
        validateCaptureResponse(captureResponse, captureRequest);
    }

    @Test
    void shouldCaptureTokenPaymentSync() {
        final PaymentResponse paymentResponse = makeTokenPayment();
        assertNotNull(paymentResponse.getLink("capture"));

        final CaptureRequest captureRequest = createSimpleCaptureRequest("Full Capture", "shouldCaptureTokenPayment");
        final CaptureResponse captureResponse = checkoutApi.paymentsClient().capturePaymentSync(paymentResponse.getId(), captureRequest);
        
        validateCaptureResponse(captureResponse, captureRequest);
    }

    @Test
    void shouldCapturePaymentPartiallySync() {
        final PaymentResponse paymentResponse = makeCardPayment(false);
        assertNotNull(paymentResponse.getLink("capture"));

        final CaptureRequest captureRequest = createPartialCaptureRequest();
        final CaptureResponse captureResponse = checkoutApi.paymentsClient().capturePaymentSync(paymentResponse.getId(), captureRequest);
        
        validateCaptureResponse(captureResponse, captureRequest);
    }

    // Common methods
    private CaptureRequest createComplexCaptureRequest() {
        final Map<String, Object> metadata = createCaptureMetadata("shouldCapturePayment");

        return CaptureRequest.builder()
                .amount(10L)
                .captureType(CaptureType.FINAL)
                .reference(UUID.randomUUID().toString())
                .customer(createCaptureCustomer())
                .description("Set of 3 masks")
                .billingDescriptor(createBillingDescriptor())
                .shipping(createShippingDetails())
                .items(Collections.singletonList(createProductRequest()))
                .processing(createProcessingSettings())
                .metadata(metadata)
                .build();
    }

    private CaptureRequest createSimpleCaptureRequest(String reference, String metadataValue) {
        return CaptureRequest.builder()
                .reference(reference)
                .metadata(createCaptureMetadata(metadataValue))
                .build();
    }

    private CaptureRequest createPartialCaptureRequest() {
        return CaptureRequest.builder()
                .amount(5L)
                .reference("Partial Capture")
                .metadata(createCaptureMetadata("shouldCapturePaymentPartially"))
                .build();
    }

    private Map<String, Object> createCaptureMetadata(String value) {
        final Map<String, Object> metadata = new HashMap<>();
        metadata.put("CaptureTestIT", value);
        return metadata;
    }

    private PaymentCustomerRequest createCaptureCustomer() {
        return PaymentCustomerRequest.builder()
                .email(TestHelper.generateRandomEmail())
                .name("Bruce Wayne")
                .taxNumber("1350693505279")
                .phone(TestHelper.createPhone())
                .build();
    }

    private BillingDescriptor createBillingDescriptor() {
        return BillingDescriptor.builder()
                .name("SUPERHEROES.COM")
                .city("GOTHAM")
                .reference(UUID.randomUUID().toString())
                .build();
    }

    private ShippingDetails createShippingDetails() {
        return ShippingDetails.builder()
                .address(TestHelper.createAddress())
                .phone(TestHelper.createPhone())
                .fromAddressZip("10014")
                .build();
    }

    private ProductRequest createProductRequest() {
        return ProductRequest.builder()
                .name("Kevlar batterang")
                .quantity(2L)
                .unitPrice(50L)
                .reference(UUID.randomUUID().toString())
                .commodityCode("DEF123")
                .unitOfMeasure("metres")
                .totalAmount(29000L)
                .taxAmount(1000L)
                .discountAmount(1000L)
                .wxpayGoodsId("1001")
                .build();
    }

    private ProcessingSettings createProcessingSettings() {
        return ProcessingSettings.builder()
                .orderId("123456789")
                .taxAmount(3000L)
                .discountAmount(0L)
                .dutyAmount(0L)
                .shippingAmount(300L)
                .shippingTaxAmount(100L)
                .aft(true)
                .preferredScheme(PreferredSchema.MASTERCARD)
                .merchantInitiatedReason(MerchantInitiatedReason.DELAYED_CHARGE)
                .productType(ProductType.QR_CODE)
                .openId("oUpF8uMuAJO_M2pxb1Q9zNjWeS6o")
                .originalOrderAmount(10L)
                .receiptId("10")
                .terminalType(TerminalType.WAP)
                .osType(OsType.ANDROID)
                .invoiceId(UUID.randomUUID().toString())
                .brandName("Super Brand")
                .locale("en-US")
                .shippingPreference(ShippingPreference.SET_PROVIDED_ADDRESS)
                .userAction(UserAction.PAY_NOW)
                .lineOfBusiness("Flights")
                .build();
    }

    private void validateCaptureResponse(CaptureResponse captureResponse, CaptureRequest captureRequest) {
        assertNotNull(captureResponse);
        assertNotNull(captureResponse.getActionId());
        assertFalse(captureResponse.getActionId().isEmpty());
        assertEquals(captureRequest.getReference(), captureResponse.getReference());
    }

}
