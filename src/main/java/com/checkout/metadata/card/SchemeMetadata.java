package com.checkout.metadata.card;

import lombok.Data;

import java.util.List;

@Data
public final class SchemeMetadata {

    private List<PinlessDebitSchemeMetadata> accel;

    private List<PinlessDebitSchemeMetadata> pulse;

    private List<PinlessDebitSchemeMetadata> nyce;

    private List<PinlessDebitSchemeMetadata> star;

}
