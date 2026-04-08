package com.checkout.agenticcommerce.request;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

/**
 * The card details for a delegated payment.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DelegatePaymentMethod {

    /**
     * Payment method type.
     * <p>
     * [Required]
     * <p>
     * Enum: "card"
     */
    private String type;

    /**
     * Whether the number is a full PAN or a network token.
     * <p>
     * [Required]
     */
    @SerializedName("card_number_type")
    private CardNumberType cardNumberType;

    /**
     * Card or token number.
     * <p>
     * [Required]
     */
    private String number;

    /**
     * Expiry month (two digits).
     * <p>
     * [Optional]
     * <p>
     * Length: &gt;= 2 characters
     * <p>
     * Length: &lt;= 2 characters
     */
    @SerializedName("exp_month")
    private String expMonth;

    /**
     * Expiry year (four digits).
     * <p>
     * [Optional]
     * <p>
     * Length: &gt;= 4 characters
     * <p>
     * Length: &lt;= 4 characters
     */
    @SerializedName("exp_year")
    private String expYear;

    /**
     * Cardholder name as shown on the card.
     * <p>
     * [Optional]
     */
    private String name;

    /**
     * Card verification value (CVC/CVV).
     * <p>
     * [Optional]
     * <p>
     * Length: &gt;= 3 characters
     * <p>
     * Length: &lt;= 4 characters
     */
    private String cvc;

    /**
     * Cryptogram for network token transactions (required when {@code card_number_type} is network token).
     * <p>
     * [Optional]
     */
    private String cryptogram;

    /**
     * Electronic Commerce Indicator (ECI) or Security Level Indicator (SLI) for network token flows.
     * <p>
     * [Optional]
     */
    @SerializedName("eci_value")
    private String eciValue;

    /**
     * Verification checks already performed on the card (array of strings).
     * <p>
     * [Optional]
     */
    @SerializedName("checks_performed")
    private List<String> checksPerformed;

    /**
     * Issuer Identification Number (IIN/BIN), typically the first six digits of the PAN.
     * <p>
     * [Optional]
     * <p>
     * Length: &gt;= 6 characters
     * <p>
     * Length: &lt;= 6 characters
     */
    private String iin;

    /**
     * Card funding type for display (credit, debit, or prepaid).
     * <p>
     * [Optional]
     */
    @SerializedName("display_card_funding_type")
    private CardFundingType displayCardFundingType;

    /**
     * Wallet type for display (e.g. Apple Pay, Google Pay).
     * <p>
     * [Optional]
     */
    @SerializedName("display_wallet_type")
    private String displayWalletType;

    /**
     * Card brand for display (e.g. Visa, Mastercard).
     * <p>
     * [Optional]
     */
    @SerializedName("display_brand")
    private String displayBrand;

    /**
     * Last four digits of the card for display.
     * <p>
     * [Optional]
     * <p>
     * Length: &gt;= 4 characters
     * <p>
     * Length: &lt;= 4 characters
     */
    @SerializedName("display_last4")
    private String displayLast4;

    /**
     * Additional payment method metadata (string values only).
     * <p>
     * [Required]
     */
    private Map<String, Object> metadata;
}
