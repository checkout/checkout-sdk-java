package com.checkout.handlepaymentsandpayouts.setups.responses;

import com.checkout.common.PaymentSourceType;
import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents a payment setup source.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentSetupSource {

    /**
     * The payment source type.
     */
    private PaymentSourceType type;

    /**
     * The unique identifier of the payment setup source.
     */
    private String id;

    /**
     * The fingerprint of the payment setup source.
     */
    private String fingerprint;

    /**
     * The card details if the source is a card.
     */
    private Card card;

    /**
     * The customer information.
     */
    private String customer;

    /**
     * The scheme of the payment method.
     */
    private String scheme;

    /**
     * The last four digits of the payment method.
     */
    @SerializedName("last_4")
    private String last4;

    /**
     * The expiry month of the payment method.
     */
    @SerializedName("expiry_month")
    private Integer expiryMonth;

    /**
     * The expiry year of the payment method.
     */
    @SerializedName("expiry_year")
    private Integer expiryYear;

    /**
     * The name on the payment method.
     */
    private String name;

    /**
     * The BIN (Bank Identification Number) of the payment method.
     */
    private String bin;

    /**
     * The card type (Credit/Debit).
     */
    @SerializedName("card_type")
    private String cardType;

    /**
     * The card category.
     */
    @SerializedName("card_category")
    private String cardCategory;

    /**
     * The issuer of the payment method.
     */
    private String issuer;

    /**
     * The issuer country of the payment method.
     */
    @SerializedName("issuer_country")
    private String issuerCountry;

    /**
     * The product ID of the payment method.
     */
    @SerializedName("product_id")
    private String productId;

    /**
     * The product type of the payment method.
     */
    @SerializedName("product_type")
    private String productType;

    /**
     * Nested card class for card-specific details.
     */
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Card {
        private String fingerprint;
        
        @SerializedName("last_4")
        private String last4;
        
        @SerializedName("expiry_month")
        private Integer expiryMonth;
        
        @SerializedName("expiry_year")
        private Integer expiryYear;
        
        private String name;
        private String bin;
        
        @SerializedName("card_type")
        private String cardType;
        
        @SerializedName("card_category")
        private String cardCategory;
        
        private String issuer;
        
        @SerializedName("issuer_country")
        private String issuerCountry;
        
        @SerializedName("product_id")
        private String productId;
        
        @SerializedName("product_type")
        private String productType;
    }
}