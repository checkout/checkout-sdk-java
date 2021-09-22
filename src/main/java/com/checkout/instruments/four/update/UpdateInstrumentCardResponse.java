package com.checkout.instruments.four.update;

import com.checkout.common.four.InstrumentType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public final class UpdateInstrumentCardResponse extends UpdateInstrumentResponse {

    private final InstrumentType type = InstrumentType.CARD;

}
