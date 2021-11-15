package com.checkout.payments.request.source.apm;

import com.checkout.common.PaymentSourceType;
import com.checkout.payments.request.source.AbstractRequestSource;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public final class RequestIdealSource extends AbstractRequestSource {

    private String bic;

    private String description;

    private String language;

    @Builder
    private RequestIdealSource(final String bic,
                               final String description,
                               final String language) {
        super(PaymentSourceType.IDEAL);
        this.bic = bic;
        this.description = description;
        this.language = language;
    }

    public RequestIdealSource() {
        super(PaymentSourceType.IDEAL);
    }

}
