package com.checkout.sources.previous;

import com.checkout.common.Address;
import com.checkout.common.CustomerRequest;
import com.checkout.common.Phone;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public final class SepaSourceRequest extends SourceRequest {

    private Address billingAddress;

    private SourceData sourceData;

    @Builder
    private SepaSourceRequest(final String reference,
                             final Phone phone,
                             final CustomerRequest customer,
                             final Address billingAddress,
                             final SourceData sourceData) {
        super(SourceType.SEPA, reference, phone, customer);
        this.billingAddress = billingAddress;
        this.sourceData = sourceData;
    }

}
