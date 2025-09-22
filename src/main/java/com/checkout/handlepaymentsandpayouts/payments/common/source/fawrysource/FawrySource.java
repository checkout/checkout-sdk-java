package com.checkout.handlepaymentsandpayouts.payments.common.source.fawrysource;

import com.checkout.handlepaymentsandpayouts.payments.common.source.AbstractSource;
import com.checkout.handlepaymentsandpayouts.payments.common.source.SourceType;
import com.google.gson.annotations.SerializedName;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * fawry source Class
 * The source of the payment
 */
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public final class FawrySource extends AbstractSource {

    /**
     * Payment description
     * [Required]
     * &lt;= 65534
     */
    private String description;

    /**
     * The customer pays using this number at Fawry's outlets
     * [Optional]
     */
    @SerializedName("reference_number")
    private String referenceNumber;

    /**
     * Initializes a new instance of the FawrySource class.
     */
    @Builder
    private FawrySource(
            final String description,
            final String referenceNumber
    ) {
        super(SourceType.FAWRY);
        this.description = description;
        this.referenceNumber = referenceNumber;
    }

    public FawrySource() {
        super(SourceType.FAWRY);
    }

}
