package com.checkout.marketplace.transfers;

import com.checkout.HttpMetadata;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public final class CreateTransferResponse extends HttpMetadata {

    private String id;

    private String status;

}
