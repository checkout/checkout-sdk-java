package com.checkout.handlepaymentsandpayouts.payments.postpayments.responses;

import com.checkout.handlepaymentsandpayouts.payments.postpayments.responses.requestapaymentorpayoutresponseaccepted.RequestAPaymentOrPayoutResponseAccepted;
import com.checkout.handlepaymentsandpayouts.payments.postpayments.responses.requestapaymentorpayoutresponsecreated.RequestAPaymentOrPayoutResponseCreated;
import lombok.Getter;

@Getter
public final class RequestAPaymentOrPayoutResponse {

    private RequestAPaymentOrPayoutResponseAccepted accepted;
    private RequestAPaymentOrPayoutResponseCreated created;

    public RequestAPaymentOrPayoutResponse(final RequestAPaymentOrPayoutResponseAccepted accepted) {
        this.accepted = accepted;
    }

    public RequestAPaymentOrPayoutResponse(final RequestAPaymentOrPayoutResponseCreated created) {
        this.created = created;
    }

}


