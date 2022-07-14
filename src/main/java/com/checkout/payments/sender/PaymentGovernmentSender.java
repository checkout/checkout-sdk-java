package com.checkout.payments.sender;

import com.checkout.common.AccountHolderIdentification;
import com.checkout.common.Address;
import com.google.gson.annotations.SerializedName;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public final class PaymentGovernmentSender extends PaymentSender {

    @SerializedName("company_name")
    private String companyName;

    private Address address;

    @SerializedName("reference_type")
    private String referenceType;

    @SerializedName("source_of_funds")
    private SourceOfFunds sourceOfFunds;

    private AccountHolderIdentification identification;

    @Builder
    private PaymentGovernmentSender(final String reference,
                                    final String companyName,
                                    final Address address,
                                    final String referenceType,
                                    final SourceOfFunds sourceOfFunds,
                                    final AccountHolderIdentification identification) {
        super(SenderType.GOVERNMENT, reference);
        this.companyName = companyName;
        this.address = address;
        this.referenceType = referenceType;
        this.sourceOfFunds = sourceOfFunds;
        this.identification = identification;
    }

    public PaymentGovernmentSender() {
        super(SenderType.GOVERNMENT);
    }

}
