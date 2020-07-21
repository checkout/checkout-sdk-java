---
id: getting_started
title: Getting Started
sidebar_label: Getting Started
---

The Checkout.com Java SDK makes it easy to interact with the Unified Payments API. You can easily accept payments from cards, digital wallets and the most popular alternative payment methods, as well as pay out to a variety of destinations, all using the same integration.

[![Build Status](https://travis-ci.com/checkout/checkout-sdk-java.svg?branch=master)](https://travis-ci.com/checkout/checkout-sdk-java) [![GitHub license](https://img.shields.io/github/license/checkout/checkout-sdk-java.svg)](https://github.com/checkout/checkout-sdk-java/blob/master/LICENSE) [![GitHub release](https://img.shields.io/github/release/checkout/checkout-sdk-java.svg)](https://GitHub.com/checkout/checkout-sdk-java/releases/)

## Get a test account

If you are starting the integration process, and you want to start interacting with Checkout.com's API, you will need a test account, so you can get your API keys.

export const RedirectButton = ({text, link}) => (
<a
href={link}
target="\_blank"
className="get-test-account">{text}</a>
);

<RedirectButton text="Get a test account" link="https://www.checkout.com/get-test-account" />

## Authentication

export const Highlight = ({children, color}) => (
<span
style={{
      backgroundColor: color,
      borderRadius: '2px',
      color: '#fff',
      padding: '0.2rem',
    }}>
{children}
</span>
);

When you sign up for an account, you are given a secret and public API key pair. They will be used to initialise the SDK so you can interact with the various endpoints. You can find the keys by navigating to <Highlight color="#1877F2">Settings > Channels > API keys</Highlight> in Checkout.com's Hub.

:::warning

Never share your secret keys. Keep them guarded and secure.

:::

## Payload and Responses

If you want to see all the parameters that you can provide in API requests, as well as examples of possible responses, please follow the Checkout.com [API Reference](https://api-reference.checkout.com/).
