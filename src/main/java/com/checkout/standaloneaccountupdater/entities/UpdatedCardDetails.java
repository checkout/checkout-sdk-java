package com.checkout.standaloneaccountupdater.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdatedCardDetails {

    private Integer expiryMonth;
    private Integer expiryYear;
    private String encryptedCardNumber;
    private String bin;
    private String last4;
    private String fingerprint;
}