[![Build Status](https://travis-ci.com/smallporgies/checkout-sdk-java.svg?token=yeJf37D6ztNEaohydeEw&branch=master)](https://travis-ci.com/smallporgies/checkout-sdk-java)

# Checkout.com Java SDK

Built with Java 12 and Gradle 5

## Dependencies
This project is built with Jackson Databind and Datatype-JSR310 versions 2.9.8

## To Build

The tests require a sandbox account to connect to. Once you have one you can specify the `CHECKOUT_PUBLIC_KEY` and `CHECKOUT_SECRET_KEY` using environment variables.
Then just run:
```
gradlew build
```
