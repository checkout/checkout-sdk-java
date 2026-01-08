package com.checkout.payments;

import static com.checkout.common.CheckoutUtils.validateParams;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;

import com.checkout.AbstractClient;
import com.checkout.ApiClient;
import com.checkout.CheckoutConfiguration;
import com.checkout.HttpMetadata;
import com.checkout.ItemsResponse;
import com.checkout.SdkAuthorizationType;
import com.checkout.handlepaymentsandpayouts.payments.postpayments.requests.unreferencedrefundrequest.UnreferencedRefundRequest;
import com.checkout.handlepaymentsandpayouts.payments.postpayments.responses.RequestAPaymentOrPayoutResponse;
import com.checkout.handlepaymentsandpayouts.payments.postpayments.responses.requestapaymentorpayoutresponseaccepted.RequestAPaymentOrPayoutResponseAccepted;
import com.checkout.handlepaymentsandpayouts.payments.postpayments.responses.requestapaymentorpayoutresponsecreated.RequestAPaymentOrPayoutResponseCreated;
import com.checkout.payments.request.AuthorizationRequest;
import com.checkout.payments.request.PaymentRequest;
import com.checkout.payments.request.PayoutRequest;
import com.checkout.payments.response.AuthorizationResponse;
import com.checkout.payments.response.GetPaymentResponse;
import com.checkout.payments.response.PaymentResponse;
import com.checkout.payments.response.PaymentsQueryResponse;
import com.checkout.payments.response.PayoutResponse;
import com.google.gson.reflect.TypeToken;

public final class PaymentsClientImpl extends AbstractClient implements PaymentsClient {

    private static final String PAYMENTS_PATH = "payments";
    private static final String ACTIONS_PATH = "actions";
    private static final String CAPTURES_PATH = "captures";
    private static final String AUTHORIZATIONS_PATH = "authorizations";
    private static final String REFUNDS_PATH = "refunds";
    private static final String REVERSALS_PATH = "reversals";
    private static final String VOIDS_PATH = "voids";

    private static final Map<Integer, Class<? extends HttpMetadata>> RESPONSE_MAPPINGS = new HashMap<>();
    private static final Type PAYMENT_ACTIONS_TYPE = new TypeToken<ItemsResponse<PaymentAction>>() {
    }.getType();

    static {
        RESPONSE_MAPPINGS.put(201, RequestAPaymentOrPayoutResponseCreated.class);
        RESPONSE_MAPPINGS.put(202, RequestAPaymentOrPayoutResponseAccepted.class);
    }

    public PaymentsClientImpl(final ApiClient apiClient, final CheckoutConfiguration configuration) {
        super(apiClient, configuration, SdkAuthorizationType.SECRET_KEY_OR_OAUTH);
    }

    @Override
    public CompletableFuture<PaymentResponse> requestPayment(final PaymentRequest paymentRequest) {
        validatePaymentRequest(paymentRequest);
        return apiClient.postAsync(PAYMENTS_PATH, sdkAuthorization(), PaymentResponse.class, paymentRequest, null);
    }

    @Override
    public CompletableFuture<PaymentResponse> requestPayment(final PaymentRequest paymentRequest, final String idempotencyKey) {
        validatePaymentRequestAndIdempotencyKey(paymentRequest, idempotencyKey);
        return apiClient.postAsync(PAYMENTS_PATH, sdkAuthorization(), PaymentResponse.class, paymentRequest, idempotencyKey);
    }

    @Override
    public CompletableFuture<RequestAPaymentOrPayoutResponse> requestPayment(final UnreferencedRefundRequest paymentRequest) {
        validateUnreferencedRefundPaymentRequest(paymentRequest);
        return requestUnreferencedRefundPayment(paymentRequest, null);
    }

    @Override
    public CompletableFuture<RequestAPaymentOrPayoutResponse> requestPayment(final UnreferencedRefundRequest paymentRequest, final String idempotencyKey) {
        validateUnreferencedRefundPaymentRequestAndIdempotencyKey(paymentRequest, idempotencyKey);
        return requestUnreferencedRefundPayment(paymentRequest, idempotencyKey);
    }

