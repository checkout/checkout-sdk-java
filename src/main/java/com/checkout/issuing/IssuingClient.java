package com.checkout.issuing;

import com.checkout.issuing.cardholders.CardholderCardsResponse;
import com.checkout.issuing.cardholders.CardholderDetailsResponse;
import com.checkout.issuing.cardholders.CardholderRequest;
import com.checkout.issuing.cardholders.CardholderResponse;
import java.util.concurrent.CompletableFuture;

public interface IssuingClient {

    CompletableFuture<CardholderResponse> createCardholder(CardholderRequest cardholderRequest);

    CompletableFuture<CardholderDetailsResponse> getCardholder(String cardholderId);

    CompletableFuture<CardholderCardsResponse> getCardholderCards(String cardholderId);
}
