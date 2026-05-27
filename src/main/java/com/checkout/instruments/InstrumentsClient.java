package com.checkout.instruments;

import com.checkout.EmptyResponse;
import com.checkout.common.CountryCode;
import com.checkout.common.Currency;
import com.checkout.instruments.create.CreateInstrumentRequest;
import com.checkout.instruments.create.CreateInstrumentResponse;
import com.checkout.instruments.get.BankAccountFieldQuery;
import com.checkout.instruments.get.BankAccountFieldResponse;
import com.checkout.instruments.get.GetInstrumentResponse;
import com.checkout.instruments.update.UpdateInstrumentRequest;
import com.checkout.instruments.update.UpdateInstrumentResponse;

import java.util.concurrent.CompletableFuture;

public interface InstrumentsClient {

    <T extends CreateInstrumentResponse> CompletableFuture<T> create(CreateInstrumentRequest createInstrumentRequest);

    CompletableFuture<GetInstrumentResponse> get(String instrumentId);

    <T extends UpdateInstrumentResponse> CompletableFuture<T> update(String instrumentId, UpdateInstrumentRequest updateInstrumentRequest);

    CompletableFuture<EmptyResponse> delete(String instrumentId);

    /**
     * Revokes a payment instrument. The instrument status is set to {@code INVALID} with the reason
     * {@code revoked_by_merchant}. The instrument record is retained for audit purposes.
     *
     * @param instrumentId The payment instrument ID. Pattern: ^(src_)[a-z0-9]{26}$.
     * @return a {@link CompletableFuture} that resolves to an empty response on success.
     */
    CompletableFuture<EmptyResponse> revoke(String instrumentId);

    CompletableFuture<BankAccountFieldResponse> getBankAccountFieldFormatting(CountryCode country, Currency currency, BankAccountFieldQuery query);

    // Synchronous methods
    <T extends CreateInstrumentResponse> T createSync(CreateInstrumentRequest createInstrumentRequest);

    GetInstrumentResponse getSync(String instrumentId);

    <T extends UpdateInstrumentResponse> T updateSync(String instrumentId, UpdateInstrumentRequest updateInstrumentRequest);

    EmptyResponse deleteSync(String instrumentId);

    /**
     * Synchronous variant of {@link #revoke(String)}.
     *
     * @param instrumentId The payment instrument ID. Pattern: ^(src_)[a-z0-9]{26}$.
     * @return an empty response on success.
     */
    EmptyResponse revokeSync(String instrumentId);

    BankAccountFieldResponse getBankAccountFieldFormattingSync(CountryCode country, Currency currency, BankAccountFieldQuery query);
}
