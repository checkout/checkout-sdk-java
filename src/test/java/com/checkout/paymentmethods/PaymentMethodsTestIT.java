package com.checkout.paymentmethods;

import com.checkout.CheckoutApiException;
import com.checkout.PlatformType;
import com.checkout.SandboxTestFixture;
import com.checkout.paymentmethods.requests.PaymentMethodsQuery;
import com.checkout.paymentmethods.entities.PaymentMethod;
import com.checkout.paymentmethods.responses.PaymentMethodsResponse;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PaymentMethodsTestIT extends SandboxTestFixture {

    private static final String VALID_PROCESSING_CHANNEL_ID = "pc_5jp2az55l3cuths25t5p3xhwru";

    PaymentMethodsTestIT() {
        super(PlatformType.DEFAULT_OAUTH);
    }

    @Test
    void shouldGetPaymentMethods() throws Exception {
        final PaymentMethodsQuery query = PaymentMethodsQuery.builder()
                .processingChannelId(VALID_PROCESSING_CHANNEL_ID)
                .build();
        
        final PaymentMethodsResponse response = blocking(() -> checkoutApi.paymentMethodsClient()
                                                                    .getPaymentMethods(query));

        validateGetAvailablePaymentMethodsResponse(response);
    }

    @Test
    void shouldGetPaymentMethodsSync() {
        final PaymentMethodsQuery query = PaymentMethodsQuery.builder()
                .processingChannelId(VALID_PROCESSING_CHANNEL_ID)
                .build();
        
        final PaymentMethodsResponse response = checkoutApi.paymentMethodsClient().getPaymentMethodsSync(query);

        validateGetAvailablePaymentMethodsResponse(response);
    }

    // Common methods
    private void validateGetAvailablePaymentMethodsResponse(final PaymentMethodsResponse response) {
        assertNotNull(response, "Response should not be null");
        assertNotNull(response.getMethods(), "Methods should not be null");
        assertTrue(response.getMethods().size() > 0, "Methods count should be greater than 0");

        for (PaymentMethod method : response.getMethods()) {
            assertNotNull(method, "Method should not be null");
            assertNotNull(method.getType(), "Method type should not be null");
        }
    }
}