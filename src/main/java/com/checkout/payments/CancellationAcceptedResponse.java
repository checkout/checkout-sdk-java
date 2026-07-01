package com.checkout.payments;

import com.checkout.common.Resource;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * Response returned when a payment cancellation is accepted.
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public final class CancellationAcceptedResponse extends Resource {

    private String actionId;

    private String reference;

}
