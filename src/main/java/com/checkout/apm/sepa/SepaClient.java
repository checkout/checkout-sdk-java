package com.checkout.apm.sepa;

import com.checkout.common.Resource;

import java.util.concurrent.CompletableFuture;

public interface SepaClient {

    CompletableFuture<MandateResponse> getMandate(String mandateId);

    CompletableFuture<Resource> cancelMandate(String mandateId);

    CompletableFuture<MandateResponse> getMandateViaPPRO(String mandateId);

    CompletableFuture<Resource> cancelMandateViaPPRO(String mandateId);

}