    private CompletableFuture<RequestAPaymentOrPayoutResponse> requestUnreferencedRefundPayment(final UnreferencedRefundRequest paymentRequest, final String idempotencyKey) {
        return apiClient.postAsync(PAYMENTS_PATH, sdkAuthorization(), RESPONSE_MAPPINGS, paymentRequest, idempotencyKey)
                .thenApply((Function<HttpMetadata, RequestAPaymentOrPayoutResponse>) resource -> {
                    if (resource instanceof RequestAPaymentOrPayoutResponseCreated) {
                        return new RequestAPaymentOrPayoutResponse((RequestAPaymentOrPayoutResponseCreated) resource);
                    } else if (resource instanceof RequestAPaymentOrPayoutResponseAccepted) {
                        return new RequestAPaymentOrPayoutResponse((RequestAPaymentOrPayoutResponseAccepted) resource);
                    } else {
                        throw new IllegalStateException("Unexpected mapping type " + resource.getClass());
                    }
                });
    }

    @Override
    public CompletableFuture<PayoutResponse> requestPayout(final PayoutRequest payoutRequest) {
        validatePayoutRequest(payoutRequest);
        return apiClient.postAsync(PAYMENTS_PATH, sdkAuthorization(), PayoutResponse.class, payoutRequest, null);
    }

    @Override
    public CompletableFuture<PayoutResponse> requestPayout(final PayoutRequest payoutRequest, final String idempotencyKey) {
        validatePayoutRequestAndIdempotencyKey(payoutRequest, idempotencyKey);
        return apiClient.postAsync(PAYMENTS_PATH, sdkAuthorization(), PayoutResponse.class, payoutRequest, idempotencyKey);
    }

    @Override
    public CompletableFuture<PaymentsQueryResponse> getPaymentsList(final PaymentsQueryFilter queryFilter) {
        validateQueryFilter(queryFilter);
        return apiClient.queryAsync(PAYMENTS_PATH, sdkAuthorization(), queryFilter, PaymentsQueryResponse.class);
    }

    @Override
    public CompletableFuture<GetPaymentResponse> getPayment(final String paymentId) {
        validatePaymentId(paymentId);
        return apiClient.getAsync(buildPath(PAYMENTS_PATH, paymentId), sdkAuthorization(), GetPaymentResponse.class);
    }

    @Override
    public CompletableFuture<ItemsResponse<PaymentAction>> getPaymentActions(final String paymentId) {
        validatePaymentId(paymentId);
        return apiClient.getAsync(buildPath(PAYMENTS_PATH, paymentId, ACTIONS_PATH), sdkAuthorization(), PAYMENT_ACTIONS_TYPE);
    }

    @Override
    public CompletableFuture<AuthorizationResponse> incrementPaymentAuthorization(final String paymentId, final AuthorizationRequest authorizationRequest) {
        validatePaymentIdForAuthorization(paymentId);
        return apiClient.postAsync(buildPath(PAYMENTS_PATH, paymentId, AUTHORIZATIONS_PATH), sdkAuthorization(), AuthorizationResponse.class, authorizationRequest, null);
    }

    @Override
    public CompletableFuture<AuthorizationResponse> incrementPaymentAuthorization(final String paymentId, final AuthorizationRequest authorizationRequest, final String idempotencyKey) {
        validatePaymentIdAndIdempotencyKeyForAuthorization(paymentId, idempotencyKey);
        return apiClient.postAsync(buildPath(PAYMENTS_PATH, paymentId, AUTHORIZATIONS_PATH), sdkAuthorization(), AuthorizationResponse.class, authorizationRequest, idempotencyKey);
    }

    @Override
    public CompletableFuture<CaptureResponse> capturePayment(final String paymentId) {
        validatePaymentId(paymentId);
        return apiClient.postAsync(buildPath(PAYMENTS_PATH, paymentId, CAPTURES_PATH), sdkAuthorization(), CaptureResponse.class, null, null);
    }

