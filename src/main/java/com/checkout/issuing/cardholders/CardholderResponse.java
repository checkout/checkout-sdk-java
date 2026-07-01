package com.checkout.issuing.cardholders;

import com.checkout.common.Resource;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.time.Instant;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public final class CardholderResponse extends Resource {

    private String id;

    private CardholderType type;

    private CardholderStatus status;

    private String reference;

    private Instant createdDate;

    private Instant lastModifiedDate;
}
