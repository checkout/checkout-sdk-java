package com.checkout.issuing.cardholders;

import com.checkout.common.Resource;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.time.Instant;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public final class CardholderUpdateResponse extends Resource {

    private Instant lastModifiedDate;
}
