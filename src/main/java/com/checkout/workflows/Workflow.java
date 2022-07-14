package com.checkout.workflows;

import com.checkout.common.Resource;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public final class Workflow extends Resource {

    private String id;

    private String name;

    private Boolean active;

}
