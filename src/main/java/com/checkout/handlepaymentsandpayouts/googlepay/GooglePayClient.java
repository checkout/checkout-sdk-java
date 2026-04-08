package com.checkout.handlepaymentsandpayouts.googlepay;

import com.checkout.EmptyResponse;
import com.checkout.handlepaymentsandpayouts.googlepay.requests.GooglePayEnrollmentRequest;
import com.checkout.handlepaymentsandpayouts.googlepay.requests.GooglePayRegisterDomainRequest;
import com.checkout.handlepaymentsandpayouts.googlepay.responses.GooglePayDomainListResponse;
import com.checkout.handlepaymentsandpayouts.googlepay.responses.GooglePayEnrollmentResponse;
import com.checkout.handlepaymentsandpayouts.googlepay.responses.GooglePayEnrollmentStateResponse;

import java.util.concurrent.CompletableFuture;

public interface GooglePayClient {

    CompletableFuture<GooglePayEnrollmentResponse> enrollEntity(GooglePayEnrollmentRequest request);

    CompletableFuture<EmptyResponse> registerDomain(String entityId, GooglePayRegisterDomainRequest request);

    CompletableFuture<GooglePayDomainListResponse> getRegisteredDomains(String entityId);

    CompletableFuture<GooglePayEnrollmentStateResponse> getEnrollmentState(String entityId);

    GooglePayEnrollmentResponse enrollEntitySync(GooglePayEnrollmentRequest request);

    EmptyResponse registerDomainSync(String entityId, GooglePayRegisterDomainRequest request);

    GooglePayDomainListResponse getRegisteredDomainsSync(String entityId);

    GooglePayEnrollmentStateResponse getEnrollmentStateSync(String entityId);

}
