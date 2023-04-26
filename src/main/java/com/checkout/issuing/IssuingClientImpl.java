package com.checkout.issuing;

import com.checkout.AbstractClient;
import com.checkout.ApiClient;
import com.checkout.CheckoutConfiguration;
import com.checkout.SdkAuthorizationType;
import com.checkout.issuing.cardholders.CardholderCardsResponse;
import com.checkout.issuing.cardholders.CardholderDetailsResponse;
import com.checkout.issuing.cardholders.CardholderRequest;
import com.checkout.issuing.cardholders.CardholderResponse;

import java.util.concurrent.CompletableFuture;

import static com.checkout.common.CheckoutUtils.validateParams;

public class IssuingClientImpl extends AbstractClient implements IssuingClient {

    private static final String ISSUING_PATH = "issuing";

    private static final String CARDHOLDERS_PATH = "cardholders";

    private static final String CARDS_PATH = "cards";

    public IssuingClientImpl(final ApiClient apiClient, final CheckoutConfiguration configuration) {
        super(apiClient, configuration, SdkAuthorizationType.SECRET_KEY_OR_OAUTH);
    }

    @Override
    public CompletableFuture<CardholderResponse> createCardholder(CardholderRequest cardholderRequest) {
        validateParams("cardholderRequest", cardholderRequest);
        return apiClient.postAsync(
                buildPath(ISSUING_PATH, CARDHOLDERS_PATH),
                sdkAuthorization(),
                CardholderResponse.class,
                cardholderRequest,
                null
        );
    }

    @Override
    public CompletableFuture<CardholderDetailsResponse> getCardholder(String cardholderId) {
        validateParams("cardholderId", cardholderId);
        return apiClient.getAsync(
                buildPath(ISSUING_PATH, CARDHOLDERS_PATH, cardholderId),
                sdkAuthorization(),
                CardholderDetailsResponse.class
        );
    }

    @Override
    public CompletableFuture<CardholderCardsResponse> getCardholderCards(String cardholderId) {
        validateParams("cardholderId", cardholderId);
        return apiClient.getAsync(
                buildPath(ISSUING_PATH, CARDHOLDERS_PATH, cardholderId, CARDS_PATH),
                sdkAuthorization(),
                CardholderCardsResponse.class
        );
    }
}
