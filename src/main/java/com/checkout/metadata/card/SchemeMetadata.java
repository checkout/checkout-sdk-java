package com.checkout.metadata.card;

import lombok.Data;

import java.util.List;

@Data
public final class SchemeMetadata {

    private PinlessDebitSchemeMetadata accel;

    private List<PinlessDebitSchemeMetadata> pulse;

    private PinlessDebitSchemeMetadata nyce;

    private PinlessDebitSchemeMetadata star;

}
