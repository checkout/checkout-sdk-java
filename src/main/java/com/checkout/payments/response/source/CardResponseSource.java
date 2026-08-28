package com.checkout.payments.response.source;

import com.checkout.common.AccountHolderResponse;
import com.checkout.common.Address;
import com.checkout.common.CardCategory;
import com.checkout.common.CardType;
import com.checkout.common.CardWalletType;
import com.checkout.common.CountryCode;
import com.checkout.common.Phone;
import com.checkout.payments.AccountUpdateStatusType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.List;

/**
 * A card payment source.
 *
 * <p>The type and the id are inherited from {@link AbstractResponseSource}.
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public final class CardResponseSource extends AbstractResponseSource implements ResponseSource {

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
     * The expiry month.
     * [Required]
     * min 1 characters
     * max 2 characters
     * min 1
     *
     * <p>The specification types this as an integer, but the API masks the value with asterisks on
     * some responses, so it is exposed as a string to keep deserialization working.
     */
    private String expiryMonth;

    /**
     * The expiry year.
     * [Required]
     * min 4 characters
     * max 4 characters
     *
     * <p>The specification types this as an integer, but the API masks the value with asterisks on
     * some responses, so it is exposed as a string to keep deserialization working.
     */
    private String expiryYear;

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
     * The local co-branded card scheme.
     * [Optional]
     *
     * @deprecated replaced by {@link CardResponseSource#localSchemes}. This property will be
     * removed in a future version.
     */
    @Deprecated
    private String schemeLocal;

    /**
     * The local co-branded card schemes.
     * [Optional]
     */
    private List<String> localSchemes;

    /**
     * The last four digits of the card number.
     * [Required]
     */
    private String last4;

    /**
     * Uniquely identifies this particular card number. You can use this to compare cards across
     * customers.
     * [Required]
     */
    private String fingerprint;

    /**
     * The card issuer's Bank Identification Number (BIN).
     * [Required]
     * max 8 characters
     */
    private String bin;

    /**
     * The card type.
     * [Optional]
     * Enum: "CREDIT" "DEBIT" "PREPAID" "CHARGE" "DEFERRED DEBIT"
     */
    private CardType cardType;

    /**
     * The card category.
     * [Optional]
     * Enum: "CONSUMER" "COMMERCIAL"
     */
    private CardCategory cardCategory;

    /**
     * The card wallet type.
     * [Optional]
     * Enum: "applepay" "googlepay"
     */
    private CardWalletType cardWalletType;

    /**
     * The name of the card issuer.
     * [Optional]
     */
    private String issuer;

    /**
     * The card issuer's country, as a two-letter ISO code.
     * [Optional]
     * min 2 characters
     * max 2 characters
     */
    private CountryCode issuerCountry;

    /**
     * The issuer or card scheme product identifier.
     * [Optional]
     */
    private String productId;

    /**
     * The issuer or card scheme product type.
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
     * A unique reference to the underlying card for network tokens, such as Apple Pay or Google
     * Pay.
     * [Optional]
     */
    private String paymentAccountReference;

    /**
     * The JWE encrypted full card number that has been updated by the real-time account updater.
     * [Optional]
     */
    private String encryptedCardNumber;

    /**
     * Specifies what card information was updated by the real-time account updater.
     * [Optional]
     * Enum: "card_updated" "card_expiry_updated" "card_closed" "contact_cardholder"
     */
    private AccountUpdateStatusType accountUpdateStatus;

    /**
     * Provides the failure code if the real-time account update failed.
     * [Optional]
     */
    private String accountUpdateFailureCode;

    /**
     * Information about the account holder of the card.
     * [Optional]
     */
    private AccountHolderResponse accountHolder;

}
