package com.checkout.apm.sepa;

import java.util.concurrent.CompletableFuture;

public interface SepaClient {

    CompletableFuture<MandateResponse> getMandate(String mandateId);

    CompletableFuture<SepaResource> cancelMandate(String mandateId);

    CompletableFuture<MandateResponse> getMandateViaPPRO(String mandateId);

    CompletableFuture<SepaResource> cancelMandateViaPPRO(String mandateId);

}
