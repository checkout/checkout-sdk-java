package com.checkout.payments.four.sender;

import com.checkout.common.CheckoutUtils;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.apache.commons.lang3.EnumUtils;

import java.util.HashMap;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public final class ResponseAlternativeSender extends HashMap<String, Object> implements Sender {

    public SenderType getType() {
        return EnumUtils.getEnumIgnoreCase(SenderType.class, (String) get(CheckoutUtils.TYPE));
    }

}
