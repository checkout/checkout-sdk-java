package com.checkout.payments;

import static com.checkout.TestHelper.createAddress;
import static com.checkout.TestHelper.createPhone;
import static com.checkout.TestHelper.getAccountHolder;
import static java.util.Objects.requireNonNull;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import com.checkout.CheckoutApi;
import com.checkout.CheckoutApiException;
import com.checkout.CheckoutSdk;
import com.checkout.Environment;
import com.checkout.TestHelper;
import com.checkout.common.AccountHolder;
import com.checkout.common.AccountHolderType;
import com.checkout.common.AccountType;
import com.checkout.common.Address;
import com.checkout.common.CountryCode;
import com.checkout.common.Currency;
import com.checkout.common.CustomerRequest;
import com.checkout.common.PaymentSourceType;
import com.checkout.common.Phone;
import com.checkout.payments.request.PaymentCustomerRequest;
import com.checkout.payments.request.PaymentRequest;
import com.checkout.payments.request.source.AbstractRequestSource;
import com.checkout.payments.request.source.apm.RequestAchSource;
import com.checkout.payments.request.source.apm.RequestAfterPaySource;
import com.checkout.payments.request.source.apm.RequestAlipayPlusSource;
import com.checkout.payments.request.source.apm.RequestAlmaSource;
import com.checkout.payments.request.source.apm.RequestBancontactSource;
import com.checkout.payments.request.source.apm.RequestBenefitSource;
import com.checkout.payments.request.source.apm.RequestBizumSource;
import com.checkout.payments.request.source.apm.RequestCvConnectSource;
import com.checkout.payments.request.source.apm.RequestEpsSource;
import com.checkout.payments.request.source.apm.RequestFawrySource;
import com.checkout.payments.request.source.apm.RequestGiropaySource;
import com.checkout.payments.request.source.apm.RequestIdealSource;
import com.checkout.payments.request.source.apm.RequestIllicadoSource;
import com.checkout.payments.request.source.apm.RequestKnetSource;
import com.checkout.payments.request.source.apm.RequestMbwaySource;
import com.checkout.payments.request.source.apm.RequestMultiBancoSource;
import com.checkout.payments.request.source.apm.RequestOctopusSource;
import com.checkout.payments.request.source.apm.RequestP24Source;
import com.checkout.payments.request.source.apm.RequestPlaidSource;
import com.checkout.payments.request.source.apm.RequestPostFinanceSource;
import com.checkout.payments.request.source.apm.RequestQPaySource;
import com.checkout.payments.request.source.apm.RequestSepaSource;
import com.checkout.payments.request.source.apm.RequestSequraSource;
import com.checkout.payments.request.source.apm.RequestSofortSource;
import com.checkout.payments.request.source.apm.RequestStcPaySource;
import com.checkout.payments.request.source.apm.RequestTamaraSource;
import com.checkout.payments.request.source.apm.RequestTrustlySource;
import com.checkout.payments.response.GetPaymentResponse;
import com.checkout.payments.response.PaymentResponse;
import com.checkout.payments.response.source.AlternativePaymentSourceResponse;

class RequestApmPaymentsIT extends AbstractPaymentsTestIT {

    // Async tests
    @Test
    void shouldMakeAliPayPayment() throws ExecutionException, InterruptedException {
        final PaymentRequest paymentRequest = createAlipayPaymentRequest();
        
        try {
            paymentsClient.requestPayment(paymentRequest).get();
            fail();
        } catch (Exception exception) {
            assertTrue(exception.getCause() instanceof CheckoutApiException);
        }
    }

    @Test
    @Disabled("unavailable")
    void shouldMakeIdealPayment() throws ExecutionException, InterruptedException {
        final PaymentRequest paymentRequest = createIdealPaymentRequest();
        
        final CompletableFuture<PaymentResponse> future = paymentsClient.requestPayment(paymentRequest);
        final PaymentResponse paymentResponse = future.get();
        
        validatePaymentResponse(paymentResponse);
        validatePaymentDetails(paymentResponse.getId(), PaymentSourceType.IDEAL);
    }

    @Disabled("payment method not supported")
    @Test
    void shouldMakeSofortPayment() throws ExecutionException, InterruptedException {
        final PaymentRequest paymentRequest = createSofortPaymentRequest();
        
        final CompletableFuture<PaymentResponse> future = paymentsClient.requestPayment(paymentRequest);
        final PaymentResponse paymentResponse = future.get();
        
        validatePaymentResponse(paymentResponse);
        validatePaymentDetails(paymentResponse.getId(), PaymentSourceType.SOFORT);
    }

    @Disabled("preview")
    @Test
    void shouldMakeTamaraPayment() throws ExecutionException, InterruptedException {
        final CheckoutApi previewApi = createPreviewApi();
        final PaymentRequest paymentRequest = createTamaraPaymentRequest();

        final CompletableFuture<PaymentResponse> future = previewApi.paymentsClient().requestPayment(paymentRequest);
        final PaymentResponse response = future.get();
        
        validateTamaraPaymentResponse(response);
    }

