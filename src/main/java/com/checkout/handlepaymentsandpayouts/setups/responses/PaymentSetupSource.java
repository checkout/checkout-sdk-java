package com.checkout.handlepaymentsandpayouts.setups.responses;

import com.checkout.common.Address;
import com.checkout.common.PaymentSourceType;
import com.checkout.common.Phone;
import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Source information for payment setup confirm response
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentSetupSource {

    /**
     * The type of the payment source
     */
    private PaymentSourceType type;

    /**
     * The unique identifier of the payment source
     */
    private String id;

    /**
     * The billing address associated with the payment source
     */
    @SerializedName("billing_address")
    private Address billingAddress;

    /**
     * The phone number associated with the payment source
     */
    private Phone phone;

    /**
     * A unique fingerprint of the underlying card number
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
     * The card scheme
     */
    private String scheme;

    /**
     * The last four digits of the card number
     */
    @SerializedName("last_4")
    private String last4;

    /**
     * The expiry month of the card
     */
    @SerializedName("expiry_month")
    private Integer expiryMonth;

    /**
     * The expiry year of the card
     */
    @SerializedName("expiry_year")
    private Integer expiryYear;

    /**
     * The cardholder's name
     */
    private String name;

    /**
     * The card BIN
     */
    private String bin;

    /**
     * The card type
     */
    @SerializedName("card_type")
    private String cardType;

    /**
     * The card category
     */
    @SerializedName("card_category")
    private String cardCategory;

    /**
     * The name of the card issuer
     */
    private String issuer;

    /**
     * The card issuer country ISO2 code
     */
    @SerializedName("issuer_country")
    private String issuerCountry;

    /**
     * The card product type
     */
    @SerializedName("product_type")
    private String productType;

    /**
     * The Address Verification System check result
     */
    @SerializedName("avs_check")
    private String avsCheck;

    /**
     * The CVV check result
     */
    @SerializedName("cvv_check")
    private String cvvCheck;

    /**
     * The Payment Account Reference (PAR)
     */
    @SerializedName("payment_account_reference")
    private String paymentAccountReference;

    /**
     * Nested card class for card-specific details.
     */
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Card {
        /**
         * A unique fingerprint of the underlying card number
         */
        private String fingerprint;
        
        /**
         * The last four digits of the card number
         */
        @SerializedName("last_4")
        private String last4;
        
        /**
         * The expiry month of the card
         */
        @SerializedName("expiry_month")
        private Integer expiryMonth;
        
        /**
         * The expiry year of the card
         */
        @SerializedName("expiry_year")
        private Integer expiryYear;
        
        /**
         * The cardholder's name
         */
        private String name;
        
        /**
         * The card BIN
         */
        private String bin;
        
        /**
         * The card type
         */
        @SerializedName("card_type")
        private String cardType;
        
        /**
         * The card category
         */
        @SerializedName("card_category")
        private String cardCategory;
        
        /**
         * The name of the card issuer
         */
        private String issuer;
        
        /**
         * The card issuer country ISO2 code
         */
        @SerializedName("issuer_country")
        private String issuerCountry;
        
        /**
         * The product ID of the payment method
         */
        @SerializedName("product_id")
        private String productId;
        
        /**
         * The card product type
         */
        @SerializedName("product_type")
        private String productType;
    }
}