    @Override
    public CompletableFuture<CaptureResponse> capturePayment(final String paymentId, final String idempotencyKey) {
        validatePaymentIdAndIdempotencyKey(paymentId, idempotencyKey);
        return apiClient.postAsync(buildPath(PAYMENTS_PATH, paymentId, CAPTURES_PATH), sdkAuthorization(), CaptureResponse.class, null, idempotencyKey);
    }

    @Override
    public CompletableFuture<CaptureResponse> capturePayment(final String paymentId, final CaptureRequest captureRequest) {
        validatePaymentIdAndCaptureRequest(paymentId, captureRequest);
        return apiClient.postAsync(buildPath(PAYMENTS_PATH, paymentId, CAPTURES_PATH), sdkAuthorization(), CaptureResponse.class, captureRequest, null);
    }

    @Override
    public CompletableFuture<CaptureResponse> capturePayment(final String paymentId, final CaptureRequest captureRequest, final String idempotencyKey) {
        validatePaymentIdCaptureRequestAndIdempotencyKey(paymentId, captureRequest, idempotencyKey);
        return apiClient.postAsync(buildPath(PAYMENTS_PATH, paymentId, CAPTURES_PATH), sdkAuthorization(), CaptureResponse.class, captureRequest, idempotencyKey);
    }

    @Override
    public CompletableFuture<RefundResponse> refundPayment(final String paymentId) {
        validatePaymentId(paymentId);
        return apiClient.postAsync(buildPath(PAYMENTS_PATH, paymentId, REFUNDS_PATH), sdkAuthorization(), RefundResponse.class, null, null);
    }

    @Override
    public CompletableFuture<RefundResponse> refundPayment(final String paymentId, final String idempotencyKey) {
        validatePaymentIdAndIdempotencyKey(paymentId, idempotencyKey);
        return apiClient.postAsync(buildPath(PAYMENTS_PATH, paymentId, REFUNDS_PATH), sdkAuthorization(), RefundResponse.class, null, idempotencyKey);
    }

    @Override
    public CompletableFuture<RefundResponse> refundPayment(final String paymentId, final RefundRequest refundRequest) {
        validatePaymentIdAndRefundRequest(paymentId, refundRequest);
        return apiClient.postAsync(buildPath(PAYMENTS_PATH, paymentId, REFUNDS_PATH), sdkAuthorization(), RefundResponse.class, refundRequest, null);
    }

    @Override
    public CompletableFuture<RefundResponse> refundPayment(final String paymentId, final RefundRequest refundRequest, final String idempotencyKey) {
        validatePaymentIdRefundRequestAndIdempotencyKey(paymentId, refundRequest, idempotencyKey);
        return apiClient.postAsync(buildPath(PAYMENTS_PATH, paymentId, REFUNDS_PATH), sdkAuthorization(), RefundResponse.class, refundRequest, idempotencyKey);
    }

    @Override
    public CompletableFuture<ReverseResponse> reversePayment(final String paymentId) {
        validatePaymentId(paymentId);
        return apiClient.postAsync(buildPath(PAYMENTS_PATH, paymentId, REVERSALS_PATH), sdkAuthorization(), ReverseResponse.class, null, null);
    }

    @Override
    public CompletableFuture<ReverseResponse> reversePayment(final String paymentId, final String idempotencyKey) {
        validatePaymentIdAndIdempotencyKey(paymentId, idempotencyKey);
        return apiClient.postAsync(buildPath(PAYMENTS_PATH, paymentId, REVERSALS_PATH), sdkAuthorization(), ReverseResponse.class, null, idempotencyKey);
    }

    @Override
    public CompletableFuture<ReverseResponse> reversePayment(final String paymentId, final ReverseRequest reverseRequest) {
        validatePaymentIdAndReverseRequest(paymentId, reverseRequest);
        return apiClient.postAsync(buildPath(PAYMENTS_PATH, paymentId, REVERSALS_PATH), sdkAuthorization(), ReverseResponse.class, reverseRequest, null);
    }

