package com.checkout.financial;

import com.checkout.AbstractClient;
import com.checkout.ApiClient;
import com.checkout.CheckoutConfiguration;
import com.checkout.SdkAuthorizationType;

import java.util.concurrent.CompletableFuture;

import static com.checkout.common.CheckoutUtils.validateParams;

public class FinancialClientImpl extends AbstractClient implements FinancialClient {

    private static final String FINANCIAL_ACTIONS_PATH = "financial-actions";

    public FinancialClientImpl(final ApiClient apiClient, final CheckoutConfiguration configuration) {
        super(apiClient, configuration, SdkAuthorizationType.SECRET_KEY_OR_OAUTH);
    }

    public CompletableFuture<FinancialActionsQueryResponse> query(FinancialActionsQueryFilter queryFilter) {
        validateParams("queryFilter", queryFilter);
        return apiClient.queryAsync(
                FINANCIAL_ACTIONS_PATH,
                sdkAuthorization(),
                queryFilter,
                FinancialActionsQueryResponse.class);
    }
}
