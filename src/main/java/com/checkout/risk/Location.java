package com.checkout.risk;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public final class Location {

    private String latitude;

    private String longitude;

}
