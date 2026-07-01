package com.checkout.apm.previous.klarna;

import com.checkout.common.Resource;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public final class CreditSessionResponse extends Resource {

    private String sessionId;

    private String clientToken;

    private List<PaymentMethod> paymentMethodCategories;

}
