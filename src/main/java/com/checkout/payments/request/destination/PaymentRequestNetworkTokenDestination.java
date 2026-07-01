package com.checkout.payments.request.destination;

import com.checkout.common.AccountHolder;
import com.checkout.payments.NetworkTokenType;
import com.checkout.payments.PaymentDestinationType;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public final class PaymentRequestNetworkTokenDestination extends PaymentRequestDestination {

    private String token;

    private Integer expiryMonth;

    private Integer expiryYear;

    private NetworkTokenType tokenType;

    private String cryptogram;

    private String eci;

    private AccountHolder accountHolder;

    @Builder
    private PaymentRequestNetworkTokenDestination(final String token,
                                                  final Integer expiryMonth,
                                                  final Integer expiryYear,
                                                  final NetworkTokenType tokenType,
                                                  final String cryptogram,
                                                  final String eci,
                                                  final AccountHolder accountHolder) {
        super(PaymentDestinationType.NETWORK_TOKEN);
        this.token = token;
        this.expiryMonth = expiryMonth;
        this.expiryYear = expiryYear;
        this.tokenType = tokenType;
        this.cryptogram = cryptogram;
        this.eci = eci;
        this.accountHolder = accountHolder;
    }

    public PaymentRequestNetworkTokenDestination() {
        super(PaymentDestinationType.NETWORK_TOKEN);
    }

}
