package com.checkout.accounts;

import lombok.Data;

@Data
public final class Instrument {

    private String id;

    private String label;

    private InstrumentStatus status;

    private InstrumentDocument document;

}
