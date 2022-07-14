package com.checkout.instruments.update;

import com.checkout.common.InstrumentType;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public final class UpdateInstrumentTokenRequest extends UpdateInstrumentRequest {

    private String token;

    @Builder
    private UpdateInstrumentTokenRequest(final String token) {
        super(InstrumentType.TOKEN);
        this.token = token;
    }

    public UpdateInstrumentTokenRequest() {
        super(InstrumentType.TOKEN);
    }

}
