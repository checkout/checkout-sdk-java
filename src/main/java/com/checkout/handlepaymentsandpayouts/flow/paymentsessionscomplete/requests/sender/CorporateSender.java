package com.checkout.handlepaymentsandpayouts.flow.paymentsessionscomplete.requests.sender;

import com.checkout.handlepaymentsandpayouts.flow.paymentsessionscomplete.enums.SenderType;
import com.checkout.handlepaymentsandpayouts.flow.paymentsessionscomplete.requests.Address;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public final class CorporateSender extends AbstractSender {

    /**
     * The corporate sender's company name.
     */
    private String companyName;
    /**
     * The sender's registered corporate address.
     */
    private Address address;
    /**
     * The sender's reference for the payout
     */
    private String reference;

    @Builder
    private CorporateSender(
            final String companyName,
            final Address address,
            final String reference
    ) {
        super(SenderType.CORPORATE);
        this.companyName = companyName;
        this.address = address;
        this.reference = reference;
    }

    public CorporateSender() {
        super(SenderType.CORPORATE);
    }

}
