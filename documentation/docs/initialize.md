---
id: initialize
title: Initialize
---

Create the client like so:

```java
String secretKey = "my_checkout_secret_key";
String publicKey = "my_checkout_public_key";
boolean useSandbox = true;

CheckoutApi api = CheckoutApiImpl.create(secretKey, useSandbox, publicKey);
```

Set **useSandbox** to _true_ to point to the Sandbox endpoint https://api.sandbox.checkout.com/.