    @Test
    void shouldMakeAfterPayPayment() throws ExecutionException, InterruptedException {
        final PaymentRequest paymentRequest = createAfterPayPaymentRequest();
        
        try {
            paymentsClient.requestPayment(paymentRequest).get();
            fail();
        } catch (Exception exception) {
            assertTrue(exception.getCause() instanceof CheckoutApiException);
        }
    }

    @Test
    @Disabled("unavailable")
    void shouldMakeBenefitPayment() throws ExecutionException, InterruptedException {
        final PaymentRequest paymentRequest = createBenefitPaymentRequest();
        checkErrorItem(() -> paymentsClient.requestPayment(paymentRequest), PAYEE_NOT_ONBOARDED);
    }

    @Test
    void shouldMakeQPayPayment() throws ExecutionException, InterruptedException {
        final PaymentRequest paymentRequest = createQPayPaymentRequest();
        checkErrorItem(() -> paymentsClient.requestPayment(paymentRequest), PAYEE_NOT_ONBOARDED);
    }

    @Test
    @Disabled("unavailable")
    void shouldMakeMbwayPayment() throws ExecutionException, InterruptedException {
        final PaymentRequest paymentRequest = createMbwayPaymentRequest();
        checkErrorItem(() -> paymentsClient.requestPayment(paymentRequest), "cko_processing_channel_id_invalid");
    }

    @Test
    void shouldMakeEpsPayment() throws ExecutionException, InterruptedException {
        final PaymentRequest paymentRequest = createEpsPaymentRequest();
        checkErrorItem(() -> paymentsClient.requestPayment(paymentRequest), PAYEE_NOT_ONBOARDED);
    }

    @Test
    void shouldMakeIllicadoPayment() throws ExecutionException, InterruptedException {
        final PaymentRequest paymentRequest = createIllicadoPaymentRequest();
        checkErrorItem(() -> paymentsClient.requestPayment(paymentRequest), PAYEE_NOT_ONBOARDED);
    }

    @Test
    void shouldMakeGiropayPayment() throws ExecutionException, InterruptedException {
        final PaymentRequest paymentRequest = createGiropayPaymentRequest();
        checkErrorItem(() -> paymentsClient.requestPayment(paymentRequest), PAYMENT_METHOD_NOT_SUPPORTED);
    }

    @Test
    void shouldMakePrzelewy24Payment() throws ExecutionException, InterruptedException {
        final PaymentRequest paymentRequest = createP24PaymentRequest();
        checkErrorItem(() -> paymentsClient.requestPayment(paymentRequest), PAYEE_NOT_ONBOARDED);
    }

    @Test
    void shouldMakeKnetPayment() throws ExecutionException, InterruptedException {
        final PaymentRequest paymentRequest = createKnetPaymentRequest();
        
        final CompletableFuture<PaymentResponse> future = paymentsClient.requestPayment(paymentRequest);
        final PaymentResponse paymentResponse = future.get();
        
        validatePaymentResponse(paymentResponse);
    }

    @Test
    @Disabled("unavailable")
    void shouldMakeBancontactPayment() throws ExecutionException, InterruptedException {
        final PaymentRequest paymentRequest = createBancontactPaymentRequest();
        
        final CompletableFuture<PaymentResponse> future = paymentsClient.requestPayment(paymentRequest);
        final PaymentResponse paymentResponse = future.get();
        
        validatePaymentResponse(paymentResponse);
        validatePaymentDetails(paymentResponse.getId(), PaymentSourceType.BANCONTACT);
    }

    @Test
    void shouldMakeMultiBancoPayment() throws ExecutionException, InterruptedException {
        final PaymentRequest paymentRequest = createMultiBancoPaymentRequest();
        checkErrorItem(() -> paymentsClient.requestPayment(paymentRequest), PAYEE_NOT_ONBOARDED);
    }

    @Test
    void shouldMakePostFinancePayment() throws ExecutionException, InterruptedException {
        final PaymentRequest paymentRequest = createPostFinancePaymentRequest();
        checkErrorItem(() -> paymentsClient.requestPayment(paymentRequest), PAYEE_NOT_ONBOARDED);
    }

    // 2026/01/16 DR - APM is currently unavailable, temporary skipping the stcPay failing tests
    @Disabled("APM is currently unavailable, temporary skipping the stcPay failing tests")
    @Test
    void shouldMakeStcPayPayment() throws ExecutionException, InterruptedException {
        final PaymentRequest paymentRequest = createStcPayPaymentRequest();
        checkErrorItem(() -> paymentsClient.requestPayment(paymentRequest), "merchant_data_delegated_authentication_failed");
    }

