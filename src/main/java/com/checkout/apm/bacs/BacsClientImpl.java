package com.checkout.apm.bacs;

import com.checkout.AbstractClient;
import com.checkout.ApiClient;
import com.checkout.CheckoutConfiguration;
import com.checkout.SdkAuthorizationType;

import java.util.concurrent.CompletableFuture;

import static com.checkout.common.CheckoutUtils.validateParams;

public class BacsClientImpl extends AbstractClient implements BacsClient {

    private static final String APMS = "apms";
    private static final String BACS_NOTIFICATIONS = "bacs/notifications";
    private static final String BACS_NOTIFICATION_REQUEST = "bacsNotificationRequest";

    /**
     * The operation declares the secret key as its only security scheme, so this client does not
     * use the secret-key-or-OAuth variant that the instruments endpoints allow.
     */
    public BacsClientImpl(final ApiClient apiClient, final CheckoutConfiguration configuration) {
        super(apiClient, configuration, SdkAuthorizationType.SECRET_KEY);
    }

    @Override
    public CompletableFuture<BacsNotificationResponse> sendNotification(final BacsNotificationRequest bacsNotificationRequest) {
        validateParams(BACS_NOTIFICATION_REQUEST, bacsNotificationRequest);
        return apiClient.postAsync(buildPath(APMS, BACS_NOTIFICATIONS), sdkAuthorization(), BacsNotificationResponse.class, bacsNotificationRequest, null);
    }

    // Synchronous methods
    @Override
    public BacsNotificationResponse sendNotificationSync(final BacsNotificationRequest bacsNotificationRequest) {
        validateParams(BACS_NOTIFICATION_REQUEST, bacsNotificationRequest);
        return apiClient.post(buildPath(APMS, BACS_NOTIFICATIONS), sdkAuthorization(), BacsNotificationResponse.class, bacsNotificationRequest, null);
    }

}
