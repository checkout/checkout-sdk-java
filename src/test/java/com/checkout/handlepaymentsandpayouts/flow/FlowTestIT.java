package com.checkout.handlepaymentsandpayouts.flow;

import com.checkout.PlatformType;
import com.checkout.SandboxTestFixture;
import com.checkout.common.CountryCode;
import com.checkout.common.Currency;
import com.checkout.handlepaymentsandpayouts.flow.paymentsessions.enums.ExemptionType;
import com.checkout.handlepaymentsandpayouts.flow.paymentsessions.enums.IdentificationType;
import com.checkout.handlepaymentsandpayouts.flow.paymentsessions.enums.PanPreferenceType;
import com.checkout.handlepaymentsandpayouts.flow.paymentsessions.enums.PurposeType;
import com.checkout.handlepaymentsandpayouts.flow.paymentsessions.requests.AmountAllocation;
import com.checkout.handlepaymentsandpayouts.flow.paymentsessions.requests.Billing;
import com.checkout.handlepaymentsandpayouts.flow.paymentsessions.requests.BillingDescriptor;
import com.checkout.handlepaymentsandpayouts.flow.paymentsessions.requests.CustomerRetry;
import com.checkout.handlepaymentsandpayouts.flow.paymentsessions.requests.Instruction;
import com.checkout.handlepaymentsandpayouts.flow.paymentsessions.requests.Item;
import com.checkout.handlepaymentsandpayouts.flow.paymentsessions.requests.PaymentMethodConfiguration;
import com.checkout.handlepaymentsandpayouts.flow.paymentsessions.requests.PaymentSessionRequest;
import com.checkout.handlepaymentsandpayouts.flow.paymentsessions.requests.sender.IndividualSender;
import com.checkout.handlepaymentsandpayouts.flow.paymentsessions.requests.Processing;
import com.checkout.handlepaymentsandpayouts.flow.paymentsessions.requests.Risk;
import com.checkout.handlepaymentsandpayouts.flow.paymentsessions.requests.Shipping;
import com.checkout.handlepaymentsandpayouts.flow.paymentsessions.requests.sender.InstrumentSender;
import com.checkout.handlepaymentsandpayouts.flow.paymentsessions.responses.PaymentSessionResponse;
import com.checkout.handlepaymentsandpayouts.flow.paymentsessionscomplete.requests.PaymentSessionWithPaymentRequest;
import com.checkout.handlepaymentsandpayouts.flow.paymentsessionscomplete.responses.PaymentSessionWithPaymentResponse;
import com.checkout.handlepaymentsandpayouts.flow.paymentsessionssubmit.requests.SubmitPaymentSessionRequest;
import com.checkout.handlepaymentsandpayouts.flow.paymentsessionssubmit.responses.SubmitPaymentSessionResponse;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class FlowTestIT extends SandboxTestFixture {

    FlowTestIT() {
        super(PlatformType.DEFAULT);
    }

    @Test
    void shouldMakeAPaymentSessionsRequest() {
        final com.checkout.handlepaymentsandpayouts.flow.paymentsessions.requests.Billing billing = createBilling();
        final com.checkout.handlepaymentsandpayouts.flow.paymentsessions.requests.BillingDescriptor billingDescriptor = createBillingDescriptor();
        final com.checkout.handlepaymentsandpayouts.flow.paymentsessions.requests.Shipping shipping = createShipping();
        final com.checkout.handlepaymentsandpayouts.flow.paymentsessions.requests.Processing processing = createProcessing();
        final com.checkout.handlepaymentsandpayouts.flow.paymentsessions.requests.Instruction instruction = createInstruction();
        final com.checkout.handlepaymentsandpayouts.flow.paymentsessions.requests.PaymentMethodConfiguration paymentMethodConfiguration = createPaymentMethodConfiguration();
        final com.checkout.handlepaymentsandpayouts.flow.paymentsessions.requests.Item item = createItem();
        final com.checkout.handlepaymentsandpayouts.flow.paymentsessions.requests.AmountAllocation amountAllocation = createAmountAllocation();
        final com.checkout.handlepaymentsandpayouts.flow.paymentsessions.requests.Risk risk = createRisk();
        final com.checkout.handlepaymentsandpayouts.flow.paymentsessions.requests.Threeds threeds = createThreeds();
        final com.checkout.handlepaymentsandpayouts.flow.paymentsessions.requests.sender.InstrumentSender sender = createInstrumentSender();
        final com.checkout.handlepaymentsandpayouts.flow.paymentsessions.requests.CustomerRetry customerRetry = createCustomerRetry();

        final HashMap<String, Object> metadata = new java.util.HashMap<>();
        metadata.put("coupon_code", "NY2018");

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

        validatePaymentSessionResponse(response);
    }

    @Disabled("Use on demand")
    @Test
    void shouldMakeAPaymentSessionWithPaymentRequest() {
        final com.checkout.handlepaymentsandpayouts.flow.paymentsessionscomplete.requests.Billing billing = createBillingShort();

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

        validatePaymentSessionWithPaymentResponse(response);
    }

    @Disabled("Use on demand")
    @Test
    void shouldSubmitPaymentSession() {
        final com.checkout.handlepaymentsandpayouts.flow.paymentsessions.requests.Billing billing = createBillingShort2();
        final PaymentSessionRequest sessionRequest = PaymentSessionRequest.builder()
                .amount(1000L)
                .currency(Currency.GBP)
                .reference("ORD-123A")
                .billing(billing)
                .successUrl("https://example.com/payments/success")
                .failureUrl("https://example.com/payments/failure")
                .build();

        final PaymentSessionResponse sessionResponse = blocking(() -> checkoutApi.flowClient().requestPaymentSession(sessionRequest));
        validatePaymentSessionResponseShort(sessionResponse);

        final SubmitPaymentSessionRequest submitRequest =
                SubmitPaymentSessionRequest.builder()
                        .sessionData("session_data_example")
                        .amount(1000L)
                        .reference("ORD-123A")
                        .ipAddress("90.197.169.245")
                        .build();

        final SubmitPaymentSessionResponse submitResponse =
                blocking(() -> checkoutApi.flowClient().submitPaymentSessions(sessionResponse.getId(), submitRequest));

        validateSubmitPaymentSessionResponse(submitResponse);
    }

    // Synchronous methods
    @Test
    void shouldMakeAPaymentSessionsRequestSync() {
        final com.checkout.handlepaymentsandpayouts.flow.paymentsessions.requests.Billing billing = createBilling();
        final com.checkout.handlepaymentsandpayouts.flow.paymentsessions.requests.BillingDescriptor billingDescriptor = createBillingDescriptor();
        final com.checkout.handlepaymentsandpayouts.flow.paymentsessions.requests.Shipping shipping = createShipping();
        final com.checkout.handlepaymentsandpayouts.flow.paymentsessions.requests.Processing processing = createProcessing();
        final com.checkout.handlepaymentsandpayouts.flow.paymentsessions.requests.Instruction instruction = createInstruction();
        final com.checkout.handlepaymentsandpayouts.flow.paymentsessions.requests.PaymentMethodConfiguration paymentMethodConfiguration = createPaymentMethodConfiguration();
        final com.checkout.handlepaymentsandpayouts.flow.paymentsessions.requests.Item item = createItem();
        final com.checkout.handlepaymentsandpayouts.flow.paymentsessions.requests.AmountAllocation amountAllocation = createAmountAllocation();
        final com.checkout.handlepaymentsandpayouts.flow.paymentsessions.requests.Risk risk = createRisk();
        final com.checkout.handlepaymentsandpayouts.flow.paymentsessions.requests.Threeds threeds = createThreeds();
        final com.checkout.handlepaymentsandpayouts.flow.paymentsessions.requests.sender.InstrumentSender sender = createInstrumentSender();
        final com.checkout.handlepaymentsandpayouts.flow.paymentsessions.requests.CustomerRetry customerRetry = createCustomerRetry();

        final HashMap<String, Object> metadata = new java.util.HashMap<>();
        metadata.put("coupon_code", "NY2018");

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

        final PaymentSessionResponse response = checkoutApi.flowClient().requestPaymentSessionSync(request);

        validatePaymentSessionResponse(response);
    }

    @Disabled("Use on demand")
    @Test
    void shouldMakeAPaymentSessionWithPaymentRequestSync() {
        final com.checkout.handlepaymentsandpayouts.flow.paymentsessionscomplete.requests.Billing billing = createBillingShort();

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

        final PaymentSessionWithPaymentResponse response = checkoutApi.flowClient().requestPaymentSessionWithPaymentSync(request);

        validatePaymentSessionWithPaymentResponse(response);
    }

    @Disabled("Use on demand")
    @Test
    void shouldSubmitPaymentSessionSync() {
        final com.checkout.handlepaymentsandpayouts.flow.paymentsessions.requests.Billing billing = createBillingShort2();
        final PaymentSessionRequest sessionRequest = PaymentSessionRequest.builder()
                .amount(1000L)
                .currency(Currency.GBP)
                .reference("ORD-123A")
                .billing(billing)
                .successUrl("https://example.com/payments/success")
                .failureUrl("https://example.com/payments/failure")
                .build();

        final PaymentSessionResponse sessionResponse = checkoutApi.flowClient().requestPaymentSessionSync(sessionRequest);
        validatePaymentSessionResponseShort(sessionResponse);

        final SubmitPaymentSessionRequest submitRequest =
                SubmitPaymentSessionRequest.builder()
                        .sessionData("session_data_example")
                        .amount(1000L)
                        .reference("ORD-123A")
                        .ipAddress("90.197.169.245")
                        .build();

        final SubmitPaymentSessionResponse submitResponse =
                checkoutApi.flowClient().submitPaymentSessionsSync(sessionResponse.getId(), submitRequest);

        validateSubmitPaymentSessionResponse(submitResponse);
    }

    // Common methods
    private Billing createBilling() {
        return com.checkout.handlepaymentsandpayouts.flow.paymentsessions.requests.Billing.builder()
                .address(com.checkout.handlepaymentsandpayouts.flow.paymentsessions.requests.Address.builder()
                        .addressLine1("123 High St.")
                        .addressLine2("Flat 456")
                        .city("London")
                        .state("str")
                        .zip("SW1A 1AA")
                        .country(CountryCode.GB)
                        .build())
                .phone(com.checkout.handlepaymentsandpayouts.flow.paymentsessions.requests.Phone.builder()
                        .countryCode("+1")
                        .number("415 555 2671")
                        .build())
                .build();
    }

    private com.checkout.handlepaymentsandpayouts.flow.paymentsessionscomplete.requests.Billing createBillingShort() {
        return com.checkout.handlepaymentsandpayouts.flow.paymentsessionscomplete.requests.Billing.builder()
                .address(com.checkout.handlepaymentsandpayouts.flow.paymentsessionscomplete.requests.Address.builder()
                        .addressLine1("23 High St.")
                        .addressLine2("Flat 456")
                        .city("London")
                        .state("str")
                        .zip("SW1A 1AA")
                        .country(CountryCode.GB)
                        .build())
                .build();
    }

    private com.checkout.handlepaymentsandpayouts.flow.paymentsessions.requests.Billing createBillingShort2() {
        return com.checkout.handlepaymentsandpayouts.flow.paymentsessions.requests.Billing.builder()
                .address(com.checkout.handlepaymentsandpayouts.flow.paymentsessions.requests.Address.builder()
                        .addressLine1("23 High St.")
                        .addressLine2("Flat 456")
                        .city("London")
                        .state("str")
                        .zip("SW1A 1AA")
                        .country(CountryCode.GB)
                        .build())
                .build();
    }

    private BillingDescriptor createBillingDescriptor() {
        return com.checkout.handlepaymentsandpayouts.flow.paymentsessions.requests.BillingDescriptor.builder()
                .name("string")
                .city("string")
                .reference("string")
                .build();
    }

    private Shipping createShipping() {
        return com.checkout.handlepaymentsandpayouts.flow.paymentsessions.requests.Shipping.builder()
                .address(com.checkout.handlepaymentsandpayouts.flow.paymentsessions.requests.Address.builder()
                        .addressLine1("123 High St.")
                        .addressLine2("Flat 456")
                        .city("London")
                        .state("str")
                        .zip("SW1A 1AA")
                        .country(CountryCode.GB)
                        .build())
                .phone(com.checkout.handlepaymentsandpayouts.flow.paymentsessions.requests.Phone.builder()
                        .countryCode("+1")
                        .number("415 555 2671")
                        .build())
                .build();
    }

    private Processing createProcessing() {
        return com.checkout.handlepaymentsandpayouts.flow.paymentsessions.requests.Processing.builder()
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
    }

    private Instruction createInstruction() {
        return com.checkout.handlepaymentsandpayouts.flow.paymentsessions.requests.Instruction.builder()
                .purpose(PurposeType.DONATIONS)
                .build();
    }

    private PaymentMethodConfiguration createPaymentMethodConfiguration() {
        return com.checkout.handlepaymentsandpayouts.flow.paymentsessions.requests.PaymentMethodConfiguration.builder().build();
    }

    private Item createItem() {
        return com.checkout.handlepaymentsandpayouts.flow.paymentsessions.requests.Item.builder()
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
    }

    private AmountAllocation createAmountAllocation() {
        return com.checkout.handlepaymentsandpayouts.flow.paymentsessions.requests.AmountAllocation.builder()
                .id("string")
                .amount(1L)
                .reference("ORD-123A")
                .commission(com.checkout.handlepaymentsandpayouts.flow.paymentsessions.requests.Commission.builder()
                        .amount(10L)
                        .percentage(12.5)
                        .build())
                .build();
    }

    private Risk createRisk() {
        return com.checkout.handlepaymentsandpayouts.flow.paymentsessions.requests.Risk.builder()
                .enabled(false)
                .build();
    }

    private com.checkout.handlepaymentsandpayouts.flow.paymentsessions.requests.Threeds createThreeds() {
        return com.checkout.handlepaymentsandpayouts.flow.paymentsessions.requests.Threeds.builder()
                .enabled(false)
                .attemptN3d(false)
                .challengeIndicator(com.checkout.handlepaymentsandpayouts.flow.paymentsessions.enums.ChallengeIndicatorType.NO_PREFERENCE)
                .exemption(ExemptionType.LOW_VALUE)
                .allowUpgrade(true)
                .build();
    }

    private InstrumentSender createInstrumentSender() {
        return com.checkout.handlepaymentsandpayouts.flow.paymentsessions.requests.sender.InstrumentSender.builder()
                .reference("8285282045818")
                .build();
    }
    private CustomerRetry createCustomerRetry() {
        return com.checkout.handlepaymentsandpayouts.flow.paymentsessions.requests.CustomerRetry.builder()
                .maxAttempts(2L)
                .build();
    }

    private void validatePaymentSessionResponseShort(final PaymentSessionResponse response) {
        assertNotNull(response);
        assertNotNull(response.getId());
    }

    private void validatePaymentSessionResponse(final PaymentSessionResponse response) {
        validatePaymentSessionResponseShort(response);
        assertNotNull(response.getPaymentSessionToken());
        assertNotNull(response.getPaymentSessionSecret());
        assertNotNull(response.getLinks());
    }

    private void validatePaymentSessionWithPaymentResponse(final PaymentSessionWithPaymentResponse response) {
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

    private void validateSubmitPaymentSessionResponse(final SubmitPaymentSessionResponse submitResponse) {
        assertNotNull(submitResponse);
        assertNotNull(submitResponse.getId());
        assertNotNull(submitResponse.getStatus());
        assertNotNull(submitResponse.getType());
        if (submitResponse.getHttpStatusCode() == 202) {
            assertNotNull(submitResponse.getAction());
        }
    }
}