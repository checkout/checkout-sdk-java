package com.checkout.transfers;

import com.checkout.common.Resource;
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

    private TransferType transferType;

    private Instant requestedOn;

    private List<String> reasonCodes;

    private TransferSourceResponse source;

    private TransferDestinationResponse destination;

}