    @Test
    void shouldMakeAlmaPayment() throws ExecutionException, InterruptedException {
        final PaymentRequest paymentRequest = createAlmaPaymentRequest();
        checkErrorItem(() -> paymentsClient.requestPayment(paymentRequest), PAYEE_NOT_ONBOARDED);
    }

    @Test
    void shouldMakeFawryPayment() throws ExecutionException, InterruptedException {
        final PaymentRequest paymentRequest = createFawryPaymentRequest();
        checkErrorItem(() -> paymentsClient.requestPayment(paymentRequest), PAYEE_NOT_ONBOARDED);
    }

    @Test
    void shouldMakeTrustlyPayment() throws ExecutionException, InterruptedException {
        final PaymentRequest paymentRequest = createTrustlyPaymentRequest();
        checkErrorItem(() -> paymentsClient.requestPayment(paymentRequest), PAYEE_NOT_ONBOARDED);
    }

    @Test
    void shouldMakeCvConnectPayment() throws ExecutionException, InterruptedException {
        final PaymentRequest paymentRequest = createCvConnectPaymentRequest();
        checkErrorItem(() -> paymentsClient.requestPayment(paymentRequest), PAYEE_NOT_ONBOARDED);
    }

    @Test
    void shouldMakeSepaV4Payment() throws ExecutionException, InterruptedException {
        final PaymentRequest paymentRequest = createSepaPaymentRequest();
        checkErrorItem(() -> paymentsClient.requestPayment(paymentRequest), APM_SERVICE_UNAVAILABLE);
    }

    @Test
    void shouldMakeAchPayment() throws ExecutionException, InterruptedException {
        final PaymentRequest paymentRequest = createAchPaymentRequest();
        
        final CompletableFuture<PaymentResponse> future = paymentsClient.requestPayment(paymentRequest);
        final PaymentResponse paymentResponse = future.get();
        
        validateAchPaymentResponse(paymentResponse);
    }

    @Test
    void shouldMakeBizumPayment() throws ExecutionException, InterruptedException {
        final PaymentRequest paymentRequest = createBizumPaymentRequest();
        checkErrorItem(() -> paymentsClient.requestPayment(paymentRequest), APM_SERVICE_UNAVAILABLE);
    }

    @Test
    void shouldMakeOctopusPayment() throws ExecutionException, InterruptedException {
        final PaymentRequest paymentRequest = createOctopusPaymentRequest();
        checkErrorItem(() -> paymentsClient.requestPayment(paymentRequest), APM_CURRENCY_NOT_SUPPORTED);
    }

    @Test
    void shouldMakePlaidPayment() throws ExecutionException, InterruptedException {
        final PaymentRequest paymentRequest = createPlaidPaymentRequest();
        
        final CompletableFuture<PaymentResponse> future = paymentsClient.requestPayment(paymentRequest);
        final PaymentResponse paymentResponse = future.get();
        
        validatePlaidPaymentResponse(paymentResponse);
    }

    @Test
    void shouldMakeSequraPayment() throws ExecutionException, InterruptedException {
        final PaymentRequest paymentRequest = createSequraPaymentRequest();
        checkErrorItem(() -> paymentsClient.requestPayment(paymentRequest), PAYEE_NOT_ONBOARDED);
    }

    // Synchronous methods
    @Test
    void shouldMakeAliPayPaymentSync() {
        final PaymentRequest paymentRequest = createAlipayPaymentRequest();
        
        try {
            paymentsClient.requestPaymentSync(paymentRequest);
            fail();
        } catch (Exception exception) {
            assertTrue(exception instanceof CheckoutApiException);
        }
    }

    @Test
    @Disabled("unavailable")
    void shouldMakeIdealPaymentSync() {
        final PaymentRequest paymentRequest = createIdealPaymentRequest();
        
        final PaymentResponse paymentResponse = paymentsClient.requestPaymentSync(paymentRequest);
        
        validatePaymentResponse(paymentResponse);
        validatePaymentDetailsSync(paymentResponse.getId(), PaymentSourceType.IDEAL);
    }

    @Disabled("payment method not supported")
    @Test
    void shouldMakeSofortPaymentSync() {
        final PaymentRequest paymentRequest = createSofortPaymentRequest();
        
        final PaymentResponse paymentResponse = paymentsClient.requestPaymentSync(paymentRequest);
        
        validatePaymentResponse(paymentResponse);
        validatePaymentDetailsSync(paymentResponse.getId(), PaymentSourceType.SOFORT);
    }

    @Disabled("preview")
    @Test
    void shouldMakeTamaraPaymentSync() {
        final CheckoutApi previewApi = createPreviewApi();
        final PaymentRequest paymentRequest = createTamaraPaymentRequest();

        final PaymentResponse response = previewApi.paymentsClient().requestPaymentSync(paymentRequest);
        
        validateTamaraPaymentResponse(response);
    }

    @Test
    void shouldMakeAfterPayPaymentSync() {
        final PaymentRequest paymentRequest = createAfterPayPaymentRequest();
        
        try {
            paymentsClient.requestPaymentSync(paymentRequest);
            fail();
        } catch (Exception exception) {
            assertTrue(exception instanceof CheckoutApiException);
        }
    }

