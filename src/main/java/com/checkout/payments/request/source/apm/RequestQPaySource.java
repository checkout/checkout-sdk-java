package com.checkout.payments.request.source.apm;

import com.checkout.common.PaymentSourceType;
import com.checkout.payments.request.source.AbstractRequestSource;
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
public final class RequestQPaySource extends AbstractRequestSource {

    private Integer quantity;

    private String description;

    private String language;

    @SerializedName("national_id")
    private String nationalId;

    @Builder
    private RequestQPaySource(final Integer quantity,
                             final String description,
                             final String language,
                             final String nationalId) {
        super(PaymentSourceType.QPAY);
        this.quantity = quantity;
        this.description = description;
        this.language = language;
        this.nationalId = nationalId;
    }

    public RequestQPaySource() {
        super(PaymentSourceType.QPAY);
    }

}
