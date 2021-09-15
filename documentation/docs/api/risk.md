---
id: risk
title: Risk
---

You can find a list of request body parameters and possible outcomes [here](https://api-reference.checkout.com/#tag/Risk).

## Request a pre-authentication risk scan

Perform a pre-authentication fraud assessment using your defined risk settings.

```java
PreAuthenticationAssessmentRequest request = PreAuthenticationAssessmentRequest.builder()
    .date(Instant.now())
    .source(requestSource)
    .customer(new CustomerRequest("id", "john.doe@checkout.com", "John Doe"))
    .payment(RiskPayment.builder().psp("Checkout.com").id("78453878").build())
    .shipping(RiskShippingDetails.builder().address(
        Address.builder()
            .addressLine1("Checkout.com")
            .addressLine2("90 Tottenham Court Road")
            .city("London")
            .state("London")
            .zip("W1T 4TJ")
            .country(CountryCode.GB)
            .build()).build())
    .reference("ORD-1011-87AH")
    .description("Groceries")
    .amount(100L)
    .currency(Currency.GBP)
    .device(Device.builder()
        .ip("90.197.169.245")
        .location(Location.builder().longitude("0.1313").latitude("51.5107").build())
        .type("Phone")
        .os("ISO")
        .model("iPhone X")
        .date(Instant.now())
        .userAgent("Mozilla/5.0 (iPhone; CPU iPhone OS 11_0 like Mac OS X) AppleWebKit/604.1.38 (KHTML, like Gecko) Version/11.0 Mobile/15A372 Safari/604.1")
        .fingerprint("34304a9e3fg09302")
        .build())
    .metadata(Stream.of(
        new AbstractMap.SimpleImmutableEntry<>("VoucherCode", "loyalty_10"),
        new AbstractMap.SimpleImmutableEntry<>("discountApplied", "10"),
        new AbstractMap.SimpleImmutableEntry<>("customer_id", "2190EF321"))
        .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue)))
    .build();

PreAuthenticationAssessmentResponse response = api.riskClient().requestPreAuthenticationRiskScan(request).get();
```

## Request a pre-capture risk scan

Perform a pre-capture fraud assessment using your defined risk settings.

```java
PreCaptureAssessmentRequest request = PreCaptureAssessmentRequest.builder()
    .date(Instant.now())
    .source(requestSource)
    .customer(new CustomerRequest("id", "john.doe@checkout.com", "John Doe"))
    .payment(RiskPayment.builder().psp("Checkout.com").id("78453878").build())
    .shipping(RiskShippingDetails.builder().address(
        Address.builder()
            .addressLine1("Checkout.com")
            .addressLine2("90 Tottenham Court Road")
            .city("London")
            .state("London")
            .zip("W1T 4TJ")
            .country(CountryCode.GB)
            .build()).build())
    .amount(100L)
    .currency(Currency.GBP)
    .device(Device.builder()
        .ip("90.197.169.245")
        .location(Location.builder().longitude("0.1313").latitude("51.5107").build())
        .type("Phone")
        .os("ISO")
        .model("iPhone X")
        .date(Instant.now())
        .userAgent("Mozilla/5.0 (iPhone; CPU iPhone OS 11_0 like Mac OS X) AppleWebKit/604.1.38 (KHTML, like Gecko) Version/11.0 Mobile/15A372 Safari/604.1")
        .fingerprint("34304a9e3fg09302")
        .build())
    .metadata(Stream.of(
        new AbstractMap.SimpleImmutableEntry<>("VoucherCode", "loyalty_10"),
        new AbstractMap.SimpleImmutableEntry<>("discountApplied", "10"),
        new AbstractMap.SimpleImmutableEntry<>("customer_id", "2190EF321"))
        .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue)))
    .authenticationResult(AuthenticationResult.builder()
        .attempted(true)
        .challenged(true)
        .liabilityShifted(true)
        .method("3ds")
        .succeeded(true)
        .version("2.0")
        .build())
    .authorizationResult(AuthorizationResult.builder()
        .avsCode("V")
        .cvvResult("N")
        .build())
    .build();

PreCaptureAssessmentResponse response = api.riskClient().requestPreCaptureRiskScan(request).get();
```