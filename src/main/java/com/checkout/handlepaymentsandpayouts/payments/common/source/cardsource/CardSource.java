package com.checkout.handlepaymentsandpayouts.payments.common.source.cardsource;

import com.checkout.handlepaymentsandpayouts.payments.common.source.AbstractSource;
import com.checkout.handlepaymentsandpayouts.payments.common.source.SourceType;
import com.checkout.handlepaymentsandpayouts.payments.common.source.cardsource.accountholder.AbstractAccountHolder;
import com.checkout.handlepaymentsandpayouts.payments.common.source.cardsource.billingaddress.BillingAddress;
import com.checkout.handlepaymentsandpayouts.payments.common.source.cardsource.phone.Phone;
import com.google.gson.annotations.SerializedName;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * card source Class
 * The source of the payment
 */
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public final class CardSource extends AbstractSource {

    /**
     * The expiry month
     * [Required]
     * [ 1 .. 2 ] characters  &gt;= 1
     * &gt;= 1
     */
    @SerializedName("expiry_month")
    private Integer expiryMonth;

    /**
     * The expiry year
     * [Required]
     * 4 characters
     */
    @SerializedName("expiry_year")
    private Integer expiryYear;

    /**
     * The last four digits of the card number
     * [Required]
     */
    @SerializedName("last4")
    private String last4;

    /**
     * Uniquely identifies this particular card number. You can use this to compare cards across customers.
     * [Required]
     */
    private String fingerprint;

    /**
     * The card issuer's Bank Identification Number (BIN)
     * [Required]
     * &lt;= 8
     */
    private String bin;

    /**
     * The payment source identifier that can be used for subsequent payments. For new sources, this will only be
     * returned if the payment was approved
     * [Optional]
     */
    private String id;

    /**
     * The payment source owner's billing address
     * [Optional]
     */
    @SerializedName("billing_address")
    private BillingAddress billingAddress;

    /**
     * The payment source owner's phone number
     * [Optional]
     */
    private Phone phone;

    /**
     * The cardholder's name
     * [Optional]
     */
    private String name;

    /**
     * The card scheme
     * [Optional]
     */
    private String scheme;

    /**
     * [DEPRECATED]
     *  Replaced by local_schemes
     * The local co-branded card scheme.
     * [Optional]
     */
    @Deprecated
    @SerializedName("scheme_local")
    private String schemeLocal;

    /**
     * The local co-branded card schemes.
     * [Optional]
     */
    @SerializedName("local_schemes")
    private List<String> localSchemes;

    /**
     * The card type
     * [Optional]
     */
    @SerializedName("card_type")
    private CardType cardType;

    /**
     * The card category
     * [Optional]
     */
    @SerializedName("card_category")
    private CardCategoryType cardCategory;

    /**
     * The card wallet type
     * [Optional]
     */
    @SerializedName("card_wallet_type")
    private CardWalletType cardWalletType;

    /**
     * The name of the card issuer
     * [Optional]
     */
    private String issuer;

    /**
     * The card issuer's country (two-letter ISO code)
     * [Optional]
     * 2 characters
     */
    @SerializedName("issuer_country")
    private String issuerCountry;

    /**
     * The issuer/card scheme product identifier
     * [Optional]
     */
    @SerializedName("product_id")
    private String productId;

    /**
     * The issuer/card scheme product type
     * [Optional]
     */
    @SerializedName("product_type")
    private String productType;

    /**
     * The Address Verification System check result
     * [Optional]
     */
    @SerializedName("avs_check")
    private String avsCheck;

    /**
     * The card verification value (CVV) check result
     * [Optional]
     */
    @SerializedName("cvv_check")
    private String cvvCheck;

    /**
     * A unique reference to the underlying card for network tokens (e.g., Apple Pay, Google Pay)
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
     * Information about the account holder of the card
     * [Optional]
     */
    @SerializedName("account_holder")
    private AbstractAccountHolder accountHolder;

    /**
     * Initializes a new instance of the CardSource class.
     */
    @Builder
    private CardSource(
        final Integer expiryMonth,
        final Integer expiryYear,
        final String last4,
        final String fingerprint,
        final String bin,
        final String id,
        final BillingAddress billingAddress,
        final Phone phone,
        final String name,
        final String scheme,
        final String schemeLocal,
        final List<String> localSchemes,
        final CardType cardType,
        final CardCategoryType cardCategory,
        final CardWalletType cardWalletType,
        final String issuer,
        final String issuerCountry,
        final String productId,
        final String productType,
        final String avsCheck,
        final String cvvCheck,
        final String paymentAccountReference,
        final String encryptedCardNumber,
        final AccountUpdateStatusType accountUpdateStatus,
        final String accountUpdateFailureCode,
        final AbstractAccountHolder accountHolder
    ) {
        super(SourceType.CARD);
        this.expiryMonth = expiryMonth;
        this.expiryYear = expiryYear;
        this.last4 = last4;
        this.fingerprint = fingerprint;
        this.bin = bin;
        this.id = id;
        this.billingAddress = billingAddress;
        this.phone = phone;
        this.name = name;
        this.scheme = scheme;
        this.schemeLocal = schemeLocal;
        this.localSchemes = localSchemes;
        this.cardType = cardType;
        this.cardCategory = cardCategory;
        this.cardWalletType = cardWalletType;
        this.issuer = issuer;
        this.issuerCountry = issuerCountry;
        this.productId = productId;
        this.productType = productType;
        this.avsCheck = avsCheck;
        this.cvvCheck = cvvCheck;
        this.paymentAccountReference = paymentAccountReference;
        this.encryptedCardNumber = encryptedCardNumber;
        this.accountUpdateStatus = accountUpdateStatus;
        this.accountUpdateFailureCode = accountUpdateFailureCode;
        this.accountHolder = accountHolder;
    }

    public CardSource() {
        super(SourceType.CARD);
    }

}
