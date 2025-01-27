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

    private String token;

    @SerializedName("expiry_month")
    private Integer expiryMonth;

    @SerializedName("expiry_year")
    private Integer expiryYear;

    @SerializedName("token_type")
    private NetworkTokenType tokenType;

    private String cryptogram;

    private String eci;

    private Boolean stored;

    @SerializedName("store_for_future_use")
    private Boolean storeForFutureUse;

    private String name;

    private String cvv;

    @SerializedName("billing_address")
    private Address billingAddress;

    private Phone phone;

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