    @Override
    public CompletableFuture<ReverseResponse> reversePayment(final String paymentId, final ReverseRequest reverseRequest, final String idempotencyKey) {
        validatePaymentIdReverseRequestAndIdempotencyKey(paymentId, reverseRequest, idempotencyKey);
        return apiClient.postAsync(buildPath(PAYMENTS_PATH, paymentId, REVERSALS_PATH), sdkAuthorization(), ReverseResponse.class, reverseRequest, idempotencyKey);
    }

    @Override
    public CompletableFuture<VoidResponse> voidPayment(final String paymentId) {
        validatePaymentId(paymentId);
        return apiClient.postAsync(buildPath(PAYMENTS_PATH, paymentId, VOIDS_PATH), sdkAuthorization(), VoidResponse.class, null, null);
    }

    @Override
    public CompletableFuture<VoidResponse> voidPayment(final String paymentId, final String idempotencyKey) {
        validatePaymentIdAndIdempotencyKey(paymentId, idempotencyKey);
        return apiClient.postAsync(buildPath(PAYMENTS_PATH, paymentId, VOIDS_PATH), sdkAuthorization(), VoidResponse.class, null, idempotencyKey);
    }

    @Override
    public CompletableFuture<VoidResponse> voidPayment(final String paymentId, final VoidRequest voidRequest) {
        validatePaymentIdAndVoidRequest(paymentId, voidRequest);
        return apiClient.postAsync(buildPath(PAYMENTS_PATH, paymentId, VOIDS_PATH), sdkAuthorization(), VoidResponse.class, voidRequest, null);
    }

    @Override
    public CompletableFuture<VoidResponse> voidPayment(final String paymentId, final VoidRequest voidRequest, final String idempotencyKey) {
        validatePaymentIdVoidRequestAndIdempotencyKey(paymentId, voidRequest, idempotencyKey);
        return apiClient.postAsync(buildPath(PAYMENTS_PATH, paymentId, VOIDS_PATH), sdkAuthorization(), VoidResponse.class, voidRequest, idempotencyKey);
    }

    // Synchronous methods
    @Override
    public PaymentResponse requestPaymentSync(final PaymentRequest paymentRequest) {
        validatePaymentRequest(paymentRequest);
        return apiClient.post(PAYMENTS_PATH, sdkAuthorization(), PaymentResponse.class, paymentRequest, null);
    }

    @Override
    public PaymentResponse requestPaymentSync(final PaymentRequest paymentRequest, final String idempotencyKey) {
        validatePaymentRequestAndIdempotencyKey(paymentRequest, idempotencyKey);
        return apiClient.post(PAYMENTS_PATH, sdkAuthorization(), PaymentResponse.class, paymentRequest, idempotencyKey);
    }

    @Override
    public RequestAPaymentOrPayoutResponse requestPaymentSync(final UnreferencedRefundRequest paymentRequest) {
        validateUnreferencedRefundPaymentRequest(paymentRequest);
        return requestUnreferencedRefundPaymentSync(paymentRequest, null);
    }

    @Override
    public RequestAPaymentOrPayoutResponse requestPaymentSync(final UnreferencedRefundRequest paymentRequest, final String idempotencyKey) {
        validateUnreferencedRefundPaymentRequestAndIdempotencyKey(paymentRequest, idempotencyKey);
        return requestUnreferencedRefundPaymentSync(paymentRequest, idempotencyKey);
    }

    private RequestAPaymentOrPayoutResponse requestUnreferencedRefundPaymentSync(final UnreferencedRefundRequest paymentRequest, final String idempotencyKey) {
        final HttpMetadata resource = apiClient.post(PAYMENTS_PATH, sdkAuthorization(), RESPONSE_MAPPINGS, paymentRequest, idempotencyKey);
        if (resource instanceof RequestAPaymentOrPayoutResponseCreated) {
            return new RequestAPaymentOrPayoutResponse((RequestAPaymentOrPayoutResponseCreated) resource);
        } else if (resource instanceof RequestAPaymentOrPayoutResponseAccepted) {
            return new RequestAPaymentOrPayoutResponse((RequestAPaymentOrPayoutResponseAccepted) resource);
        } else {
            throw new IllegalStateException("Unexpected mapping type " + resource.getClass());
        }
    }

