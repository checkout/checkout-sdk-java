---
id: customers
title: Customers
sidebar_position: 1
---

You can find a list of request body parameters and possible outcomes [here](https://api-reference.checkout.com/#tag/Customers).

export const Highlight = ({children, color}) => (
<span
style={{
color: color,
padding: '0.2rem',
}}>
{children}
</span>
);

## Create a customer

Create a customer which can be linked to one or more payment instruments, and can be passed as a source when making a payment, using the customer’s default instrument.

```java
CustomerRequest request = CustomerRequest.builder()
        .email("jhon.doe@checkout.com")
        .name("Jhon Doe")
        .phone(Phone.builder()
            .countryCode("1")
            .number("321654987")
            .build())
        .build();

IdResponse response = api.customersClient().create(request).get();
```

## Get customer details

Returns details of a customer and their instruments

```java
CustomerDetailsResponse response = api.customersClient().get(request).get();
```

## Update customer details

Update details of a customer

```java
CustomerRequest request = CustomerRequest.builder()
        .email("jhon.doe@checkout.com")
        .name("Jhon Doe")
        .phone(Phone.builder()
            .countryCode("1")
            .number("321654987")
            .build())
        .build();

Void response = api.customersClient().update("cus_123456789ASDZX", request).get();
```

## Delete a customer

Delete a customer and all of their linked payment instruments

```java
Void response = api.customersClient().delete("cus_123456789ASDZX").get();
```
