package com.checkout.instruments.previous;

import com.checkout.common.InstrumentType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class CreateInstrumentRequest {

    private InstrumentType type;

    private String token;

    private InstrumentAccountHolder accountHolder;

    private InstrumentCustomerRequest customer;

}
