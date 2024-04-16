package com.checkout.instruments.get;

import com.checkout.common.InstrumentType;
import com.checkout.instruments.create.InstrumentData;
import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.time.Instant;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public final class GetSepaInstrumentResponse extends GetInstrumentResponse {

    private final InstrumentType type = InstrumentType.SEPA;
    
    @SerializedName("created_on")
    private Instant createdOn;
    
    @SerializedName("modified_on")
    private Instant modifiedOn;
    
    @SerializedName("vault_id")
    private String vaultId;
    
    @SerializedName("instrument_data")
    private InstrumentData instrumentData;

}
