package com.checkout.handlepaymentsandpayouts.payments.common.source.multibancosource;

import com.checkout.handlepaymentsandpayouts.payments.common.source.AbstractSource;
import com.checkout.handlepaymentsandpayouts.payments.common.source.SourceType;
import com.google.gson.annotations.SerializedName;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * multibanco source Class
 * The source of the payment
 */
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public final class MultibancoSource extends AbstractSource {

    /**
     * Multibanco payment reference
     * [Optional]
     */
    @SerializedName("payment_reference")
    private String paymentReference;

    /**
     * The identifier of a supplier charging for its service or product
     * [Optional]
     */
    @SerializedName("service_supplier_id")
    private String serviceSupplierId;

    /**
     * Initializes a new instance of the MultibancoSource class.
     */
    @Builder
    private MultibancoSource(
            final String paymentReference,
            final String serviceSupplierId
    ) {
        super(SourceType.MULTIBANCO);
        this.paymentReference = paymentReference;
        this.serviceSupplierId = serviceSupplierId;
    }

    public MultibancoSource() {
        super(SourceType.MULTIBANCO);
    }

}
