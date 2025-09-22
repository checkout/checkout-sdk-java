package com.checkout.handlepaymentsandpayouts.payments.common.source.ptwofoursource;

import com.checkout.handlepaymentsandpayouts.payments.common.source.AbstractSource;
import com.checkout.handlepaymentsandpayouts.payments.common.source.SourceType;
import com.google.gson.annotations.SerializedName;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * p24 source Class
 * The source of the payment
 */
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public final class PTwoFourSource extends AbstractSource {

    /**
     * P24-generated payment descriptor, which contains the requested billing descriptor or the merchant's default
     * descriptor (subject to truncation).
     * [Optional]
     */
    @SerializedName("p24_descriptor")
    private String pTwoFourDescriptor;

    /**
     * Initializes a new instance of the PTwoFourSource class.
     */
    @Builder
    private PTwoFourSource(
            final String pTwoFourDescriptor
    ) {
        super(SourceType.P24);
        this.pTwoFourDescriptor = pTwoFourDescriptor;
    }

    public PTwoFourSource() {
        super(SourceType.P24);
    }

}
