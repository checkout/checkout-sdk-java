package com.checkout.agenticcommerce.request;

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
    private String eciValue;

    /**
     * Verification checks already performed on the card (array of strings).
     * <p>
     * [Optional]
     */
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
    private CardFundingType displayCardFundingType;

    /**
     * Wallet type for display (e.g. Apple Pay, Google Pay).
     * <p>
     * [Optional]
     */
    private String displayWalletType;

    /**
     * Card brand for display (e.g. Visa, Mastercard).
     * <p>
     * [Optional]
     */
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
    private String displayLast4;

    /**
     * Additional payment method metadata (string values only).
     * <p>
     * [Required]
     */
    private Map<String, Object> metadata;
}
