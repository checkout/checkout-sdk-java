package com.checkout;

import com.checkout.accounts.AccountsClient;
import com.checkout.accounts.AccountsClientImpl;
import com.checkout.balances.BalancesClient;
import com.checkout.balances.BalancesClientImpl;
import com.checkout.customers.CustomersClient;
import com.checkout.customers.CustomersClientImpl;
import com.checkout.disputes.DisputesClient;
import com.checkout.disputes.DisputesClientImpl;
import com.checkout.financial.FinancialClient;
import com.checkout.financial.FinancialClientImpl;
import com.checkout.forex.ForexClient;
import com.checkout.forex.ForexClientImpl;
import com.checkout.forward.ForwardClient;
import com.checkout.forward.ForwardClientImpl;
import com.checkout.instruments.InstrumentsClient;
import com.checkout.instruments.InstrumentsClientImpl;
import com.checkout.issuing.IssuingClient;
import com.checkout.issuing.IssuingClientImpl;
import com.checkout.metadata.MetadataClient;
import com.checkout.metadata.MetadataClientImpl;
import com.checkout.payments.PaymentsClient;
import com.checkout.payments.PaymentsClientImpl;
import com.checkout.payments.contexts.PaymentContextsClient;
import com.checkout.payments.contexts.PaymentContextsClientImpl;
import com.checkout.payments.hosted.HostedPaymentsClient;
import com.checkout.payments.hosted.HostedPaymentsClientImpl;
import com.checkout.payments.links.PaymentLinksClient;
import com.checkout.payments.links.PaymentLinksClientImpl;
import com.checkout.payments.sessions.PaymentSessionsClient;
import com.checkout.payments.sessions.PaymentSessionsClientImpl;
import com.checkout.reports.ReportsClient;
import com.checkout.reports.ReportsClientImpl;
import com.checkout.risk.RiskClient;
import com.checkout.risk.RiskClientImpl;
import com.checkout.sessions.SessionsClient;
import com.checkout.sessions.SessionsClientImpl;
import com.checkout.tokens.TokensClient;
import com.checkout.tokens.TokensClientImpl;
import com.checkout.transfers.TransfersClient;
import com.checkout.transfers.TransfersClientImpl;
import com.checkout.workflows.WorkflowsClient;
import com.checkout.workflows.WorkflowsClientImpl;

import java.net.URI;

public class CheckoutApiImpl extends AbstractCheckoutApmApi implements CheckoutApi, CheckoutApiClient {

    private final TokensClient tokensClient;
    private final PaymentsClient paymentsClient;
    private final CustomersClient customersClient;
    private final DisputesClient disputesClient;
    private final InstrumentsClient instrumentsClient;
    private final RiskClient riskClient;
    private final WorkflowsClient workflowsClient;
    private final AccountsClient accountsClient;
    private final SessionsClient sessionsClient;
    private final ForexClient forexClient;
    private final PaymentLinksClient paymentLinksClient;
    private final HostedPaymentsClient hostedPaymentsClient;
    private final TransfersClient transfersClient;
    private final BalancesClient balancesClient;
    private final ReportsClient reportsClient;
    private final MetadataClient metadataClient;
    private final FinancialClient financialClient;
    private final IssuingClient issuingClient;
    private final PaymentContextsClient paymentContextsClient;
    private final PaymentSessionsClient paymentSessionsClient;
    private final ForwardClient forwardClient;