    @Test
    @Disabled("unavailable")
    void shouldMakeBenefitPaymentSync() {
        final PaymentRequest paymentRequest = createBenefitPaymentRequest();
        checkErrorItemSync(() -> paymentsClient.requestPaymentSync(paymentRequest), PAYEE_NOT_ONBOARDED);
    }

    @Test
    void shouldMakeQPayPaymentSync() {
        final PaymentRequest paymentRequest = createQPayPaymentRequest();
        checkErrorItemSync(() -> paymentsClient.requestPaymentSync(paymentRequest), PAYEE_NOT_ONBOARDED);
    }

    @Test
    @Disabled("unavailable")
    void shouldMakeMbwayPaymentSync() {
        final PaymentRequest paymentRequest = createMbwayPaymentRequest();
        checkErrorItemSync(() -> paymentsClient.requestPaymentSync(paymentRequest), "cko_processing_channel_id_invalid");
    }

    @Test
    void shouldMakeEpsPaymentSync() {
        final PaymentRequest paymentRequest = createEpsPaymentRequest();
        checkErrorItemSync(() -> paymentsClient.requestPaymentSync(paymentRequest), PAYEE_NOT_ONBOARDED);
    }

    @Test
    void shouldMakeIllicadoPaymentSync() {
        final PaymentRequest paymentRequest = createIllicadoPaymentRequest();
        checkErrorItemSync(() -> paymentsClient.requestPaymentSync(paymentRequest), PAYEE_NOT_ONBOARDED);
    }

    @Test
    void shouldMakeGiropayPaymentSync() {
        final PaymentRequest paymentRequest = createGiropayPaymentRequest();
        checkErrorItemSync(() -> paymentsClient.requestPaymentSync(paymentRequest), PAYMENT_METHOD_NOT_SUPPORTED);
    }

    @Test
    void shouldMakePrzelewy24PaymentSync() {
        final PaymentRequest paymentRequest = createP24PaymentRequest();
        checkErrorItemSync(() -> paymentsClient.requestPaymentSync(paymentRequest), PAYEE_NOT_ONBOARDED);
    }

    @Test
    void shouldMakeKnetPaymentSync() {
        final PaymentRequest paymentRequest = createKnetPaymentRequest();
        
        final PaymentResponse paymentResponse = paymentsClient.requestPaymentSync(paymentRequest);
        
        validatePaymentResponse(paymentResponse);
    }

    @Test
    @Disabled("unavailable")
    void shouldMakeBancontactPaymentSync() {
        final PaymentRequest paymentRequest = createBancontactPaymentRequest();
        
        final PaymentResponse paymentResponse = paymentsClient.requestPaymentSync(paymentRequest);
        
        validatePaymentResponse(paymentResponse);
        validatePaymentDetailsSync(paymentResponse.getId(), PaymentSourceType.BANCONTACT);
    }

    @Test
    void shouldMakeMultiBancoPaymentSync() {
        final PaymentRequest paymentRequest = createMultiBancoPaymentRequest();
        checkErrorItemSync(() -> paymentsClient.requestPaymentSync(paymentRequest), PAYEE_NOT_ONBOARDED);
    }

    @Test
    void shouldMakePostFinancePaymentSync() {
        final PaymentRequest paymentRequest = createPostFinancePaymentRequest();
        checkErrorItemSync(() -> paymentsClient.requestPaymentSync(paymentRequest), PAYEE_NOT_ONBOARDED);
    }

    // 2026/01/16 DR - APM is currently unavailable, temporary skipping the stcPay failing tests
    @Disabled("APM is currently unavailable, temporary skipping the stcPay failing tests")
    @Test
    void shouldMakeStcPayPaymentSync() {
        final PaymentRequest paymentRequest = createStcPayPaymentRequest();
        checkErrorItemSync(() -> paymentsClient.requestPaymentSync(paymentRequest), "merchant_data_delegated_authentication_failed");
    }

    @Test
    void shouldMakeAlmaPaymentSync() {
        final PaymentRequest paymentRequest = createAlmaPaymentRequest();
        checkErrorItemSync(() -> paymentsClient.requestPaymentSync(paymentRequest), PAYEE_NOT_ONBOARDED);
    }

    @Test
    void shouldMakeFawryPaymentSync() {
        final PaymentRequest paymentRequest = createFawryPaymentRequest();
        checkErrorItemSync(() -> paymentsClient.requestPaymentSync(paymentRequest), PAYEE_NOT_ONBOARDED);
    }

    @Test
    void shouldMakeTrustlyPaymentSync() {
        final PaymentRequest paymentRequest = createTrustlyPaymentRequest();
        checkErrorItemSync(() -> paymentsClient.requestPaymentSync(paymentRequest), PAYEE_NOT_ONBOARDED);
    }

