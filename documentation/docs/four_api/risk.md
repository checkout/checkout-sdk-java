---
id: risk
title: Risk
---

The full list of request body parameters and possible outcomes can be found [here](https://api-reference.checkout.com/preview/crusoe/#tag/Risk).

## Request a pre-authentication risk scan

Perform a pre-authentication fraud assessment using your defined risk settings.

```java
PreAuthenticationAssessmentRequest request = PreAuthenticationAssessmentRequest.builder()
    .date(Instant.now())
    .source(requestSource)
    .customer()
    .payment(RiskPayment.builder().build())
    .shipping(RiskShippingDetails.builder().build())
    .reference()
    .description()
    .amount()
    .currency()
    .device(Device.builder().build())
    .metadata()
    .build();

PreAuthenticationAssessmentResponse response = fourApi.riskClient().requestPreAuthenticationRiskScan(request).get();
```

## Request a pre-capture risk scan

Perform a pre-capture fraud assessment using your defined risk settings.

```java
PreCaptureAssessmentRequest request = PreCaptureAssessmentRequest.builder()
    .date()
    .source(requestSource)
    .customer()
    .payment()
    .shipping(RiskShippingDetails.builder().build())
    .amount()
    .currency()
    .device(Device.builder().build())
    .metadata()
    .authenticationResult(AuthenticationResult.builder().build())
    .authorizationResult(AuthorizationResult.builder().build())
    .build();

PreCaptureAssessmentResponse response = fourApi.riskClient().requestPreCaptureRiskScan(request).get();
```
