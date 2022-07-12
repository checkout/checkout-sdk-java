package com.checkout.transfers;

import com.checkout.common.Resource;
import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.time.Instant;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public final class TransferDetailsResponse extends Resource {

    private String id;

    private String reference;

    private TransferStatus status;

    @SerializedName("transfer_type")
    private TransferType transferType;

    @SerializedName("requested_on")
    private Instant requestedOn;

    @SerializedName("reason_codes")
    private List<String> reasonCodes;

    private TransferSourceResponse source;

    private TransferDestinationResponse destination;

}