    @Test
    void shouldMakeCvConnectPaymentSync() {
        final PaymentRequest paymentRequest = createCvConnectPaymentRequest();
        checkErrorItemSync(() -> paymentsClient.requestPaymentSync(paymentRequest), PAYEE_NOT_ONBOARDED);
    }

    @Test
    void shouldMakeSepaV4PaymentSync() {
        final PaymentRequest paymentRequest = createSepaPaymentRequest();
        checkErrorItemSync(() -> paymentsClient.requestPaymentSync(paymentRequest), APM_SERVICE_UNAVAILABLE);
    }

    @Test
    void shouldMakeAchPaymentSync() {
        final PaymentRequest paymentRequest = createAchPaymentRequest();
        final PaymentResponse paymentResponse = paymentsClient.requestPaymentSync(paymentRequest);
        validateAchPaymentResponse(paymentResponse);
    }

    @Test
    void shouldMakeBizumPaymentSync() {
        final PaymentRequest paymentRequest = createBizumPaymentRequest();
        checkErrorItemSync(() -> paymentsClient.requestPaymentSync(paymentRequest), APM_SERVICE_UNAVAILABLE);
    }

    @Test
    void shouldMakeOctopusPaymentSync() {
        final PaymentRequest paymentRequest = createOctopusPaymentRequest();
        checkErrorItemSync(() -> paymentsClient.requestPaymentSync(paymentRequest), APM_CURRENCY_NOT_SUPPORTED);
    }

    @Test
    void shouldMakePlaidPaymentSync() {
        final PaymentRequest paymentRequest = createPlaidPaymentRequest();
        final PaymentResponse paymentResponse = paymentsClient.requestPaymentSync(paymentRequest);
        validatePlaidPaymentResponse(paymentResponse);
    }

    @Test
    void shouldMakeSequraPaymentSync() {
        final PaymentRequest paymentRequest = createSequraPaymentRequest();
        checkErrorItemSync(() -> paymentsClient.requestPaymentSync(paymentRequest), PAYEE_NOT_ONBOARDED);
    }

    // Common methods
    private PaymentRequest createAlipayPaymentRequest() {
        return createBasePaymentRequest(createAlipaySource(), Currency.EUR, 1000L)
                .reference(UUID.randomUUID().toString())
                .processingChannelId(System.getenv("CHECKOUT_PREVIOUS_PUBLIC_KEY"))
                .build();
    }

    private PaymentRequest createIdealPaymentRequest() {
        return createBasePaymentRequest(createIdealSource(), Currency.EUR, 1000L).build();
    }

    private PaymentRequest createSofortPaymentRequest() {
        return createBasePaymentRequest(createSofortSource(), Currency.EUR, 1000L).build();
    }

    private PaymentRequest createAfterPayPaymentRequest() {
        return createBasePaymentRequest(createAfterPaySource(), Currency.GBP, 10L).build();
    }

    private PaymentRequest createBenefitPaymentRequest() {
        return createBasePaymentRequest(new RequestBenefitSource(), Currency.BHD, 10L)
                .reference("reference")
                .build();
    }

    private PaymentRequest createQPayPaymentRequest() {
        return createBasePaymentRequest(createQPaySource(), Currency.QAR, 100L).build();
    }

    private PaymentRequest createMbwayPaymentRequest() {
        return createBasePaymentRequest(new RequestMbwaySource(), Currency.GBP, 100L)
                .reference(UUID.randomUUID().toString())
                .build();
    }

    private PaymentRequest createEpsPaymentRequest() {
        return createBasePaymentRequest(createEpsSource(), Currency.EUR, 10L).build();
    }

    private PaymentRequest createIllicadoPaymentRequest() {
        return createBasePaymentRequest(createIllicadoSource(), Currency.EUR, 10L).build();
    }

    private PaymentRequest createGiropayPaymentRequest() {
        return createBasePaymentRequest(createGiropaySource(), Currency.EUR, 100L)
                .description("Giropay example")
                .shipping(ShippingDetails.builder()
                        .address(createAddress())
                        .build())
                .build();
    }

    private PaymentRequest createP24PaymentRequest() {
        return createBasePaymentRequest(createP24Source(), Currency.PLN, 100L).build();
    }

    private PaymentRequest createKnetPaymentRequest() {
        return createBasePaymentRequest(createKnetSource(), Currency.KWD, 1000L).build();
    }

    private PaymentRequest createBancontactPaymentRequest() {
        return createBasePaymentRequest(createBancontactSource(), Currency.EUR, 100L).build();
    }

    private PaymentRequest createMultiBancoPaymentRequest() {
        return createBasePaymentRequest(createMultiBancoSource(), Currency.EUR, 10L).build();
    }

    private PaymentRequest createPostFinancePaymentRequest() {
        return createBasePaymentRequest(createPostFinanceSource(), Currency.EUR, 10L).build();
    }

