package com.checkout.payments.hosted;

import com.checkout.common.Resource;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public final class HostedPaymentResponse extends Resource {

    private String id;

    private String reference;

    private List<Object> warnings;

}
