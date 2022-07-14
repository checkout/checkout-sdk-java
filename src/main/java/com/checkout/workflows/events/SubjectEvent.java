package com.checkout.workflows.events;

import com.checkout.common.Resource;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public final class SubjectEvent extends Resource {

    private String id;

    private String type;

    private String timestamp;

}
