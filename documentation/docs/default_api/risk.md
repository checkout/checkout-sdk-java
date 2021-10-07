---
id: risk
title: Risk
---

The full list of request body parameters and possible outcomes can be found [here](https://api-reference.checkout.com/#tag/Risk).

## Request a pre-authentication risk scan

Perform a pre-authentication fraud assessment using your defined risk settings.

```java
PreAuthenticationAssessmentRequest request = PreAuthenticationAssessmentRequest.builder()
    .date()
    .source(requestSource)
    .customer(new CustomerRequest("id", "email", "name"))
    .payment(RiskPayment.builder().build())
    .shipping(RiskShippingDetails.builder().address(Address.builder().build()).build())
    .reference("ORD-1011-87AH")
    .description("Groceries")
    .amount(100L)
    .currency(Currency.GBP)
    .device(Device.builder().build())
    .metadata()
    .build();

PreAuthenticationAssessmentResponse response = api.riskClient().requestPreAuthenticationRiskScan(request).get();
```

## Request a pre-capture risk scan

Perform a pre-capture fraud assessment using your defined risk settings.

```java
PreCaptureAssessmentRequest request = PreCaptureAssessmentRequest.builder()
    .date(Instant.now())
    .source(requestSource)
    .customer(new CustomerRequest("id", "email", "name"))
    .payment(RiskPayment.builder().build())
    .shipping(RiskShippingDetails.builder().address(Address.builder().build()).build())
    .amount()
    .currency()
    .device(Device.builder().build())
    .metadata()
    .authenticationResult(AuthenticationResult.builder().build())
    .authorizationResult(AuthorizationResult.builder().build())
    .build();

PreCaptureAssessmentResponse response = api.riskClient().requestPreCaptureRiskScan(request).get();
```