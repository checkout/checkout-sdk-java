package com.checkout.payments.request.source;

import com.checkout.common.AccountHolder;
import com.checkout.common.Address;
import com.checkout.common.Phone;
import com.checkout.payments.NetworkTokenType;
import com.google.gson.annotations.SerializedName;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import static com.checkout.common.PaymentSourceType.NETWORK_TOKEN;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public final class RequestNetworkTokenSource extends AbstractRequestSource {

    /**
     * The network token value.
     * [Optional]
     */
    private String token;

    /**
     * The expiry month of the token.
     * [Optional]
     */
    @SerializedName("expiry_month")
    private Integer expiryMonth;

    /**
     * The expiry year of the token.
     * [Optional]
     */
    @SerializedName("expiry_year")
    private Integer expiryYear;

    /**
     * The network token type.
     * [Optional]
     */
    @SerializedName("token_type")
    private NetworkTokenType tokenType;

    /**
     * The cryptogram for the network token (device pan). Required for Visa and Mastercard DPAN payments.
     * [Optional]
     */
    private String cryptogram;

    /**
     * The Electronic Commerce Indicator (ECI) security level.
     * [Optional]
     */
    private String eci;

    /**
     * Set to true for payments that use stored card details. Write-only.
     * [Optional]
     */
    private Boolean stored;

    /**
     * Set to true if you intend to reuse the payment credentials in subsequent payments. Write-only.
     * [Optional]
     */
    @SerializedName("store_for_future_use")
    private Boolean storeForFutureUse;

    /**
     * The cardholder's name. Write-only.
     * [Optional]
     */
    private String name;

    /**
     * The card verification value/code. 3 digits, except for American Express (4 digits). Write-only.
     * [Optional]
     */
    private String cvv;

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
     * The card account holder's details.
     * [Optional]
     */
    @SerializedName("account_holder")
    private AccountHolder accountHolder;

    @Builder
    private RequestNetworkTokenSource(final String token,
                                      final Integer expiryMonth,
                                      final Integer expiryYear,
                                      final NetworkTokenType tokenType,
                                      final String cryptogram,
                                      final String eci,
                                      final Boolean stored,
                                      final Boolean storeForFutureUse,
                                      final String name,
                                      final String cvv,
                                      final Address billingAddress,
                                      final Phone phone,
                                      final AccountHolder accountHolder) {
        super(NETWORK_TOKEN);
        this.token = token;
        this.expiryMonth = expiryMonth;
        this.expiryYear = expiryYear;
        this.tokenType = tokenType;
        this.cryptogram = cryptogram;
        this.eci = eci;
        this.stored = stored;
        this.storeForFutureUse = storeForFutureUse;
        this.name = name;
        this.cvv = cvv;
        this.billingAddress = billingAddress;
        this.phone = phone;
        this.accountHolder = accountHolder;
    }

    public RequestNetworkTokenSource() {
        super(NETWORK_TOKEN);
    }

}
