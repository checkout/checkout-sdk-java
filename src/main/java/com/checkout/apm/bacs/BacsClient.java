package com.checkout.apm.bacs;

import java.util.concurrent.CompletableFuture;

/**
 * Bacs Direct Debit client.
 */
public interface BacsClient {

    /**
     * Sends a Bacs Direct Debit pre-notification (advance notice) to a payer ahead of collecting
     * funds from their account.
     *
     * @param bacsNotificationRequest the pre-notification details.
     * @return a {@link CompletableFuture} that resolves to the created notification event.
     */
    CompletableFuture<BacsNotificationResponse> sendNotification(BacsNotificationRequest bacsNotificationRequest);

    // Synchronous methods

    /**
     * Synchronous variant of {@link #sendNotification(BacsNotificationRequest)}.
     *
     * @param bacsNotificationRequest the pre-notification details.
     * @return the created notification event.
     */
    BacsNotificationResponse sendNotificationSync(BacsNotificationRequest bacsNotificationRequest);

}
