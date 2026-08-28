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

/**
 * The payment instruments client.
 *
 * <p>The create, update and retrieve operations are polymorphic: the concrete request type selects
 * the instrument variant, and the response deserializes to the matching concrete type. Cast or
 * parameterize to the variant you sent.
 */
public interface InstrumentsClient {

    /**
     * Stores a payment instrument.
     *
     * @param createInstrumentRequest the instrument details, as one of the concrete variants of
     *                                {@link CreateInstrumentRequest}.
     * @param <T>                     the concrete response variant matching the request type.
     * @return a {@link CompletableFuture} that resolves to the stored instrument.
     */
    <T extends CreateInstrumentResponse> CompletableFuture<T> create(CreateInstrumentRequest createInstrumentRequest);

    /**
     * Retrieves a payment instrument.
     *
     * @param instrumentId the payment instrument ID.
     * @return a {@link CompletableFuture} that resolves to the instrument, as one of the concrete
     * variants of {@link GetInstrumentResponse}.
     */
    CompletableFuture<GetInstrumentResponse> get(String instrumentId);

    /**
     * Updates a payment instrument.
     *
     * @param instrumentId            the payment instrument ID.
     * @param updateInstrumentRequest the properties to update, as one of the concrete variants of
     *                                {@link UpdateInstrumentRequest}.
     * @param <T>                     the concrete response variant matching the request type.
     * @return a {@link CompletableFuture} that resolves to the updated instrument.
     */
    <T extends UpdateInstrumentResponse> CompletableFuture<T> update(String instrumentId, UpdateInstrumentRequest updateInstrumentRequest);

    /**
     * Deletes a payment instrument.
     *
     * @param instrumentId the payment instrument ID.
     * @return a {@link CompletableFuture} that resolves to an empty response on success.
     */
    CompletableFuture<EmptyResponse> delete(String instrumentId);

    /**
     * Revokes a payment instrument. The instrument status is set to {@code INVALID} with the reason
     * {@code revoked_by_merchant}. The instrument record is retained for audit purposes.
     *
     * @param instrumentId The payment instrument ID. Pattern: ^(src_)[a-z0-9]{26}$.
     * @return a {@link CompletableFuture} that resolves to an empty response on success.
     */
    CompletableFuture<EmptyResponse> revoke(String instrumentId);

    /**
     * Retrieves the bank account field formatting requirements for a country and currency.
     *
     * @param country  the two-letter ISO country code.
     * @param currency the three-letter ISO currency code.
     * @param query    the optional filters on account holder type and payment network.
     * @return a {@link CompletableFuture} that resolves to the required field sections.
     */
    CompletableFuture<BankAccountFieldResponse> getBankAccountFieldFormatting(CountryCode country, Currency currency, BankAccountFieldQuery query);

    // Synchronous methods

    /**
     * Synchronous variant of {@link #create(CreateInstrumentRequest)}.
     *
     * @param createInstrumentRequest the instrument details.
     * @param <T>                     the concrete response variant matching the request type.
     * @return the stored instrument.
     */
    <T extends CreateInstrumentResponse> T createSync(CreateInstrumentRequest createInstrumentRequest);

    /**
     * Synchronous variant of {@link #get(String)}.
     *
     * @param instrumentId the payment instrument ID.
     * @return the instrument.
     */
    GetInstrumentResponse getSync(String instrumentId);

    /**
     * Synchronous variant of {@link #update(String, UpdateInstrumentRequest)}.
     *
     * @param instrumentId            the payment instrument ID.
     * @param updateInstrumentRequest the properties to update.
     * @param <T>                     the concrete response variant matching the request type.
     * @return the updated instrument.
     */
    <T extends UpdateInstrumentResponse> T updateSync(String instrumentId, UpdateInstrumentRequest updateInstrumentRequest);

    /**
     * Synchronous variant of {@link #delete(String)}.
     *
     * @param instrumentId the payment instrument ID.
     * @return an empty response on success.
     */
    EmptyResponse deleteSync(String instrumentId);

    /**
     * Synchronous variant of {@link #revoke(String)}.
     *
     * @param instrumentId The payment instrument ID. Pattern: ^(src_)[a-z0-9]{26}$.
     * @return an empty response on success.
     */
    EmptyResponse revokeSync(String instrumentId);

    /**
     * Synchronous variant of
     * {@link #getBankAccountFieldFormatting(CountryCode, Currency, BankAccountFieldQuery)}.
     *
     * @param country  the two-letter ISO country code.
     * @param currency the three-letter ISO currency code.
     * @param query    the optional filters on account holder type and payment network.
     * @return the required field sections.
     */
    BankAccountFieldResponse getBankAccountFieldFormattingSync(CountryCode country, Currency currency, BankAccountFieldQuery query);
}
