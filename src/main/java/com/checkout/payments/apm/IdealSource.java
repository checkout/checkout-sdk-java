package com.checkout.payments.apm;

import com.checkout.common.PaymentSourceType;
import com.checkout.payments.RequestSource;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public final class IdealSource extends com.checkout.payments.four.request.source.RequestSource implements RequestSource {

    private String bic;

    private String description;

    private String language;

    @Builder
    private IdealSource(final String bic,
                        final String description,
                        final String language) {
        super(PaymentSourceType.IDEAL);
        this.bic = bic;
        this.description = description;
        this.language = language;
    }

    public IdealSource() {
        super(PaymentSourceType.IDEAL);
    }

}
