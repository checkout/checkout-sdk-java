package com.checkout.payments.sender;

import com.checkout.common.AccountHolderIdentification;
import com.checkout.common.Address;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public final class PaymentCorporateSender extends PaymentSender {

    private String companyName;

    private Address address;

    private String referenceType;

    private SourceOfFunds sourceOfFunds;

    private AccountHolderIdentification identification;

    @Builder
    private PaymentCorporateSender(final String reference,
                                  final String companyName,
                                  final Address address,
                                  final String referenceType,
                                  final SourceOfFunds sourceOfFunds,
                                  final AccountHolderIdentification identification) {
        super(SenderType.CORPORATE, reference);
        this.companyName = companyName;
        this.address = address;
        this.referenceType = referenceType;
        this.sourceOfFunds = sourceOfFunds;
        this.identification = identification;
    }

    public PaymentCorporateSender() {
        super(SenderType.CORPORATE);
    }

}
