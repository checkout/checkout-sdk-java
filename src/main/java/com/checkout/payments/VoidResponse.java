package com.checkout.payments;

import com.checkout.common.Resource;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class VoidResponse extends Resource {
    private String actionId;
    private String reference;
}