    private PaymentRequest createStcPayPaymentRequest() {
        return createBasePaymentRequest(new RequestStcPaySource(), Currency.SAR, 10L)
                .reference(UUID.randomUUID().toString())
                .customer(createStcPayCustomer())
                .build();
    }

    private PaymentRequest createAlmaPaymentRequest() {
        return createBasePaymentRequest(createAlmaSource(), Currency.EUR, 10L).build();
    }

    private PaymentRequest createFawryPaymentRequest() {
        return createBasePaymentRequest(createFawrySource(), Currency.EGP, 10L).build();
    }

    private PaymentRequest createTrustlyPaymentRequest() {
        return createBasePaymentRequest(createTrustlySource(), Currency.EUR, 10L).build();
    }

    private PaymentRequest createCvConnectPaymentRequest() {
        return createBasePaymentRequest(createCvConnectSource(), Currency.EUR, 10L).build();
    }

    private PaymentRequest createSepaPaymentRequest() {
        return createBasePaymentRequest(createSepaSource(), Currency.EUR, 10L).build();
    }

    private PaymentRequest createAchPaymentRequest() {
        return createBasePaymentRequest(createAchSource(), Currency.USD, 10L)
                .processingChannelId(System.getenv("CHECKOUT_PROCESSING_CHANNEL_ID"))
                .successUrl("https://testing.checkout.com/success")
                .failureUrl("https://testing.checkout.com/failure")
                .build();
    }

    private PaymentRequest createBizumPaymentRequest() {
        return createBasePaymentRequest(createBizumSource(), Currency.EUR, 10L)
                .customer(createBizumCustomer())
                .processingChannelId(System.getenv("CHECKOUT_PROCESSING_CHANNEL_ID"))
                .successUrl("https://testing.checkout.com/success")
                .failureUrl("https://testing.checkout.com/failure")
                .build();
    }

    private PaymentRequest createOctopusPaymentRequest() {
        return createBasePaymentRequest(new RequestOctopusSource(), Currency.USD, 10L)
                .processingChannelId(System.getenv("CHECKOUT_PROCESSING_CHANNEL_ID"))
                .successUrl("https://testing.checkout.com/success")
                .failureUrl("https://testing.checkout.com/failure")
                .build();
    }

    private PaymentRequest createPlaidPaymentRequest() {
        return createBasePaymentRequest(createPlaidSource(), Currency.USD, 10L)
                .processingChannelId(System.getenv("CHECKOUT_PROCESSING_CHANNEL_ID"))
                .successUrl("https://testing.checkout.com/success")
                .failureUrl("https://testing.checkout.com/failure")
                .build();
    }

    private PaymentRequest createSequraPaymentRequest() {
        return createBasePaymentRequest(createSequraSource(), Currency.EUR, 10L)
                .successUrl("https://testing.checkout.com/success")
                .failureUrl("https://testing.checkout.com/failure")
                .build();
    }

    private PaymentRequest createTamaraPaymentRequest() {
        return PaymentRequest.builder()
                .source(createTamaraSource())
                .currency(Currency.SAR)
                .amount(10000L)
                .capture(true)
                .successUrl("https://testing.checkout.com/sucess")
                .failureUrl("https://testing.checkout.com/failure")
                .reference("ORD-5023-4E89")
                .processing(ProcessingSettings.builder().taxAmount(500L).shippingAmount(1000L).build())
                .processingChannelId("pc_zs5fqhybzc2e3jmq3efvybybpq")
                .customer(createTamaraCustomer())
                .items(Collections.singletonList(createTamaraProduct()))
                .build();
    }

    private PaymentRequest.PaymentRequestBuilder createBasePaymentRequest(AbstractRequestSource source, Currency currency, Long amount) {
        return PaymentRequest.builder()
                .source(source)
                .currency(currency)
                .amount(amount)
                .capture(true)
                .successUrl("https://testing.checkout.com/sucess")
                .failureUrl("https://testing.checkout.com/failure");
    }

    // Source builders
    private RequestAlipayPlusSource createAlipaySource() {
        return RequestAlipayPlusSource.requestAlipayPlusSource();
    }

    private RequestIdealSource createIdealSource() {
        return RequestIdealSource.builder()
                .description("ORD50234E89")
                .language("nl")
                .build();
    }

    private RequestSofortSource createSofortSource() {
        return RequestSofortSource.builder().build();
    }

    private RequestAfterPaySource createAfterPaySource() {
        return RequestAfterPaySource.builder()
                .accountHolder(AccountHolder.builder().build())
                .build();
    }

    private RequestQPaySource createQPaySource() {
        return RequestQPaySource.builder()
                .description("QPay Demo Payment")
                .language("en")
                .quantity(1)
                .nationalId("070AYY010BU234M")
                .build();
    }

    private RequestEpsSource createEpsSource() {
        return RequestEpsSource.builder()
                .accountHolder(getAccountHolder())
                .purpose("Mens black t-shirt L")
                .build();
    }

