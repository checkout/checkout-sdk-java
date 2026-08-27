package com.checkout.payments.previous.request.source.apm;

import com.checkout.common.PaymentSourceType;
import com.checkout.payments.previous.request.source.AbstractRequestSource;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * A payment against a stored SEPA mandate on the previous platform.
 *
 * <p>The previous platform references the stored mandate through the generic "id" source, so the
 * type on the wire is "id" and not "sepa". Use {@link com.checkout.payments.request.source.apm.RequestSepaSource}
 * for the current platform, where the type is "sepa".
 */
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public final class RequestSepaSource extends AbstractRequestSource {

    /**
     * The ID of the stored SEPA mandate.
     * [Required]
     */
    private String id;

    @Builder
    private RequestSepaSource(final String id) {
        super(PaymentSourceType.ID);
        this.id = id;
    }

    public RequestSepaSource() {
        super(PaymentSourceType.ID);
    }

}
