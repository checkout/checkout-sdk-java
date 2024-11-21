package com.checkout.payments;

import com.checkout.TestHelper;
import com.checkout.payments.request.PaymentCustomerRequest;
import com.checkout.payments.response.PaymentResponse;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class CaptureTestIT extends AbstractPaymentsTestIT {

    @Test
    void shouldCaptureCardPayment() {

        final PaymentResponse paymentResponse = makeCardPayment(false);

        assertNotNull(paymentResponse.getLink("capture"));

        final Map<String, Object> metadata = new HashMap<>();
        metadata.put("CaptureTestIT", "shouldCapturePayment");

        final CaptureRequest captureRequest = CaptureRequest.builder()
                .amount(10L)
                .captureType(CaptureType.FINAL)
                .reference(UUID.randomUUID().toString())
                .customer(PaymentCustomerRequest.builder()
                        .email(TestHelper.generateRandomEmail())
                        .name("Bruce Wayne")
                        .taxNumber("1350693505279")
                        .phone(TestHelper.createPhone())
                        .build())
                .description("Set of 3 masks")
                .billingDescriptor(BillingDescriptor.builder()
                        .name("SUPERHEROES.COM")
                        .city("GOTHAM")
                        .reference(UUID.randomUUID().toString())
                        .build())
                .shipping(ShippingDetails.builder()
                        .address(TestHelper.createAddress())
                        .phone(TestHelper.createPhone())
                        .fromAddressZip("10014")
                        .build())
                .items(Collections.singletonList(Product.builder()
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
                        .build()))
                .processing(ProcessingSettings.builder()
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
                        .build())
                .metadata(metadata)
                .build();

        final CaptureResponse captureResponse = blocking(() -> checkoutApi.paymentsClient().capturePayment(paymentResponse.getId(), captureRequest));
        assertNotNull(captureResponse);
        assertNotNull(captureResponse.getActionId());
        assertFalse(captureResponse.getActionId().isEmpty());
        assertEquals(captureRequest.getReference(), captureResponse.getReference());

    }

    @Test
    void shouldCaptureTokenPayment() {

        final PaymentResponse paymentResponse = makeTokenPayment();

        assertNotNull(paymentResponse.getLink("capture"));

        final Map<String, Object> metadata = new HashMap<>();
        metadata.put("CaptureTestIT", "shouldCaptureTokenPayment");

        final CaptureRequest captureRequest = CaptureRequest.builder()
                .reference("Full Capture")
                .metadata(metadata)
                .build();

        final CaptureResponse captureResponse = blocking(() -> checkoutApi.paymentsClient().capturePayment(paymentResponse.getId(), captureRequest));
        assertNotNull(captureResponse);
        assertNotNull(captureResponse.getActionId());
        assertFalse(captureResponse.getActionId().isEmpty());
        assertEquals(captureRequest.getReference(), captureResponse.getReference());

    }

    @Test
    void shouldCapturePaymentPartially() {

        final PaymentResponse paymentResponse = makeCardPayment(false);

        assertNotNull(paymentResponse.getLink("capture"));

        final Map<String, Object> metadata = new HashMap<>();
        metadata.put("CaptureTestIT", "shouldCapturePaymentPartially");

        final CaptureRequest captureRequest = CaptureRequest.builder()
                .amount(5L)
                .reference("Partial Capture")
                .metadata(metadata)
                .build();

        final CaptureResponse captureResponse = blocking(() -> checkoutApi.paymentsClient().capturePayment(paymentResponse.getId(), captureRequest));
        assertNotNull(captureResponse);
        assertNotNull(captureResponse.getActionId());
        assertFalse(captureResponse.getActionId().isEmpty());
        assertEquals(captureRequest.getReference(), captureResponse.getReference());

    }

}
