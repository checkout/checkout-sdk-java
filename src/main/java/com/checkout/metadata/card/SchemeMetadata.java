package com.checkout.metadata.card;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public final class SchemeMetadata {

    private PinlessDebitSchemeMetadata accel;

    private PinlessDebitSchemeMetadata pulse;

    private PinlessDebitSchemeMetadata nyce;

    private PinlessDebitSchemeMetadata star;

}
