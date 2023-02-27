package com.checkout.financial;

import com.checkout.PlatformType;
import com.checkout.payments.AbstractPaymentsTestIT;
import com.checkout.payments.response.PaymentResponse;
import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class FinancialTestIT extends AbstractPaymentsTestIT {

    public FinancialTestIT() {
        super(PlatformType.DEFAULT_OAUTH);
    }

    @Test
    @Disabled("unstable")
    public void shouldQueryFinancialActions() {
        // payment
        final PaymentResponse paymentResponse = makeCardPayment(false);
        assertNotNull(paymentResponse.getLink("capture"));

        // capture
        capturePayment(paymentResponse.getId());

        final FinancialActionsQueryFilter query = FinancialActionsQueryFilter.builder()
                                                        .limit(5)
                                                        .paymentId(paymentResponse.getId())
                                                        .build();

        final FinancialActionsQueryResponse response = blocking(
                () -> checkoutApi.financialClient().query(query),
                new HasActions(),
                5L);

        assertNotNull(response);
        assertNotNull(response.getData());
        assertTrue(response.getData().size() > 0);
        response.getData().forEach(action -> {
            assertNotNull(action.getActionId());
            assertEquals(paymentResponse.getId(), action.getPaymentId());
        });
    }

    protected static class HasActions extends BaseMatcher<FinancialActionsQueryResponse> {

        public HasActions() {}

        @Override
        public boolean matches(final Object actual) {
            if (!(actual instanceof FinancialActionsQueryResponse)) {
                throw new IllegalStateException("not a FinancialActionsQueryResponse!");
            }
            return ((FinancialActionsQueryResponse) actual).getCount() > 0;
        }

        @Override
        public void describeTo(final Description description) {
            throw new UnsupportedOperationException();
        }

    }
}
