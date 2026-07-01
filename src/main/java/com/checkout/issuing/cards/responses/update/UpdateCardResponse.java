package com.checkout.issuing.cards.responses.update;

import com.checkout.common.Resource;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.time.Instant;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public final class UpdateCardResponse extends Resource {

    private Instant lastModifiedDate;
}