package com.checkout.reconciliation;

import com.checkout.CheckoutValidationException;
import com.checkout.FakeApiTestFixture;
import com.checkout.TestHelper;
import com.github.tomakehurst.wiremock.junit.WireMockRule;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.time.Instant;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class ReconciliationTests extends FakeApiTestFixture {

    @ClassRule
    public static final WireMockRule wireMockRule = new WireMockRule(4000);
    private final static Instant FROM = Instant.from(ZonedDateTime.of(2020, 2, 22, 12, 31, 44, 0, ZoneOffset.UTC));
    private final static Instant TO = Instant.from(ZonedDateTime.of(2020, 2, 26, 13, 21, 34, 0, ZoneOffset.UTC));
    private final static Instant FROM_422 = Instant.from(ZonedDateTime.of(2020, 3, 22, 12, 31, 44, 0, ZoneOffset.UTC));
    private final static Instant TO_422 = Instant.from(ZonedDateTime.of(2020, 3, 26, 13, 21, 34, 0, ZoneOffset.UTC));
    private final static String PAYMENT_ID = "pay_nezg6bx2k22utmk4xm5s2ughxi";
    private final static String PAYMENT_ID_422 = "pay_11";
    @Rule
    public ExpectedException exceptionRule = ExpectedException.none();

    @BeforeClass
    public static void before() {
        wireMockRule.stubFor(get(urlEqualTo("/reporting/payments?from=" + FROM.toString() + "&to=" + TO.toString() + "&reference=ORD-5023-4E89&limit=350"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody(TestHelper.readFile("src/test/resources/reconciliation/payment_report.json"))));
        wireMockRule.stubFor(get(urlEqualTo("/reporting/payments?from=" + FROM_422.toString() + "&to=" + TO_422.toString() + "&reference=ORD-5023-4E89&limit=350"))
                .willReturn(aResponse()
                        .withStatus(422)
                        .withHeader("Content-Type", "application/json")
                        .withBody(TestHelper.readFile("src/test/resources/reconciliation/payment_report_invalid_data.json"))));

        wireMockRule.stubFor(get(urlEqualTo("/reporting/payments/" + PAYMENT_ID))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody(TestHelper.readFile("src/test/resources/reconciliation/single_payment_report.json"))));
        wireMockRule.stubFor(get(urlEqualTo("/reporting/payments/" + PAYMENT_ID_422))
                .willReturn(aResponse()
                        .withStatus(422)
                        .withHeader("Content-Type", "application/json")
                        .withBody(TestHelper.readFile("src/test/resources/reconciliation/single_payment_report_invalid_data.json"))));

        wireMockRule.stubFor(get(urlEqualTo("/reporting/statements?from=" + FROM.toString() + "&to=" + TO.toString()))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody(TestHelper.readFile("src/test/resources/reconciliation/statement_report.json"))));
        wireMockRule.stubFor(get(urlEqualTo("/reporting/statements?from=" + FROM_422.toString() + "&to=" + TO_422.toString()))
                .willReturn(aResponse()
                        .withStatus(422)
                        .withHeader("Content-Type", "application/json")
                        .withBody(TestHelper.readFile("src/test/resources/reconciliation/statement_report_invalid_data.json"))));


        wireMockRule.start();
    }

    @AfterClass
    public static void after() {
        wireMockRule.stop();
    }


    @Test
    public void payment_report_200() throws Exception {
        PaymentReportResponse reportResponse = getApi().reconciliationClient()
                .paymentsReportAsync(FROM, TO, "ORD-5023-4E89", 350)
                .get();

        assertNotNull(reportResponse);
        assertEquals(2, reportResponse.getCount());
        assertEquals(2, reportResponse.getData().size());
    }

    @Test
    public void payment_report_422() throws Exception {
        exceptionRule.expectCause(is(instanceOf(CheckoutValidationException.class)));
        exceptionRule.expectMessage("com.checkout.CheckoutValidationException: " +
                "The API response status code (422) does not indicate success. " +
                "A validation error of type request_invalid occurred with error codes [payment_source_required].");

        getApi().reconciliationClient()
                .paymentsReportAsync(FROM_422, TO_422, "ORD-5023-4E89", 350)
                .get();
    }

    @Test
    public void single_payment_report_200() throws Exception {
        PaymentReportResponse reportResponse = getApi().reconciliationClient()
                .singlePaymentReportAsync(PAYMENT_ID)
                .get();

        assertNotNull(reportResponse);
        assertEquals(1, reportResponse.getCount());
        assertEquals(1, reportResponse.getData().size());
    }

    @Test
    public void single_payment_report_422() throws Exception {
        exceptionRule.expectCause(is(instanceOf(CheckoutValidationException.class)));
        exceptionRule.expectMessage("com.checkout.CheckoutValidationException: " +
                "The API response status code (422) does not indicate success. " +
                "A validation error of type request_invalid occurred with error codes [payment_source_required].");

        getApi().reconciliationClient()
                .singlePaymentReportAsync(PAYMENT_ID_422)
                .get();

    }

    @Test
    public void statement_report_200() throws Exception {
        StatementReportResponse statementReportResponse = getApi().reconciliationClient()
                .statementsReportAsync(FROM, TO)
                .get();

        assertNotNull(statementReportResponse);
        assertEquals(1, statementReportResponse.getCount());
        assertEquals(1, statementReportResponse.getData().size());
    }

    @Test
    public void statement_report_422() throws Exception {
        exceptionRule.expectCause(is(instanceOf(CheckoutValidationException.class)));
        exceptionRule.expectMessage("com.checkout.CheckoutValidationException: " +
                "The API response status code (422) does not indicate success. " +
                "A validation error of type request_invalid occurred with error codes [payment_source_required].");

        getApi().reconciliationClient()
                .statementsReportAsync(FROM_422, TO_422)
                .get();

    }


}
