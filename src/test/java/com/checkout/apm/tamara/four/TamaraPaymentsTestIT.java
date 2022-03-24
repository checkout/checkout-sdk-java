package com.checkout.apm.tamara.four;

import com.checkout.CheckoutSdk;
import com.checkout.Environment;
import com.checkout.PlatformType;
import com.checkout.SandboxTestFixture;
import com.checkout.common.Address;
import com.checkout.common.CountryCode;
import com.checkout.common.Currency;
import com.checkout.common.CustomerRequest;
import com.checkout.common.Phone;
import com.checkout.common.four.Product;
import com.checkout.four.CheckoutApi;
import com.checkout.payments.PaymentStatus;
import com.checkout.payments.ProcessingSettings;
import com.checkout.payments.four.request.PaymentRequest;
import com.checkout.payments.four.request.source.apm.RequestTamaraSource;
import com.checkout.payments.four.response.PaymentResponse;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.Collections;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class TamaraPaymentsTestIT extends SandboxTestFixture {

    TamaraPaymentsTestIT() {
        super(PlatformType.FOUR);
    }

    @Disabled("preview")
    @Test
    void shouldMakeTamaraPayment() {

        final CheckoutApi previewApi = CheckoutSdk.fourSdk()
                .oAuth()
                .clientCredentials(
                        requireNonNull(System.getenv("CHECKOUT_FOUR_PREVIEW_OAUTH_CLIENT_ID")),
                        requireNonNull(System.getenv("CHECKOUT_FOUR_PREVIEW_OAUTH_CLIENT_SECRET")))
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
        assertEquals(PaymentStatus.PENDING, response.getStatus());

    }

}
