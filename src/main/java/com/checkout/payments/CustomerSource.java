package com.checkout.payments;

import com.checkout.CheckoutArgumentException;
import com.checkout.common.PaymentSourceType;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

@Data
public class CustomerSource implements RequestSource {

    private final PaymentSourceType type = PaymentSourceType.CUSTOMER;

    private final String id;

    private final String email;

    public CustomerSource(final String id, final String email) {
        if (StringUtils.isBlank(id) && StringUtils.isBlank(email)) {
            throw new CheckoutArgumentException("either the customer id or email is required");
        }
        this.email = email;
        this.id = id;
    }

}
