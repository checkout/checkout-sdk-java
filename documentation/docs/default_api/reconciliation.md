---
id: reconciliation
title: Reconciliation
---

The full list of request body parameters and possible outcomes can be found [here](https://api-reference.checkout.com/#tag/Reconciliation).

Quickly find all fees associated with each of your payments, so you can concentrate on the important stuff while streamlining your financial reporting.

## Get JSON payments report

Returns a JSON report containing all payments within your specified parameters. You can reconcile the data from this report against your statements (which can be found in the Hub), the list of payments in the Hub (using the Reference field) or your own systems. Note: no payments from before 7 February 2019 at 00.00.00 UTC will appear when using the payments endpoint. To view earlier payments, please contact our support team.

```java
//You can use multiple fields in filter object such as a query parameter request
ReconciliationQueryPaymentsFilter filter = ReconciliationQueryPaymentsFilter
        .builder()
        .from()
        .to()
        .reference()
        .limit()
        .build();

ReconciliationPaymentReportResponse response = api.reconciliationClient().queryPaymentsReport(filter).get();
```

## Get JSON single payment report

Returns a JSON payment report containing all the data related to a specific payment, based on the payment's identifier. Note: no payments from before 7 February 2019 at 00.00.00 UTC will appear when using the payments endpoint. To view earlier payments, please contact our support team.

```java
ReconciliationPaymentReportResponse response = api.reconciliationClient().singlePaymentReportAsync(paymentId).get();
```

## Get CSV payments report

In addition to the JSON format returned by the reporting/payments endpoint, you can also download a CSV report containing the same data. [Learn more about how to read your CSV report](https://docs.checkout.com/reporting-and-insights/reconciliation-api/payments-endpoint#Paymentsendpoint-HowtoreadtheCSVfile).

```java
//The parameter and the response will be the absolute path for a file
String file = api.reconciliationClient().retrieveCSVPaymentReport("/etc/foo/payment_report.csv").get();
```

## Get JSON statements report

Returns a JSON report containing all statements within your specified parameters. Please note that the timezone for the request will be UTC.

```java
QueryFilterDateRange filter = QueryFilterDateRange
        .builder()
        .from()
        .to()
        .build();

StatementReportResponse response = api.reconciliationClient().queryStatementsReport(filter).get();
```

## Get CSV single statement report

Downloads a CSV statement report containing all the data related to a specific statement, based on the statement's identifier.

```java
//The parameter and the response will be the absolute path for a file
String file = api.reconciliationClient().retrieveCSVSingleStatementReport("/etc/foo/single_statement_report.csv").get();
```

## Get CSV statements report

In addition to the JSON format returned by the reporting/statements endpoint, you can also download a CSV report containing the same data.

```java
//The parameter and the response will be the absolute path for a file
String file = api.reconciliationClient().retrieveCSVStatementsReport("/etc/foo/statement_report.csv").get();
```
