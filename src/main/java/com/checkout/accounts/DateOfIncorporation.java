package com.checkout.accounts;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public final class DateOfIncorporation {

    private Integer month;

    private Integer year;

}
