package com.checkout.issuing.cards;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class IssuingDevice {

    private String id;

    private String type;

}
