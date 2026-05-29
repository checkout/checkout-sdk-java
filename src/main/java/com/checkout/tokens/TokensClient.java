package com.checkout.tokens;

import java.util.concurrent.CompletableFuture;

public interface TokensClient {

    CompletableFuture<CardTokenResponse> requestCardToken(CardTokenRequest cardTokenRequest);

    CompletableFuture<TokenResponse> requestWalletToken(WalletTokenRequest walletTokenRequest);

    /**
     * Returns the details for an active token without consuming it. The token remains usable
     * after this call.
     *
     * @param tokenId The token ID. Pattern: ^(tok)_(\w{26})$.
     * @return a {@link CompletableFuture} that resolves to the token metadata.
     */
    CompletableFuture<TokenMetadataResponse> getTokenMetadata(String tokenId);

    // Synchronous methods
    CardTokenResponse requestCardTokenSync(CardTokenRequest cardTokenRequest);

    TokenResponse requestWalletTokenSync(WalletTokenRequest walletTokenRequest);

    /**
     * Synchronous variant of {@link #getTokenMetadata(String)}.
     *
     * @param tokenId The token ID. Pattern: ^(tok)_(\w{26})$.
     * @return the token metadata.
     */
    TokenMetadataResponse getTokenMetadataSync(String tokenId);

}
