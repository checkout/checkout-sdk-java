package com.checkout.payments.four.request.source;

import com.checkout.common.Address;
import com.checkout.common.Phone;
import com.google.gson.annotations.SerializedName;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import static com.checkout.common.PaymentSourceType.NETWORK_TOKEN;

@Getter
@EqualsAndHashCode(callSuper = true)
public final class RequestNetworkTokenSource extends RequestSource {

    private final String token;

    @SerializedName("expiry_month")
    private final Integer expiryMonth;

    @SerializedName("expiry_year")
    private final Integer expiryYear;

    @SerializedName("token_type")
    private final NetworkTokenType tokenType;

    private final String cryptogram;

    private final String eci;

    private final boolean stored;

    private final String name;

    private final String cvv;

    @SerializedName("billing_address")
    private final Address billingAddress;

    private final Phone phone;

    @Builder
    private RequestNetworkTokenSource(final String token,
                                      final Integer expiryMonth,
                                      final Integer expiryYear,
                                      final NetworkTokenType tokenType,
                                      final String cryptogram,
                                      final String eci,
                                      final boolean stored,
                                      final String name,
                                      final String cvv,
                                      final Address billingAddress,
                                      final Phone phone) {
        super(NETWORK_TOKEN);
        this.token = token;
        this.expiryMonth = expiryMonth;
        this.expiryYear = expiryYear;
        this.tokenType = tokenType;
        this.cryptogram = cryptogram;
        this.eci = eci;
        this.stored = stored;
        this.name = name;
        this.cvv = cvv;
        this.billingAddress = billingAddress;
        this.phone = phone;
    }

}
