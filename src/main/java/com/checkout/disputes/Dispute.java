package com.checkout.disputes;

import com.checkout.common.Resource;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@Builder
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper=false)
public final class Dispute extends Resource {

    private String id;
    private DisputeCategory category;
    private DisputeStatus status;
    private Number amount;
    private String currency;
    private String reasonCode;
    private String paymentId;
    private String paymentActionId;
    private String paymentReference;
    private String paymentArn;
    private String paymentMethod;
    private String evidenceRequiredBy;
    private String receivedOn;
    private String lastUpdate;

}
