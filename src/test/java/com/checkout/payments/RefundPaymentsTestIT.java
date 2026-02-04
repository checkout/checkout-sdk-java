package com.checkout.payments;

import static com.checkout.CardSourceHelper.getCardSourcePayment;
import static com.checkout.CardSourceHelper.getCorporateSender;
import static com.checkout.CardSourceHelper.getRequestCardSource;
import static com.checkout.TestHelper.createAddress;
import static com.checkout.TestHelper.getAccountHolder;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.Test;

import com.checkout.common.AccountType;
import com.checkout.common.BankDetails;
import com.checkout.common.CountryCode;
import com.checkout.common.Destination;
import com.checkout.payments.request.PaymentRequest;
import com.checkout.payments.request.RefundOrder;
import com.checkout.payments.request.source.RequestCardSource;
import com.checkout.payments.response.PaymentResponse;
import com.checkout.payments.sender.PaymentCorporateSender;

class RefundPaymentsTestIT extends AbstractPaymentsTestIT {

    @Test
    void shouldRefundCardPayment() {
        final RequestCardSource source = getRequestCardSource();
        final PaymentCorporateSender sender = getCorporateSender();
        final PaymentRequest request = getCardSourcePayment(source, sender, false);

        // payment
        final PaymentResponse paymentResponse = makeCardPayment(request);
        assertNotNull(paymentResponse.getLink("capture"));

        // capture
        capturePayment(paymentResponse.getId());

        // refund
        final RefundRequest refundRequest = createComplexRefundRequest();
        final RefundResponse refundResponse = blocking(() -> paymentsClient.refundPayment(paymentResponse.getId(), refundRequest));

        validateRefundResponse(refundResponse);
    }

    @Test
    void shouldRefundTokenPayment() {
        // Make Payment
        final PaymentResponse paymentResponse = makeTokenPayment();
        assertNotNull(paymentResponse.getLink("capture"));

        // Capture Payment
        capturePayment(paymentResponse.getId());

        // Refund
        final RefundRequest refundRequest = createSimpleRefundRequest();
        final RefundResponse refundResponse = blocking(() -> paymentsClient.refundPayment(paymentResponse.getId(), refundRequest));

        validateRefundResponse(refundResponse);
    }

    // Synchronous methods
    @Test
    void shouldRefundCardPaymentSync() {
        final RequestCardSource source = getRequestCardSource();
        final PaymentCorporateSender sender = getCorporateSender();
        final PaymentRequest request = getCardSourcePayment(source, sender, false);

        // payment
        final PaymentResponse paymentResponse = makeCardPayment(request);
        assertNotNull(paymentResponse.getLink("capture"));

        // capture
        capturePayment(paymentResponse.getId());

        // refund
        final RefundRequest refundRequest = createComplexRefundRequest();
        final RefundResponse refundResponse = paymentsClient.refundPaymentSync(paymentResponse.getId(), refundRequest);

        validateRefundResponse(refundResponse);
    }

    @Test
    void shouldRefundTokenPaymentSync() {
        // Make Payment
        final PaymentResponse paymentResponse = makeTokenPayment();
        assertNotNull(paymentResponse.getLink("capture"));

        // Capture Payment
        capturePayment(paymentResponse.getId());

        // Refund
        final RefundRequest refundRequest = createSimpleRefundRequest();
        final RefundResponse refundResponse = paymentsClient.refundPaymentSync(paymentResponse.getId(), refundRequest);

        validateRefundResponse(refundResponse);
    }

    // Common methods
    private RefundRequest createSimpleRefundRequest() {
        return RefundRequest.builder()
                .reference(UUID.randomUUID().toString())
                .build();
    }

    private RefundRequest createComplexRefundRequest() {
        final RefundOrder refundOrder = RefundOrder.builder()
                .name("Order Test")
                .totalAmount(99L)
                .quantity(88L)
                .build();

        final List<RefundOrder> refundOrders = new ArrayList<>();
        refundOrders.add(refundOrder);

        final BankDetails bank = BankDetails.builder()
                .name("Lloyds TSB")
                .branch("Bournemouth")
                .address(createAddress())
                .build();

        final Destination destination = Destination.builder()
                .accountType(AccountType.SAVINGS)
                .accountNumber("13654567455")
                .bankCode("23-456")
                .branchCode("6443")
                .iban("HU93116000060000000012345676")
                .bban("3704 0044 0532 0130 00")
                .swiftBic("37040044")
                .country(CountryCode.GB)
                .accountHolder(getAccountHolder())
                .bank(bank)
                .build();

        return RefundRequest.builder()
                .reference(UUID.randomUUID().toString())
                .items(refundOrders)
                .destination(destination)
                .build();
    }

    private void validateRefundResponse(RefundResponse refundResponse) {
        assertNotNull(refundResponse);
        assertNotNull(refundResponse.getActionId());
        assertNotNull(refundResponse.getReference());
        assertEquals(1, refundResponse.getLinks().size());
    }

}
