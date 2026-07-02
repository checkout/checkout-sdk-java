package com.checkout.disputes;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Size;
import java.time.Instant;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public final class DisputesQueryFilter {

    @Size(min = 1, max = 250)
    private Integer limit;

    @Size
    private Integer skip;

    private Instant from;

    private Instant to;

    private String id;

    /**
     * One or more comma-separated statuses. This works like a logical OR operator
     */
    private String statuses;

    private String paymentId;

    private String paymentReference;

    private String paymentArn;

    private Boolean thisChannelOnly;

    /**
     * Not available on Previous
     */

    /**
     * One or more comma-separated client entities. This works like a logical OR operator
     */
    private String entityIds;

    /**
     * One or more comma-separated sub-entities. This works like a logical OR operator
     */
    private String subEntityIds;

    private String paymentMcc;

    private String processingChannelIds;

    private String SegmentIds;

}