    @Override
    public PayoutResponse requestPayoutSync(final PayoutRequest payoutRequest) {
        validatePayoutRequest(payoutRequest);
        return apiClient.post(PAYMENTS_PATH, sdkAuthorization(), PayoutResponse.class, payoutRequest, null);
    }

    @Override
    public PayoutResponse requestPayoutSync(final PayoutRequest payoutRequest, final String idempotencyKey) {
        validatePayoutRequestAndIdempotencyKey(payoutRequest, idempotencyKey);
        return apiClient.post(PAYMENTS_PATH, sdkAuthorization(), PayoutResponse.class, payoutRequest, idempotencyKey);
    }

    @Override
    public PaymentsQueryResponse getPaymentsListSync(final PaymentsQueryFilter queryFilter) {
        validateQueryFilter(queryFilter);
        return apiClient.query(PAYMENTS_PATH, sdkAuthorization(), queryFilter, PaymentsQueryResponse.class);
    }

    @Override
    public GetPaymentResponse getPaymentSync(final String paymentId) {
        validatePaymentId(paymentId);
        return apiClient.get(buildPath(PAYMENTS_PATH, paymentId), sdkAuthorization(), GetPaymentResponse.class);
    }

    @Override
    public ItemsResponse<PaymentAction> getPaymentActionsSync(final String paymentId) {
        validatePaymentId(paymentId);
        return apiClient.get(buildPath(PAYMENTS_PATH, paymentId, ACTIONS_PATH), sdkAuthorization(), PAYMENT_ACTIONS_TYPE);
    }

    @Override
    public AuthorizationResponse incrementPaymentAuthorizationSync(final String paymentId, final AuthorizationRequest authorizationRequest) {
        validatePaymentIdForAuthorization(paymentId);
        return apiClient.post(buildPath(PAYMENTS_PATH, paymentId, AUTHORIZATIONS_PATH), sdkAuthorization(), AuthorizationResponse.class, authorizationRequest, null);
    }

    @Override
    public AuthorizationResponse incrementPaymentAuthorizationSync(final String paymentId, final AuthorizationRequest authorizationRequest, final String idempotencyKey) {
        validatePaymentIdAndIdempotencyKeyForAuthorization(paymentId, idempotencyKey);
        return apiClient.post(buildPath(PAYMENTS_PATH, paymentId, AUTHORIZATIONS_PATH), sdkAuthorization(), AuthorizationResponse.class, authorizationRequest, idempotencyKey);
    }

    @Override
    public CaptureResponse capturePaymentSync(final String paymentId) {
        validatePaymentId(paymentId);
        return apiClient.post(buildPath(PAYMENTS_PATH, paymentId, CAPTURES_PATH), sdkAuthorization(), CaptureResponse.class, null, null);
    }

    @Override
    public CaptureResponse capturePaymentSync(final String paymentId, final String idempotencyKey) {
        validatePaymentIdAndIdempotencyKey(paymentId, idempotencyKey);
        return apiClient.post(buildPath(PAYMENTS_PATH, paymentId, CAPTURES_PATH), sdkAuthorization(), CaptureResponse.class, null, idempotencyKey);
    }

    @Override
    public CaptureResponse capturePaymentSync(final String paymentId, final CaptureRequest captureRequest) {
        validatePaymentIdAndCaptureRequest(paymentId, captureRequest);
        return apiClient.post(buildPath(PAYMENTS_PATH, paymentId, CAPTURES_PATH), sdkAuthorization(), CaptureResponse.class, captureRequest, null);
    }

    @Override
    public CaptureResponse capturePaymentSync(final String paymentId, final CaptureRequest captureRequest, final String idempotencyKey) {
        validatePaymentIdCaptureRequestAndIdempotencyKey(paymentId, captureRequest, idempotencyKey);
        return apiClient.post(buildPath(PAYMENTS_PATH, paymentId, CAPTURES_PATH), sdkAuthorization(), CaptureResponse.class, captureRequest, idempotencyKey);
    }

    @Override
    public RefundResponse refundPaymentSync(final String paymentId) {
        validatePaymentId(paymentId);
        return apiClient.post(buildPath(PAYMENTS_PATH, paymentId, REFUNDS_PATH), sdkAuthorization(), RefundResponse.class, null, null);
    }