    private RequestIllicadoSource createIllicadoSource() {
        return RequestIllicadoSource.builder()
                .billingAddress(createStandardAddress())
                .build();
    }

    private RequestGiropaySource createGiropaySource() {
        return RequestGiropaySource.builder()
                .accountHolder(AccountHolder.builder()
                        .firstName("Firstname")
                        .lastName("Lastname")
                        .build())
                .build();
    }

    private RequestP24Source createP24Source() {
        return RequestP24Source.builder()
                .paymentCountry(CountryCode.PL)
                .accountHolderName("Bruce Wayne")
                .accountHolderEmail("bruce@wayne-enterprises.com")
                .billingDescriptor("P24 Demo Payment")
                .build();
    }

    private RequestKnetSource createKnetSource() {
        return RequestKnetSource.builder()
                .language("en")
                .paymentMethodDetails(PaymentMethodDetails.builder()
                        .displayName("name")
                        .type("type")
                        .network("card_network")
                        .build())
                .build();
    }

    private RequestBancontactSource createBancontactSource() {
        return RequestBancontactSource.builder()
                .paymentCountry(CountryCode.BE)
                .accountHolderName("Bruce Wayne")
                .billingDescriptor("CKO Demo - bancontact")
                .build();
    }

    private RequestMultiBancoSource createMultiBancoSource() {
        return RequestMultiBancoSource.builder()
                .paymentCountry(CountryCode.PT)
                .accountHolderName("Bruce Wayne")
                .billingDescriptor("Multibanco Demo Payment")
                .build();
    }

    private RequestPostFinanceSource createPostFinanceSource() {
        return RequestPostFinanceSource.builder()
                .paymentCountry(CountryCode.CH)
                .accountHolderName("Bruce Wayne")
                .billingDescriptor("PostFinance Demo Payment")
                .build();
    }

    private RequestAlmaSource createAlmaSource() {
        return RequestAlmaSource.builder()
                .billingAddress(createAddress())
                .build();
    }

    private RequestFawrySource createFawrySource() {
        return RequestFawrySource.builder()
                .description("Fawry Demo Payment")
                .customerMobile("01058375055")
                .customerEmail("bruce@wayne-enterprises.com")
                .products(Collections.singletonList(RequestFawrySource.Product.builder()
                        .id("0123456789")
                        .quantity(1L)
                        .price(10L)
                        .description("Fawry Demo Product")
                        .build()))
                .build();
    }

    private RequestTrustlySource createTrustlySource() {
        return RequestTrustlySource.builder()
                .billingAddress(createAddress())
                .build();
    }

    private RequestCvConnectSource createCvConnectSource() {
        return RequestCvConnectSource.builder()
                .billingAddress(createAddress())
                .build();
    }

    private RequestSepaSource createSepaSource() {
        return RequestSepaSource.builder()
                .country(CountryCode.ES)
                .accountNumber("HU93116000060000000012345676")
                .bankCode("37040044")
                .currency(Currency.EUR)
                .mandateId("man_12321233211")
                .dateOfSignature("2023-01-01")
                .accountHolder(createSepaAccountHolder())
                .build();
    }

    private RequestAchSource createAchSource() {
        return RequestAchSource.builder()
                .accountType(AccountType.SAVINGS)
                .country(CountryCode.GB)
                .accountNumber("8784738748973829")
                .bankCode("BANK")
                .accountHolder(createAchAccountHolder())
                .build();
    }

    private RequestBizumSource createBizumSource() {
        return RequestBizumSource.builder()
                .mobileNumber("+447700900986")
                .build();
    }

    private RequestPlaidSource createPlaidSource() {
        return RequestPlaidSource.builder()
                .token("token")
                .accountHolder(createPlaidAccountHolder())
                .build();
    }

    private RequestSequraSource createSequraSource() {
        return RequestSequraSource.builder()
                .billingAddress(createAddress())
                .build();
    }

    private RequestTamaraSource createTamaraSource() {
        final RequestTamaraSource source = new RequestTamaraSource();
        source.setBillingAddress(createStandardAddress());
        return source;
    }

    // Account holder builders
    private AccountHolder createSepaAccountHolder() {
        return AccountHolder.builder()
                .firstName("Name")
                .lastName("Last")
                .billingAddress(Address.builder()
                        .addressLine1("Address Line 1")
                        .addressLine2("Max_10_c__")
                        .city("City")
                        .zip("12345")
                        .country(CountryCode.GB)
                        .build())
                .build();
    }

    private AccountHolder createAchAccountHolder() {
        return AccountHolder.builder()
                .firstName("John")
                .lastName("Doe")
                .billingAddress(createAddress())
                .phone(createPhone())
                .build();
    }

    private AccountHolder createPlaidAccountHolder() {
        return AccountHolder.builder()
                .firstName("John")
                .lastName("Doe")
                .phone(createPhone())
                .billingAddress(createAddress())
                .type(AccountHolderType.INDIVIDUAL)
                .accountNameInquiry(false)
                .build();
    }

