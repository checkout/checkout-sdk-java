package com.checkout.handlepaymentsandpayouts.setups.responses;

import java.util.List;

import com.checkout.common.Address;
import com.checkout.common.PaymentSourceType;
import com.checkout.common.Phone;
import com.checkout.handlepaymentsandpayouts.payments.common.source.cardsource.CardType;
import com.checkout.handlepaymentsandpayouts.payments.common.source.cardsource.CardCategoryType;
import com.checkout.handlepaymentsandpayouts.payments.common.source.cardsource.CardWalletType;
import com.checkout.handlepaymentsandpayouts.payments.common.source.cardsource.AccountUpdateStatusType;
import com.checkout.handlepaymentsandpayouts.payments.common.source.cardsource.accountholder.AbstractAccountHolder;

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
public final class PaymentSetupSource {

    /**
     * The payment source type.
     * [Optional]
     */
    private PaymentSourceType type;

    /**
     * The expiry month.
     * [Optional]
     */
    @SerializedName("expiry_month")
    private Integer expiryMonth;

    /**
     * The expiry year.
     * [Optional]
     */
    @SerializedName("expiry_year")
    private Integer expiryYear;

    /**
     * The last four digits of the card number.
     * [Optional]
     */
    private String last4;

    /**
     * Uniquely identifies this particular card number. You can use this to compare cards across customers.
     * [Optional]
     */
    private String fingerprint;

    /**
     * The card issuer's Bank Identification Number (BIN).
     * [Optional]
     */
    private String bin;

    /**
     * The payment source identifier that can be used for subsequent payments.
     * For new sources, this will only be returned if the payment was approved.
     * [Optional]
     */
    private String id;

    /**
     * The payment source owner's billing address.
     * [Optional]
     */
    @SerializedName("billing_address")
    private Address billingAddress;

    /**
     * The payment source owner's phone number.
     * [Optional]
     */
    private Phone phone;

    /**
     * The cardholder's name.
     * [Optional]
     */
    private String name;

    /**
     * The card scheme.
     * [Optional]
     */
    private String scheme;

    /**
     * Deprecated. Replaced by local_schemes. The local co-branded card scheme.
     * [Optional]
     */
    @SerializedName("scheme_local")
    private String schemeLocal;

    /**
     * The local co-branded card schemes.
     * [Optional]
     */
    @SerializedName("local_schemes")
    private List<String> localSchemes;

    /**
     * The card type.
     * [Optional]
     */
    @SerializedName("card_type")
    private CardType cardType;

    /**
     * The card category.
     * [Optional]
     */
    @SerializedName("card_category")
    private CardCategoryType cardCategory;

    /**
     * The card wallet type.
     * [Optional]
     */
    @SerializedName("card_wallet_type")
    private CardWalletType cardWalletType;

    /**
     * The name of the card issuer.
     * [Optional]
     */
    private String issuer;

    /**
     * The card issuer's country (two-letter ISO code).
     * [Optional]
     */
    @SerializedName("issuer_country")
    private String issuerCountry;

    /**
     * The issuer/card scheme product identifier.
     * [Optional]
     */
    @SerializedName("product_id")
    private String productId;

    /**
     * The issuer/card scheme product type.
     * [Optional]
     */
    @SerializedName("product_type")
    private String productType;

    /**
     * The Address Verification System check result.
     * [Optional]
     */
    @SerializedName("avs_check")
    private String avsCheck;

    /**
     * The card verification value (CVV) check result.
     * [Optional]
     */
    @SerializedName("cvv_check")
    private String cvvCheck;

    /**
     * A unique reference to the underlying card for network tokens (e.g., Apple Pay, Google Pay).
     * [Optional]
     */
    @SerializedName("payment_account_reference")
    private String paymentAccountReference;

    /**
     * The JWE encrypted full card number that has been updated by real-time account updater.
     * [Optional]
     */
    @SerializedName("encrypted_card_number")
    private String encryptedCardNumber;

    /**
     * Specifies what card information was updated by the Real-Time Account Updater.
     * [Optional]
     */
    @SerializedName("account_update_status")
    private AccountUpdateStatusType accountUpdateStatus;

    /**
     * Provides the failure code if the Real-Time Account Updater fails to update the card information.
     * [Optional]
     */
    @SerializedName("account_update_failure_code")
    private String accountUpdateFailureCode;

    /**
     * Information about the account holder of the card.
     * [Optional]
     */
    @SerializedName("account_holder")
    private AbstractAccountHolder accountHolder;
}