    @Override
    public RefundResponse refundPaymentSync(final String paymentId, final String idempotencyKey) {
        validatePaymentIdAndIdempotencyKey(paymentId, idempotencyKey);
        return apiClient.post(buildPath(PAYMENTS_PATH, paymentId, REFUNDS_PATH), sdkAuthorization(), RefundResponse.class, null, idempotencyKey);
    }

    @Override
    public RefundResponse refundPaymentSync(final String paymentId, final RefundRequest refundRequest) {
        validatePaymentIdAndRefundRequest(paymentId, refundRequest);
        return apiClient.post(buildPath(PAYMENTS_PATH, paymentId, REFUNDS_PATH), sdkAuthorization(), RefundResponse.class, refundRequest, null);
    }

    @Override
    public RefundResponse refundPaymentSync(final String paymentId, final RefundRequest refundRequest, final String idempotencyKey) {
        validatePaymentIdRefundRequestAndIdempotencyKey(paymentId, refundRequest, idempotencyKey);
        return apiClient.post(buildPath(PAYMENTS_PATH, paymentId, REFUNDS_PATH), sdkAuthorization(), RefundResponse.class, refundRequest, idempotencyKey);
    }

    @Override
    public ReverseResponse reversePaymentSync(final String paymentId) {
        validatePaymentId(paymentId);
        return apiClient.post(buildPath(PAYMENTS_PATH, paymentId, REVERSALS_PATH), sdkAuthorization(), ReverseResponse.class, null, null);
    }

    @Override
    public ReverseResponse reversePaymentSync(final String paymentId, final String idempotencyKey) {
        validatePaymentIdAndIdempotencyKey(paymentId, idempotencyKey);
        return apiClient.post(buildPath(PAYMENTS_PATH, paymentId, REVERSALS_PATH), sdkAuthorization(), ReverseResponse.class, null, idempotencyKey);
    }

    @Override
    public ReverseResponse reversePaymentSync(final String paymentId, final ReverseRequest reverseRequest) {
        validatePaymentIdAndReverseRequest(paymentId, reverseRequest);
        return apiClient.post(buildPath(PAYMENTS_PATH, paymentId, REVERSALS_PATH), sdkAuthorization(), ReverseResponse.class, reverseRequest, null);
    }

    @Override
    public ReverseResponse reversePaymentSync(final String paymentId, final ReverseRequest reverseRequest, final String idempotencyKey) {
        validatePaymentIdReverseRequestAndIdempotencyKey(paymentId, reverseRequest, idempotencyKey);
        return apiClient.post(buildPath(PAYMENTS_PATH, paymentId, REVERSALS_PATH), sdkAuthorization(), ReverseResponse.class, reverseRequest, idempotencyKey);
    }

    @Override
    public VoidResponse voidPaymentSync(final String paymentId) {
        validatePaymentId(paymentId);
        return apiClient.post(buildPath(PAYMENTS_PATH, paymentId, VOIDS_PATH), sdkAuthorization(), VoidResponse.class, null, null);
    }

    @Override
    public VoidResponse voidPaymentSync(final String paymentId, final String idempotencyKey) {
        validatePaymentIdAndIdempotencyKey(paymentId, idempotencyKey);
        return apiClient.post(buildPath(PAYMENTS_PATH, paymentId, VOIDS_PATH), sdkAuthorization(), VoidResponse.class, null, idempotencyKey);
    }

    @Override
    public VoidResponse voidPaymentSync(final String paymentId, final VoidRequest voidRequest) {
        validatePaymentIdAndVoidRequest(paymentId, voidRequest);
        return apiClient.post(buildPath(PAYMENTS_PATH, paymentId, VOIDS_PATH), sdkAuthorization(), VoidResponse.class, voidRequest, null);
    }

    @Override
    public VoidResponse voidPaymentSync(final String paymentId, final VoidRequest voidRequest, final String idempotencyKey) {
        validatePaymentIdVoidRequestAndIdempotencyKey(paymentId, voidRequest, idempotencyKey);
        return apiClient.post(buildPath(PAYMENTS_PATH, paymentId, VOIDS_PATH), sdkAuthorization(), VoidResponse.class, voidRequest, idempotencyKey);
    }

