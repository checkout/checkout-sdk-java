package com.checkout.instruments.beta.update;

import com.checkout.common.beta.InstrumentType;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public final class UpdateInstrumentCardResponse extends UpdateInstrumentResponse {

    private final InstrumentType type = InstrumentType.CARD;

}
