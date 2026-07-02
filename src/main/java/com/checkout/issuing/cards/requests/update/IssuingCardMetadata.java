package com.checkout.issuing.cards.requests.update;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class IssuingCardMetadata {

    private String udf1;

    private String udf2;

    private String udf3;

    private String udf4;

    private String udf5;
}