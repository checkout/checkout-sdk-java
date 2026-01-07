package com.checkout.apm.previous.sepa;

import java.util.concurrent.CompletableFuture;

public interface SepaClient {

    CompletableFuture<MandateResponse> getMandate(String mandateId);

    CompletableFuture<SepaResource> cancelMandate(String mandateId);

    CompletableFuture<MandateResponse> getMandateViaPPRO(String mandateId);

    CompletableFuture<SepaResource> cancelMandateViaPPRO(String mandateId);

    // Synchronous methods
    MandateResponse getMandateSync(String mandateId);

    SepaResource cancelMandateSync(String mandateId);

    MandateResponse getMandateViaPPROSync(String mandateId);

    SepaResource cancelMandateViaPPROSync(String mandateId);

}