    public CheckoutApiImpl(final CheckoutConfiguration configuration) {
        super(configuration);
        this.tokensClient = new TokensClientImpl(this.apiClient, configuration);
        this.paymentsClient = new PaymentsClientImpl(this.apiClient, configuration);
        this.customersClient = new CustomersClientImpl(this.apiClient, configuration);
        this.disputesClient = new DisputesClientImpl(this.apiClient, configuration, SdkAuthorizationType.SECRET_KEY_OR_OAUTH);
        this.instrumentsClient = new InstrumentsClientImpl(this.apiClient, configuration);
        this.riskClient = new RiskClientImpl(this.apiClient, configuration);
        this.workflowsClient = new WorkflowsClientImpl(this.apiClient, configuration);
        this.sessionsClient = new SessionsClientImpl(this.apiClient, configuration);
        this.forexClient = new ForexClientImpl(this.apiClient, configuration);
        this.paymentLinksClient = new PaymentLinksClientImpl(this.apiClient, configuration);
        this.hostedPaymentsClient = new HostedPaymentsClientImpl(this.apiClient, configuration);
        this.transfersClient = new TransfersClientImpl(getTransfersClient(configuration), configuration);
        this.balancesClient = new BalancesClientImpl(getBalancesClient(configuration), configuration);
        this.reportsClient = new ReportsClientImpl(this.apiClient, configuration);
        this.metadataClient = new MetadataClientImpl(this.apiClient, configuration);
        this.financialClient = new FinancialClientImpl(this.apiClient, configuration);
        this.issuingClient = new IssuingClientImpl(this.apiClient, configuration);
        this.accountsClient = new AccountsClientImpl(this.apiClient,
                getFilesClient(configuration),
                configuration);
        this.paymentContextsClient = new PaymentContextsClientImpl(this.apiClient, configuration);
        this.paymentSessionsClient = new PaymentSessionsClientImpl(this.apiClient, configuration);
        this.forwardClient = new ForwardClientImpl(this.apiClient, configuration);
    }

    @Override
    public TokensClient tokensClient() {
        return tokensClient;
    }

    @Override
    public PaymentsClient paymentsClient() {
        return paymentsClient;
    }

    @Override
    public CustomersClient customersClient() {
        return customersClient;
    }

    @Override
    public DisputesClient disputesClient() {
        return disputesClient;
    }

    @Override
    public InstrumentsClient instrumentsClient() {
        return instrumentsClient;
    }

    @Override
    public RiskClient riskClient() {
        return riskClient;
    }

    @Override
    public WorkflowsClient workflowsClient() {
        return workflowsClient;
    }

    @Override
    public AccountsClient accountsClient() {
        return accountsClient;
    }

    @Override
    public SessionsClient sessionsClient() {
        return sessionsClient;
    }

    @Override
    public ForexClient forexClient() {
        return forexClient;
    }

    @Override
    public PaymentLinksClient paymentLinksClient() {
        return paymentLinksClient;
    }

    @Override
    public HostedPaymentsClient hostedPaymentsClient() {
        return hostedPaymentsClient;
    }

    @Override
    public BalancesClient balancesClient() {
        return balancesClient;
    }

    @Override
    public TransfersClient transfersClient() {
        return transfersClient;
    }

    @Override
    public ReportsClient reportsClient() {
        return reportsClient;
    }

    @Override
    public MetadataClient metadataClient() {
        return metadataClient;
    }

    @Override
    public FinancialClient financialClient() { return financialClient; }

    @Override
    public IssuingClient issuingClient() { return issuingClient; }

    @Override
    public PaymentContextsClient paymentContextsClient() { return paymentContextsClient; }

    @Override
    public PaymentSessionsClient paymentSessionsClient() { return paymentSessionsClient; }

    @Override
    public ForwardClient forwardClient() { return forwardClient; }

    private ApiClient getFilesClient(final CheckoutConfiguration configuration) {
        return new ApiClientImpl(configuration, new FilesApiUriStrategy(configuration));
    }

    private ApiClient getTransfersClient(final CheckoutConfiguration configuration) {
        return new ApiClientImpl(configuration, new TransfersApiUriStrategy(configuration));
    }

    private ApiClient getBalancesClient(final CheckoutConfiguration configuration) {
        return new ApiClientImpl(configuration, new BalancesApiUriStrategy(configuration));
    }

    private static class FilesApiUriStrategy implements UriStrategy {

        private final CheckoutConfiguration configuration;

        private FilesApiUriStrategy(final CheckoutConfiguration configuration) {
            this.configuration = configuration;
        }

        @Override
        public URI getUri() {
            return configuration.getEnvironment().getFilesApi();
        }

    }

    private static class TransfersApiUriStrategy implements UriStrategy {

        private final CheckoutConfiguration configuration;

        private TransfersApiUriStrategy(final CheckoutConfiguration configuration) {
            this.configuration = configuration;
        }

        @Override
        public URI getUri() {
            return configuration.getEnvironment().getTransfersApi();
        }

    }

    private static class BalancesApiUriStrategy implements UriStrategy {

        private final CheckoutConfiguration configuration;

        private BalancesApiUriStrategy(final CheckoutConfiguration configuration) {
            this.configuration = configuration;
        }

        @Override
        public URI getUri() {
            return configuration.getEnvironment().getBalancesApi();
        }

    }

}
