package com.checkout.issuing.cards.responses.revocation;

import com.checkout.common.Resource;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ScheduleRevocationResponse extends Resource {
    // Response contains only _links field which is inherited from Resource
}