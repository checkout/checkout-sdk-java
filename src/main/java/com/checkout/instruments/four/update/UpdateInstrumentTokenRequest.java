package com.checkout.instruments.four.update;

import com.checkout.common.four.InstrumentType;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public final class UpdateInstrumentTokenRequest extends UpdateInstrumentRequest {

    private final String token;

    @Builder
    protected UpdateInstrumentTokenRequest(final String token) {
        super(InstrumentType.TOKEN);
        this.token = token;
    }

}
