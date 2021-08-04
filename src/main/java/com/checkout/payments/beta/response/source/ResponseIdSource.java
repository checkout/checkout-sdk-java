package com.checkout.payments.beta.response.source;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public final class ResponseIdSource extends ResponseSource {

    private final String id;

}