    // Common methods
    protected void validatePaymentRequest(final PaymentRequest paymentRequest) {
        validateParams("paymentRequest", paymentRequest);
    }

    protected void validatePaymentRequestAndIdempotencyKey(final PaymentRequest paymentRequest, final String idempotencyKey) {
        validateParams("paymentRequest", paymentRequest, "idempotencyKey", idempotencyKey);
    }

    protected void validateUnreferencedRefundPaymentRequest(final UnreferencedRefundRequest paymentRequest) {
        validateParams("paymentRequest", paymentRequest);
    }

    protected void validateUnreferencedRefundPaymentRequestAndIdempotencyKey(final UnreferencedRefundRequest paymentRequest, final String idempotencyKey) {
        validateParams("paymentRequest", paymentRequest, "idempotencyKey", idempotencyKey);
    }

    protected void validatePayoutRequest(final PayoutRequest payoutRequest) {
        validateParams("payoutRequest", payoutRequest);
    }

    protected void validatePayoutRequestAndIdempotencyKey(final PayoutRequest payoutRequest, final String idempotencyKey) {
        validateParams("payoutRequest", payoutRequest, "idempotencyKey", idempotencyKey);
    }

    protected void validateQueryFilter(final PaymentsQueryFilter queryFilter) {
        validateParams("queryFilter", queryFilter);
    }

    protected void validatePaymentId(final String paymentId) {
        validateParams("paymentId", paymentId);
    }

    protected void validatePaymentIdForAuthorization(final String paymentId) {
        validateParams("paymentId", paymentId);
    }

    protected void validatePaymentIdAndIdempotencyKeyForAuthorization(final String paymentId, final String idempotencyKey) {
        validateParams("paymentId", paymentId, "idempotencyKey", idempotencyKey);
    }

    protected void validatePaymentIdAndIdempotencyKey(final String paymentId, final String idempotencyKey) {
        validateParams("paymentId", paymentId, "idempotencyKey", idempotencyKey);
    }

    protected void validatePaymentIdAndCaptureRequest(final String paymentId, final CaptureRequest captureRequest) {
        validateParams("paymentId", paymentId, "captureRequest", captureRequest);
    }

    protected void validatePaymentIdCaptureRequestAndIdempotencyKey(final String paymentId, final CaptureRequest captureRequest, final String idempotencyKey) {
        validateParams("paymentId", paymentId, "captureRequest", captureRequest, "idempotencyKey", idempotencyKey);
    }

    protected void validatePaymentIdAndRefundRequest(final String paymentId, final RefundRequest refundRequest) {
        validateParams("paymentId", paymentId, "refundRequest", refundRequest);
    }

    protected void validatePaymentIdRefundRequestAndIdempotencyKey(final String paymentId, final RefundRequest refundRequest, final String idempotencyKey) {
        validateParams("paymentId", paymentId, "refundRequest", refundRequest, "idempotencyKey", idempotencyKey);
    }

    protected void validatePaymentIdAndReverseRequest(final String paymentId, final ReverseRequest reverseRequest) {
        validateParams("paymentId", paymentId, "reverseRequest", reverseRequest);
    }

    protected void validatePaymentIdReverseRequestAndIdempotencyKey(final String paymentId, final ReverseRequest reverseRequest, final String idempotencyKey) {
        validateParams("paymentId", paymentId, "reverseRequest", reverseRequest, "idempotencyKey", idempotencyKey);
    }

    protected void validatePaymentIdAndVoidRequest(final String paymentId, final VoidRequest voidRequest) {
        validateParams("paymentId", paymentId, "voidRequest", voidRequest);
    }

    protected void validatePaymentIdVoidRequestAndIdempotencyKey(final String paymentId, final VoidRequest voidRequest, final String idempotencyKey) {
        validateParams("paymentId", paymentId, "voidRequest", voidRequest, "idempotencyKey", idempotencyKey);
    }

}
