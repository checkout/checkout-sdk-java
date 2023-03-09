package com.checkout.payments;


import com.checkout.CheckoutApi;
import com.checkout.CheckoutApiException;
import com.checkout.CheckoutSdk;
import com.checkout.Environment;
import com.checkout.TestHelper;
import com.checkout.common.AccountHolder;
import com.checkout.common.Address;
import com.checkout.common.CountryCode;
import com.checkout.common.Currency;
import com.checkout.common.CustomerRequest;
import com.checkout.common.PaymentSourceType;
import com.checkout.common.Phone;
import com.checkout.payments.request.PaymentCustomerRequest;
import com.checkout.payments.request.PaymentRequest;
import com.checkout.payments.request.source.apm.*;
import com.checkout.payments.response.GetPaymentResponse;
import com.checkout.payments.response.PaymentResponse;
import com.checkout.payments.response.source.AlternativePaymentSourceResponse;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.UUID;

import static com.checkout.TestHelper.createAddress;
import static com.checkout.TestHelper.createPhone;
import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

class RequestApmPaymentsIT extends AbstractPaymentsTestIT {

    @Test
    void shouldMakeAliPayPayment() throws InterruptedException {
        RequestAlipayPlusSource source = RequestAlipayPlusSource.requestAlipayPlusCNSource();
        source = RequestAlipayPlusSource.requestAlipayPlusGCashSource();
        source = RequestAlipayPlusSource.requestAlipayPlusDanaSource();
        source = RequestAlipayPlusSource.requestAlipayPlusKakaoPaySource();
        source = RequestAlipayPlusSource.requestAlipayPlusTrueMoneySource();
        source = RequestAlipayPlusSource.requestAlipayPlusTNGSource();
        source = RequestAlipayPlusSource.requestAlipayPlusSource();
        final PaymentRequest paymentRequest = PaymentRequest.builder()
                .source(source)
                .reference(UUID.randomUUID().toString())
                .processingChannelId("pc_5jp2az55l3cuths25t5p3xhwru")
                .currency(Currency.EUR)
                .amount(1000L)
                .capture(true)
                .successUrl("https://testing.checkout.com/sucess")
                .failureUrl("https://testing.checkout.com/failure")
                .build();

        try {
            paymentsClient.requestPayment(paymentRequest).get();
            fail();
        } catch (Exception exception) {
            assertTrue(exception.getCause() instanceof CheckoutApiException);

        }


    }

    @Test
    void shouldMakeIdealPayment() {
        final PaymentRequest paymentRequest = PaymentRequest.builder()
                .source(RequestIdealSource.builder()
                        .bic("INGBNL2A")
                        .description("ORD50234E89")
                        .language("nl")
                        .build())
                .currency(Currency.EUR)
                .amount(1000L)
                .capture(true)
                .successUrl("https://testing.checkout.com/sucess")
                .failureUrl("https://testing.checkout.com/failure")
                .build();

        final PaymentResponse paymentResponse = blocking(() -> paymentsClient.requestPayment(paymentRequest));
        assertNotNull(paymentResponse);

        final GetPaymentResponse paymentDetails = blocking(() -> paymentsClient.getPayment(paymentResponse.getId()));
        assertNotNull(paymentDetails);
        assertTrue(paymentDetails.getSource() instanceof AlternativePaymentSourceResponse);
        assertEquals(PaymentSourceType.IDEAL, paymentDetails.getSource().getType());
    }


    @Test
    void shouldMakeSofortPayment() {
        final PaymentRequest paymentRequest = PaymentRequest.builder()
                .source(RequestSofortSource.builder()
                        .build())
                .currency(Currency.EUR)
                .amount(1000L)
                .capture(true)
                .successUrl("https://testing.checkout.com/sucess")
                .failureUrl("https://testing.checkout.com/failure")
                .build();

        final PaymentResponse paymentResponse = blocking(() -> paymentsClient.requestPayment(paymentRequest));
        assertNotNull(paymentResponse);

        final GetPaymentResponse paymentDetails = blocking(() -> paymentsClient.getPayment(paymentResponse.getId()));
        assertNotNull(paymentDetails);
        assertTrue(paymentDetails.getSource() instanceof AlternativePaymentSourceResponse);
        assertEquals(PaymentSourceType.SOFORT, paymentDetails.getSource().getType());
    }