    // Address builders
    private Address createStandardAddress() {
        final Address address = new Address();
        address.setAddressLine1("Cecilia Chapman");
        address.setAddressLine2("711-2880 Nulla St.");
        address.setCity("Mankato");
        address.setState("Mississippi");
        address.setZip("96522");
        address.setCountry(CountryCode.SA);
        return address;
    }

    // Customer builders
    private PaymentCustomerRequest createStcPayCustomer() {
        return PaymentCustomerRequest.builder()
                .email(TestHelper.generateRandomEmail())
                .name("Louis Smith")
                .phone(createPhone())
                .build();
    }

    private PaymentCustomerRequest createBizumCustomer() {
        return PaymentCustomerRequest.builder()
                .email(TestHelper.generateRandomEmail())
                .name("Louis Smith")
                .phone(createPhone())
                .build();
    }

    private CustomerRequest createTamaraCustomer() {
        return new CustomerRequest("c.chapman@example.com", "Cecilia Chapman",
                Phone.builder().countryCode("+966").number("113 496 0000").build());
    }

    private ProductRequest createTamaraProduct() {
        return ProductRequest.builder()
                .name("Item name")
                .quantity(3L)
                .unitPrice(100L)
                .totalAmount(100L)
                .taxAmount(19L)
                .discountAmount(2L)
                .reference("some description about item")
                .imageUrl("https://some_s3bucket.com")
                .url("https://some.website.com/item")
                .sku("123687000111")
                .build();
    }

    // API builders
    private CheckoutApi createPreviewApi() {
        return CheckoutSdk.builder()
                .oAuth()
                .clientCredentials(
                        requireNonNull(System.getenv("CHECKOUT_PREVIEW_OAUTH_CLIENT_ID")),
                        requireNonNull(System.getenv("CHECKOUT_PREVIEW_OAUTH_CLIENT_SECRET")))
                .environment(Environment.SANDBOX)
                .build();
    }

    // Validation methods
    private void validatePaymentResponse(PaymentResponse paymentResponse) {
        assertNotNull(paymentResponse);
    }

    private void validatePaymentDetails(String paymentId, PaymentSourceType expectedType) throws ExecutionException, InterruptedException {
        final CompletableFuture<GetPaymentResponse> future = paymentsClient.getPayment(paymentId);
        final GetPaymentResponse paymentDetails = future.get();
        
        assertNotNull(paymentDetails);
        assertTrue(paymentDetails.getSource() instanceof AlternativePaymentSourceResponse);
        assertEquals(expectedType, paymentDetails.getSource().getType());
    }

    private void validatePaymentDetailsSync(String paymentId, PaymentSourceType expectedType) {
        final GetPaymentResponse paymentDetails = paymentsClient.getPaymentSync(paymentId);
        
        assertNotNull(paymentDetails);
        assertTrue(paymentDetails.getSource() instanceof AlternativePaymentSourceResponse);
        assertEquals(expectedType, paymentDetails.getSource().getType());
    }

    private void validateTamaraPaymentResponse(PaymentResponse response) {
        assertNotNull(response);
        assertNotNull(response.getId());
        assertNotNull(response.getReference());
        assertNotNull(response.getLinks());
        assertNotNull(response.getCustomer());
        assertNotNull(response.getCustomer().getId());
        assertNotNull(response.getCustomer().getName());
        assertNotNull(response.getCustomer().getEmail());
        assertNotNull(response.getCustomer().getPhone());
        assertNotNull(response.getProcessing().getPartnerPaymentId());
        assertEquals(PaymentStatus.PENDING, response.getStatus());
    }

    private void validateAchPaymentResponse(PaymentResponse paymentResponse) {
        assertNotNull(paymentResponse);
        assertEquals(PaymentStatus.PENDING, paymentResponse.getStatus());
        assertNotNull(paymentResponse.getId());
        assertTrue(paymentResponse.getSource() instanceof AlternativePaymentSourceResponse);

        final AlternativePaymentSourceResponse sourceResponse = (AlternativePaymentSourceResponse) paymentResponse.getSource();
        assertEquals(PaymentSourceType.ACH, sourceResponse.getType());
    }

    private void validatePlaidPaymentResponse(PaymentResponse paymentResponse) {
        assertNotNull(paymentResponse);
        assertNotNull(paymentResponse.getId());
        assertEquals(202, paymentResponse.getHttpStatusCode());
    }

    private void checkErrorItemSync(Runnable operation, String expectedError) {
        try {
            operation.run();
            fail();
        } catch (Exception exception) {
            assertTrue(exception instanceof CheckoutApiException);
            @SuppressWarnings("unchecked")
            final List<String> error_codes = (List<String>) ((CheckoutApiException) exception).getErrorDetails().get("error_codes");
            assertThat(error_codes, hasItem(expectedError));
        }
    }
}