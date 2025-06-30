package com.checkout.handlepaymentsandpayouts.flow;

import com.checkout.PlatformType;
import com.checkout.SandboxTestFixture;
import com.checkout.common.CountryCode;
import com.checkout.common.Currency;
import com.checkout.handlepaymentsandpayouts.flow.paymentsessions.enums.ExemptionType;
import com.checkout.handlepaymentsandpayouts.flow.paymentsessions.enums.PanPreferenceType;
import com.checkout.handlepaymentsandpayouts.flow.paymentsessions.enums.PurposeType;
import com.checkout.handlepaymentsandpayouts.flow.paymentsessions.requests.PaymentSessionRequest;
import com.checkout.handlepaymentsandpayouts.flow.paymentsessions.responses.PaymentSessionResponse;
import com.checkout.handlepaymentsandpayouts.flow.paymentsessionscomplete.requests.PaymentSessionWithPaymentRequest;
import com.checkout.handlepaymentsandpayouts.flow.paymentsessionscomplete.responses.PaymentSessionWithPaymentResponse;
import com.checkout.handlepaymentsandpayouts.flow.paymentsessionssubmit.requests.SubmitPaymentSessionRequest;
import com.checkout.handlepaymentsandpayouts.flow.paymentsessionssubmit.responses.SubmitPaymentSessionResponse;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class FlowTestIT extends SandboxTestFixture {

    FlowTestIT() {
        super(PlatformType.DEFAULT);
    }

    @Test
    void shouldMakeAPaymentSessionsRequest() {
        final com.checkout.handlepaymentsandpayouts.flow.paymentsessions.requests.Billing billing = com.checkout.handlepaymentsandpayouts.flow.paymentsessions.requests.Billing.builder()
                .address(com.checkout.handlepaymentsandpayouts.flow.paymentsessions.requests.Address.builder()
                        .addressLine1("123 High St.")
                        .addressLine2("Flat 456")
                        .city("London")
                        .state("str")
                        .zip("SW1A 1AA")
                        .country(CountryCode.GB)
                        .build())
                .phone(com.checkout.handlepaymentsandpayouts.flow.paymentsessions.requests.Phone.builder()
                        .countryCode(CountryCode.US)
                        .number("415 555 2671")
                        .build())
                .build();

        final com.checkout.handlepaymentsandpayouts.flow.paymentsessions.requests.BillingDescriptor billingDescriptor = com.checkout.handlepaymentsandpayouts.flow.paymentsessions.requests.BillingDescriptor.builder()
                .name("string")
                .city("string")
                .reference("string")
                .build();

        final com.checkout.handlepaymentsandpayouts.flow.paymentsessions.requests.Customer customer = com.checkout.handlepaymentsandpayouts.flow.paymentsessions.requests.Customer.builder()
                .email("jia.tsang@example.com")
                .name("Jia Tsang")
                .id("string")
                .phone(com.checkout.handlepaymentsandpayouts.flow.paymentsessions.requests.Phone.builder()
                        .countryCode(CountryCode.US)
                        .number("415 555 2671")
                        .build())
                .taxNumber("string")
                .build();

        final com.checkout.handlepaymentsandpayouts.flow.paymentsessions.requests.Shipping shipping = com.checkout.handlepaymentsandpayouts.flow.paymentsessions.requests.Shipping.builder()
                .address(com.checkout.handlepaymentsandpayouts.flow.paymentsessions.requests.Address.builder()
                        .addressLine1("123 High St.")
                        .addressLine2("Flat 456")
                        .city("London")
                        .state("str")
                        .zip("SW1A 1AA")
                        .country(CountryCode.GB)
                        .build())
                .phone(com.checkout.handlepaymentsandpayouts.flow.paymentsessions.requests.Phone.builder()
                        .countryCode(CountryCode.US)
                        .number("415 555 2671")
                        .build())
                .build();

        final com.checkout.handlepaymentsandpayouts.flow.paymentsessions.requests.Recipient recipient = com.checkout.handlepaymentsandpayouts.flow.paymentsessions.requests.Recipient.builder()
                .dob(LocalDateTime.parse("1985-05-15T00:00:00").toInstant(ZoneOffset.UTC))
                .accountNumber("5555554444")
                .address(com.checkout.handlepaymentsandpayouts.flow.paymentsessions.requests.Address.builder()
                        .addressLine1("123 High St.")
                        .addressLine2("Flat 456")
                        .city("London")
                        .state("str")
                        .zip("SW1A 1AA")
                        .country(CountryCode.GB)
                        .build())
                .firstName("Jia")
                .lastName("Tsang")
                .build();

        final com.checkout.handlepaymentsandpayouts.flow.paymentsessions.requests.Processing processing = com.checkout.handlepaymentsandpayouts.flow.paymentsessions.requests.Processing.builder()
                .aft(true)
                .discountAmount(0.0)
                .shippingAmount(300.0)
                .taxAmount(3000.0)
                .invoiceId("string")
                .brandName("string")
                .locale("en-US")
                .orderId("123456789")
                .surchargeAmount(200L)
                .dutyAmount(0.0)
                .shippingTaxAmount(100.0)
                .merchantInitiatedReason(com.checkout.handlepaymentsandpayouts.flow.paymentsessions.enums.MerchantInitiatedReasonType.DELAYED_CHARGE)
                .campaignId(0L)
                .originalOrderAmount(10.0)
                .receiptId("10")
                .merchantCallbackUrl("string")
                .lineOfBusiness("Flights")
                .panPreference(PanPreferenceType.FPAN)
                .provisionNetworkToken(true)
                .build();

        final com.checkout.handlepaymentsandpayouts.flow.paymentsessions.requests.Instruction instruction = com.checkout.handlepaymentsandpayouts.flow.paymentsessions.requests.Instruction.builder()
                .purpose(PurposeType.DONATIONS)
                .build();

        final com.checkout.handlepaymentsandpayouts.flow.paymentsessions.requests.PaymentMethodConfiguration paymentMethodConfiguration = com.checkout.handlepaymentsandpayouts.flow.paymentsessions.requests.PaymentMethodConfiguration.builder().build();

        final com.checkout.handlepaymentsandpayouts.flow.paymentsessions.requests.Item item = com.checkout.handlepaymentsandpayouts.flow.paymentsessions.requests.Item.builder()
                .reference("string")
                .commodityCode("string")
                .unitOfMeasure("string")
                .totalAmount(1000L)
                .taxAmount(1000L)
                .discountAmount(1000L)
                .url("string")
                .imageUrl("string")
                .name("Gold Necklace")
                .quantity(1L)
                .unitPrice(1000L)
                .build();

        final com.checkout.handlepaymentsandpayouts.flow.paymentsessions.requests.AmountAllocation amountAllocation = com.checkout.handlepaymentsandpayouts.flow.paymentsessions.requests.AmountAllocation.builder()
                .id("string")
                .amount(1L)
                .reference("ORD-123A")
                .commission(com.checkout.handlepaymentsandpayouts.flow.paymentsessions.requests.Commission.builder()
                        .amount(10L)
                        .percentage(12.5)
                        .build())
                .build();

        final com.checkout.handlepaymentsandpayouts.flow.paymentsessions.requests.Risk risk = com.checkout.handlepaymentsandpayouts.flow.paymentsessions.requests.Risk.builder()
                .enabled(false)
                .build();

        final HashMap<String, Object> metadata = new java.util.HashMap<>();
        metadata.put("coupon_code", "NY2018");

        final com.checkout.handlepaymentsandpayouts.flow.paymentsessions.requests.Threeds threeds = com.checkout.handlepaymentsandpayouts.flow.paymentsessions.requests.Threeds.builder()
                .enabled(false)
                .attemptN3d(false)
                .challengeIndicator(com.checkout.handlepaymentsandpayouts.flow.paymentsessions.enums.ChallengeIndicatorType.NO_PREFERENCE)
                .exemption(ExemptionType.LOW_VALUE)
                .allowUpgrade(true)
                .build();

        final com.checkout.handlepaymentsandpayouts.flow.paymentsessions.requests.sender.InstrumentSender sender = com.checkout.handlepaymentsandpayouts.flow.paymentsessions.requests.sender.InstrumentSender.builder()
                .reference("8285282045818")
                .build();

        final com.checkout.handlepaymentsandpayouts.flow.paymentsessions.requests.CustomerRetry customerRetry = com.checkout.handlepaymentsandpayouts.flow.paymentsessions.requests.CustomerRetry.builder()
                .maxAttempts(2L)
                .build();

        final com.checkout.handlepaymentsandpayouts.flow.paymentsessions.requests.PaymentSessionRequest request = com.checkout.handlepaymentsandpayouts.flow.paymentsessions.requests.PaymentSessionRequest.builder()
                .amount(1000L)
                .currency(Currency.USD)
                .paymentType(com.checkout.handlepaymentsandpayouts.flow.paymentsessions.enums.PaymentType.REGULAR)
                .billing(billing)
                .billingDescriptor(billingDescriptor)
                .reference("ORD-123A")
                .description("Payment for gold necklace")
                //.customer(customer)
                .shipping(shipping)
                //.recipient(recipient)
                .processing(processing)
                .instruction(instruction)
                .processingChannelId(System.getenv("CHECKOUT_PROCESSING_CHANNEL_ID"))
                .paymentMethodConfiguration(paymentMethodConfiguration)
                .items(java.util.Collections.singletonList(item))
                .amountAllocations(java.util.Collections.singletonList(amountAllocation))
                .risk(risk)
                .displayName("Company Test")
                .successUrl("https://example.com/payments/success")
                .failureUrl("https://example.com/payments/failure")
                .metadata(metadata)
                .locale(com.checkout.handlepaymentsandpayouts.flow.paymentsessions.enums.LocaleType.AR)
                .threeds(threeds)
                .sender(sender)
                .capture(true)
                .captureOn(java.time.Instant.parse("2024-01-01T09:15:30Z"))
                //.expiresOn(java.time.Instant.parse("2024-01-01T09:15:30Z"))
                .enabledPaymentMethods(java.util.Arrays.asList(
                        com.checkout.handlepaymentsandpayouts.flow.paymentsessions.enums.EnabledPaymentMethodsType.CARD,
                        com.checkout.handlepaymentsandpayouts.flow.paymentsessions.enums.EnabledPaymentMethodsType.APPLEPAY,
                        com.checkout.handlepaymentsandpayouts.flow.paymentsessions.enums.EnabledPaymentMethodsType.GOOGLEPAY))
                .disabledPaymentMethods(java.util.Arrays.asList(
                        com.checkout.handlepaymentsandpayouts.flow.paymentsessions.enums.DisabledPaymentMethodsType.EPS,
                        com.checkout.handlepaymentsandpayouts.flow.paymentsessions.enums.DisabledPaymentMethodsType.IDEAL,
                        com.checkout.handlepaymentsandpayouts.flow.paymentsessions.enums.DisabledPaymentMethodsType.KNET))
                .customerRetry(customerRetry)
                .ipAddress("90.197.169.245")
                .build();

        final PaymentSessionResponse response = blocking(() -> checkoutApi.flowClient().requestPaymentSession(request));

        assertNotNull(response);
        assertNotNull(response.getId());
        assertNotNull(response.getPaymentSessionToken());
        assertNotNull(response.getPaymentSessionSecret());
        assertNotNull(response.getLinks());
    }

    @Disabled("Use on demand")
    @Test
    void shouldSubmitPaymentSession() {
        final com.checkout.handlepaymentsandpayouts.flow.paymentsessions.requests.Billing billing =
                com.checkout.handlepaymentsandpayouts.flow.paymentsessions.requests.Billing.builder()
                        .address(com.checkout.handlepaymentsandpayouts.flow.paymentsessions.requests.Address.builder()
                                .addressLine1("23 High St.")
                                .addressLine2("Flat 456")
                                .city("London")
                                .state("str")
                                .zip("SW1A 1AA")
                                .country(CountryCode.GB)
                                .build())
                        .build();
        final PaymentSessionRequest sessionRequest = PaymentSessionRequest.builder()
                .amount(1000L)
                .currency(Currency.GBP)
                .reference("ORD-123A")
                .billing(billing)
                .successUrl("https://example.com/payments/success")
                .failureUrl("https://example.com/payments/failure")
                .build();

        final PaymentSessionResponse sessionResponse = blocking(() -> checkoutApi.flowClient().requestPaymentSession(sessionRequest));
        assertNotNull(sessionResponse);
        assertNotNull(sessionResponse.getId());

        final SubmitPaymentSessionRequest submitRequest =
                SubmitPaymentSessionRequest.builder()
                        .sessionData("session_data_example")
                        .amount(1000L)
                        .reference("ORD-123A")
                        .ipAddress("90.197.169.245")
                        .build();

        final SubmitPaymentSessionResponse submitResponse =
                blocking(() -> checkoutApi.flowClient().submitPaymentSessions(sessionResponse.getId(), submitRequest));

        assertNotNull(submitResponse);
        assertNotNull(submitResponse.getId());
        assertNotNull(submitResponse.getStatus());
        assertNotNull(submitResponse.getType());
        if (submitResponse.getHttpStatusCode() == 202) {
            assertNotNull(submitResponse.getAction());
        }
    }

    @Disabled("Use on demand")
    @Test
    void shouldMakeAPaymentSessionWithPaymentRequest() {
        final com.checkout.handlepaymentsandpayouts.flow.paymentsessionscomplete.requests.Billing billing =
                com.checkout.handlepaymentsandpayouts.flow.paymentsessionscomplete.requests.Billing.builder()
                        .address(com.checkout.handlepaymentsandpayouts.flow.paymentsessionscomplete.requests.Address.builder()
                                .addressLine1("23 High St.")
                                .addressLine2("Flat 456")
                                .city("London")
                                .state("str")
                                .zip("SW1A 1AA")
                                .country(CountryCode.GB)
                                .build())
                        .build();

        final PaymentSessionWithPaymentRequest request =
                PaymentSessionWithPaymentRequest.builder()
                        .sessionData("session_data_example")
                        .amount(1000L)
                        .currency(Currency.GBP)
                        .reference("ORD-123A")
                        .billing(billing)
                        .successUrl("https://example.com/payments/success")
                        .failureUrl("https://example.com/payments/failure")
                        .build();

        final PaymentSessionWithPaymentResponse response =
                blocking(() -> checkoutApi.flowClient().requestPaymentSessionWithPayment(request));

        assertNotNull(response);
        assertNotNull(response.getId());
        assertNotNull(response.getStatus());
        assertNotNull(response.getType());
        assertNotNull(response.getPaymentSessionId());
        assertNotNull(response.getPaymentSessionSecret());
        if (response.getHttpStatusCode() == 202) {
            assertNotNull(response.getAction());
        }
    }

}