    @Disabled("preview")
    @Test
    void shouldMakeTamaraPayment() {

        final CheckoutApi previewApi = CheckoutSdk.builder()
                .oAuth()
                .clientCredentials(
                        requireNonNull(System.getenv("CHECKOUT_PREVIEW_OAUTH_CLIENT_ID")),
                        requireNonNull(System.getenv("CHECKOUT_PREVIEW_OAUTH_CLIENT_SECRET")))
                .environment(Environment.SANDBOX)
                .build();

        final Address billingAddress = new Address();
        billingAddress.setAddressLine1("Cecilia Chapman");
        billingAddress.setAddressLine2("711-2880 Nulla St.");
        billingAddress.setCity("Mankato");
        billingAddress.setState("Mississippi");
        billingAddress.setZip("96522");
        billingAddress.setCountry(CountryCode.SA);

        final RequestTamaraSource source = new RequestTamaraSource();
        source.setBillingAddress(billingAddress);

        final PaymentRequest request = PaymentRequest.builder()
                .source(source)
                .currency(Currency.SAR)
                .amount(10000L)
                .capture(true)
                .successUrl("https://testing.checkout.com/sucess")
                .failureUrl("https://testing.checkout.com/failure")
                .reference("ORD-5023-4E89")
                .processing(ProcessingSettings.builder().taxAmount(500L).shippingAmount(1000L).build())
                .processingChannelId("pc_zs5fqhybzc2e3jmq3efvybybpq")
                .customer(new CustomerRequest(null, "c.chapman@example.com", "Cecilia Chapman",
                        Phone.builder().countryCode("+966").number("113 496 0000").build()))
                .items(Collections.singletonList(Product.builder()
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
                        .build()))
                .build();

        final PaymentResponse response = blocking(() -> previewApi.paymentsClient().requestPayment(request));
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

    @Test
    void shouldMakeAfterPayPayment() {
        final PaymentRequest paymentRequest = PaymentRequest.builder()
                .source(RequestAfterPaySource.builder()
                        .accountHolder(AccountHolder.builder()
                                .build())
                        .build())
                .currency(Currency.GBP)
                .amount(10L)
                .capture(true)
                .successUrl("https://testing.checkout.com/sucess")
                .failureUrl("https://testing.checkout.com/failure")
                .build();

        try {
            paymentsClient.requestPayment(paymentRequest).get();
            fail();
        } catch (Exception exception) {
            assertTrue(exception.getCause() instanceof CheckoutApiException);
        }
    }

    @Test
    void shouldMakeBenefitPayment() {
        final PaymentRequest paymentRequest = PaymentRequest.builder()
                .source(new RequestBenefitSource())
                .currency(Currency.BHD)
                .reference("reference")
                .amount(10L)
                .capture(true)
                .successUrl("https://testing.checkout.com/sucess")
                .failureUrl("https://testing.checkout.com/failure")
                .build();

        checkErrorItem(() -> paymentsClient.requestPayment(paymentRequest), PAYEE_NOT_ONBOARDED);
    }

    @Test
    void shouldMakeQPayPayment() {
        final PaymentRequest paymentRequest = PaymentRequest.builder()
                .source(RequestQPaySource.builder()
                        .description("QPay Demo Payment")
                        .language("en")
                        .quantity(1)
                        .nationalId("070AYY010BU234M")
                        .build())
                .currency(Currency.QAR)
                .amount(100L)
                .capture(true)
                .successUrl("https://testing.checkout.com/sucess")
                .failureUrl("https://testing.checkout.com/failure")
                .build();

        checkErrorItem(() -> paymentsClient.requestPayment(paymentRequest), PAYEE_NOT_ONBOARDED);
    }

    @Test
    @Disabled("unavailable")
    void shouldMakeMbwayPayment() {
        final PaymentRequest paymentRequest = PaymentRequest.builder()
                .source(new RequestMbwaySource())
                .currency(Currency.GBP)
                .amount(100L)
                .capture(true)
                .reference(UUID.randomUUID().toString())
                .successUrl("https://testing.checkout.com/sucess")
                .failureUrl("https://testing.checkout.com/failure")
                .build();

        checkErrorItem(() -> paymentsClient.requestPayment(paymentRequest), "cko_processing_channel_id_invalid");
    }

    @Test
    void shouldMakeEpsPayment() {
        final PaymentRequest paymentRequest = PaymentRequest.builder()
                .source(RequestEpsSource.builder()
                        .purpose("Mens black t-shirt L")
                        .build())
                .currency(Currency.EUR)
                .amount(10L)
                .capture(true)
                .successUrl("https://testing.checkout.com/sucess")
                .failureUrl("https://testing.checkout.com/failure")
                .build();

        checkErrorItem(() -> paymentsClient.requestPayment(paymentRequest), PAYEE_NOT_ONBOARDED);
    }

    @Test
    void shouldMakeIllicadoPayment() {
        final Address billingAddress = new Address();
        billingAddress.setAddressLine1("Cecilia Chapman");
        billingAddress.setAddressLine2("711-2880 Nulla St.");
        billingAddress.setCity("Mankato");
        billingAddress.setState("Mississippi");
        billingAddress.setZip("96522");
        billingAddress.setCountry(CountryCode.SA);

        final PaymentRequest paymentRequest = PaymentRequest.builder()
                .source(RequestIllicadoSource.builder()
                        .billingAddress(billingAddress)
                        .build())
                .currency(Currency.EUR)
                .amount(10L)
                .capture(true)
                .successUrl("https://testing.checkout.com/sucess")
                .failureUrl("https://testing.checkout.com/failure")
                .build();

        checkErrorItem(() -> paymentsClient.requestPayment(paymentRequest), PAYEE_NOT_ONBOARDED);
    }

    @Test
    @Disabled("unstable")
    void shouldMakeGiropayPayment() {
        final PaymentRequest paymentRequest = PaymentRequest.builder()
                .source(RequestGiropaySource.builder()
                        .purpose("CKO Giropay test")
                        .build())
                .currency(Currency.EUR)
                .amount(100L)
                .capture(true)
                .successUrl("https://testing.checkout.com/sucess")
                .failureUrl("https://testing.checkout.com/failure")
                .build();

        checkErrorItem(() -> paymentsClient.requestPayment(paymentRequest), PAYEE_NOT_ONBOARDED);
    }

    @Test
    void shouldMakePrzelewy24Payment() {
        final PaymentRequest paymentRequest = PaymentRequest.builder()
                .source(RequestP24Source.builder()
                        .paymentCountry(CountryCode.PL)
                        .accountHolderName("Bruce Wayne")
                        .accountHolderEmail("bruce@wayne-enterprises.com")
                        .billingDescriptor("P24 Demo Payment")
                        .build())
                .currency(Currency.PLN)
                .amount(100L)
                .capture(true)
                .successUrl("https://testing.checkout.com/sucess")
                .failureUrl("https://testing.checkout.com/failure")
                .build();

        checkErrorItem(() -> paymentsClient.requestPayment(paymentRequest), PAYEE_NOT_ONBOARDED);
    }

    @Test
    void shouldMakeKnetPayment() {
        final PaymentRequest paymentRequest = PaymentRequest.builder()
                .source(RequestKnetSource.builder()
                        .language("en")
                        .build())
                .currency(Currency.KWD)
                .amount(1000L)
                .capture(true)
                .successUrl("https://testing.checkout.com/sucess")
                .failureUrl("https://testing.checkout.com/failure")
                .build();

        checkErrorItem(() -> paymentsClient.requestPayment(paymentRequest), PAYEE_NOT_ONBOARDED);
    }

    @Test
    void shouldMakeBancontactPayment() {
        final PaymentRequest paymentRequest = PaymentRequest.builder()
                .source(RequestBancontactSource.builder()
                        .paymentCountry(CountryCode.BE)
                        .accountHolderName("Bruce Wayne")
                        .billingDescriptor("CKO Demo - bancontact")
                        .build())
                .currency(Currency.EUR)
                .amount(100L)
                .capture(true)
                .successUrl("https://testing.checkout.com/sucess")
                .failureUrl("https://testing.checkout.com/failure")
                .build();

        checkErrorItem(() -> paymentsClient.requestPayment(paymentRequest), PAYEE_NOT_ONBOARDED);
    }

    @Test
    void shouldMakeMultiBancoPayment() {
        final PaymentRequest paymentRequest = PaymentRequest.builder()
                .source(RequestMultiBancoSource.builder()
                        .paymentCountry(CountryCode.PT)
                        .accountHolderName("Bruce Wayne")
                        .billingDescriptor("Multibanco Demo Payment")
                        .build())
                .currency(Currency.EUR)
                .amount(10L)
                .capture(true)
                .successUrl("https://testing.checkout.com/sucess")
                .failureUrl("https://testing.checkout.com/failure")
                .build();

        checkErrorItem(() -> paymentsClient.requestPayment(paymentRequest), PAYEE_NOT_ONBOARDED);
    }

    @Test
    void shouldMakePostFinancePayment() {
        final PaymentRequest paymentRequest = PaymentRequest.builder()
                .source(RequestPostFinanceSource.builder()
                        .paymentCountry(CountryCode.CH)
                        .accountHolderName("Bruce Wayne")
                        .billingDescriptor("PostFinance Demo Payment")
                        .build())
                .currency(Currency.EUR)
                .amount(10L)
                .capture(true)
                .successUrl("https://testing.checkout.com/sucess")
                .failureUrl("https://testing.checkout.com/failure")
                .build();

        checkErrorItem(() -> paymentsClient.requestPayment(paymentRequest), PAYEE_NOT_ONBOARDED);
    }

    @Test
    void shouldMakeStcPayPayment() {
        final PaymentRequest paymentRequest = PaymentRequest.builder()
                .source(new RequestStcPaySource())
                .currency(Currency.SAR)
                .amount(10L)
                .capture(true)
                .reference(UUID.randomUUID().toString())
                .customer(PaymentCustomerRequest.builder()
                        .email(TestHelper.generateRandomEmail())
                        .name("Louis Smith")
                        .phone(createPhone())
                        .build())
                .successUrl("https://testing.checkout.com/sucess")
                .failureUrl("https://testing.checkout.com/failure")
                .build();

        checkErrorItem(() -> paymentsClient.requestPayment(paymentRequest), "merchant_data_delegated_authentication_failed");
    }

    @Test
    void shouldMakeAlmaPayment() {
        final PaymentRequest paymentRequest = PaymentRequest.builder()
                .source(RequestAlmaSource.builder()
                        .billingAddress(createAddress())
                        .build())
                .currency(Currency.EUR)
                .amount(10L)
                .capture(true)
                .successUrl("https://testing.checkout.com/sucess")
                .failureUrl("https://testing.checkout.com/failure")
                .build();

        checkErrorItem(() -> paymentsClient.requestPayment(paymentRequest), PAYEE_NOT_ONBOARDED);
    }

    @Test
    void shouldMakeKlarnaPayment() {
        final PaymentRequest paymentRequest = PaymentRequest.builder()
                .source(RequestKlarnaSource.builder()
                        .accountHolder(TestHelper.getAccountHolder())
                        .build())
                .currency(Currency.EUR)
                .amount(10L)
                .capture(true)
                .successUrl("https://testing.checkout.com/sucess")
                .failureUrl("https://testing.checkout.com/failure")
                .build();

        checkErrorItem(() -> paymentsClient.requestPayment(paymentRequest), APM_SERVICE_UNAVAILABLE);
    }

    @Test
    void shouldMakePayPalPayment() {
        final PaymentRequest paymentRequest = PaymentRequest.builder()
                .source(RequestPayPalSource.builder()
                        .plan(BillingPlan.builder()
                                .type(BillingPlanType.CHANNEL_INITIATED_BILLING_SINGLE_AGREEMENT)
                                .immutableShippingAddress(false)
                                .skipShippingAddress(true)
                                .build())
                        .build())
                .currency(Currency.EUR)
                .amount(10L)
                .capture(true)
                .successUrl("https://testing.checkout.com/sucess")
                .failureUrl("https://testing.checkout.com/failure")
                .items(Collections.singletonList(Product.builder()
                        .name("laptop")
                        .unitPrice(10L)
                        .quantity(1L)
                        .build()))
                .build();

        checkErrorItem(() -> paymentsClient.requestPayment(paymentRequest), PAYEE_NOT_ONBOARDED);
    }

    @Test
    void shouldMakeFawryPayment() {
        final PaymentRequest paymentRequest = PaymentRequest.builder()
                .source(RequestFawrySource.builder()
                        .description("Fawry Demo Payment")
                        .customerMobile("01058375055")
                        .customerEmail("bruce@wayne-enterprises.com")
                        .products(Collections.singletonList(RequestFawrySource.Product.builder()
                                .id("0123456789")
                                .quantity(1L)
                                .price(10L)
                                .description("Fawry Demo Product")
                                .build()))
                        .build())
                .currency(Currency.EGP)
                .amount(10L)
                .capture(true)
                .successUrl("https://testing.checkout.com/sucess")
                .failureUrl("https://testing.checkout.com/failure")
                .build();

        checkErrorItem(() -> paymentsClient.requestPayment(paymentRequest), PAYEE_NOT_ONBOARDED);
    }

    @Test
    void shouldMakeTrustlyPayment() {
        final PaymentRequest paymentRequest = PaymentRequest.builder()
                .source(RequestTrustlySource.builder()
                        .billingAddress(createAddress())
                        .build())
                .currency(Currency.EUR)
                .amount(10L)
                .capture(true)
                .successUrl("https://testing.checkout.com/sucess")
                .failureUrl("https://testing.checkout.com/failure")
                .build();

        checkErrorItem(() -> paymentsClient.requestPayment(paymentRequest), PAYEE_NOT_ONBOARDED);
    }

    @Test
    void shouldMakeCvConnectPayment() {
        final PaymentRequest paymentRequest = PaymentRequest.builder()
                .source(RequestCvConnectSource.builder()
                        .billingAddress(createAddress())
                        .build())
                .currency(Currency.EUR)
                .amount(10L)
                .capture(true)
                .successUrl("https://testing.checkout.com/sucess")
                .failureUrl("https://testing.checkout.com/failure")
                .build();

        checkErrorItem(() -> paymentsClient.requestPayment(paymentRequest), PAYEE_NOT_ONBOARDED);
    }

    @Test
    void shouldMakeSepaV4Payment() {
        final PaymentRequest paymentRequest = PaymentRequest.builder()
                .source(RequestSepaSource.builder()
                        .country(CountryCode.ES)
                        .accountNumber("HU93116000060000000012345676")
                        .bankCode("37040044")
                        .currency(Currency.EUR)
                        .mandateId("man_12321233211")
                        .dateOfSignature("2023-01-01")
                        .accountHolder(AccountHolder.builder()
                                .firstName("Name")
                                .lastName("Last")
                                .billingAddress(Address.builder()
                                        .addressLine1("Address Line 1")
                                        .addressLine2("Address Line 2")
                                        .city("City")
                                        .zip("12345")
                                        .country(CountryCode.GB)
                                        .build())
                                .build())
                        .build())
                .currency(Currency.EUR)
                .amount(10L)
                .capture(true)
                .successUrl("https://testing.checkout.com/sucess")
                .failureUrl("https://testing.checkout.com/failure")
                .build();

        checkErrorItem(() -> paymentsClient.requestPayment(paymentRequest), PAYEE_NOT_ONBOARDED);
    }
}
