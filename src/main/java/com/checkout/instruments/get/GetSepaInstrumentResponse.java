package com.checkout.instruments.get;

import com.checkout.common.InstrumentType;
import com.checkout.instruments.create.InstrumentData;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.time.Instant;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public final class GetSepaInstrumentResponse extends GetInstrumentResponse {

    private final InstrumentType type = InstrumentType.SEPA;
    
    private Instant createdOn;
    
    private Instant modifiedOn;
    
    private String vaultId;
    
    private InstrumentData instrumentData;

}
