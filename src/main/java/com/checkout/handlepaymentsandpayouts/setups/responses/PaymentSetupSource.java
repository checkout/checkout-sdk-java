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
    private Integer expiryMonth;

    /**
     * The expiry year.
     * [Optional]
     */
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
    private String schemeLocal;

    /**
     * The local co-branded card schemes.
     * [Optional]
     */
    private List<String> localSchemes;

    /**
     * The card type.
     * [Optional]
     */
    private CardType cardType;

    /**
     * The card category.
     * [Optional]
     */
    private CardCategoryType cardCategory;

    /**
     * The card wallet type.
     * [Optional]
     */
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
    private String issuerCountry;

    /**
     * The issuer/card scheme product identifier.
     * [Optional]
     */
    private String productId;

    /**
     * The issuer/card scheme product type.
     * [Optional]
     */
    private String productType;

    /**
     * The Address Verification System check result.
     * [Optional]
     */
    private String avsCheck;

    /**
     * The card verification value (CVV) check result.
     * [Optional]
     */
    private String cvvCheck;

    /**
     * A unique reference to the underlying card for network tokens (e.g., Apple Pay, Google Pay).
     * [Optional]
     */
    private String paymentAccountReference;

    /**
     * The JWE encrypted full card number that has been updated by real-time account updater.
     * [Optional]
     */
    private String encryptedCardNumber;

    /**
     * Specifies what card information was updated by the Real-Time Account Updater.
     * [Optional]
     */
    private AccountUpdateStatusType accountUpdateStatus;

    /**
     * Provides the failure code if the Real-Time Account Updater fails to update the card information.
     * [Optional]
     */
    private String accountUpdateFailureCode;

    /**
     * Information about the account holder of the card.
     * [Optional]
     */
    private AbstractAccountHolder accountHolder;
}
