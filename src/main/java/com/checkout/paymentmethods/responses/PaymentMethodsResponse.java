package com.checkout.paymentmethods.responses;

import com.checkout.common.Resource;
import com.checkout.paymentmethods.entities.PaymentMethod;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public final class PaymentMethodsResponse extends Resource {

    /**
     * The enabled payment methods for the processing channel.
     */
    private List<PaymentMethod> methods;
}