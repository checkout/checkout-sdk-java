package com.checkout.payments;

import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class PaymentsQueryFilter {

    @Size(min = 1, max = 100)
    private Integer limit;

    private Integer skip;

    private String reference;
}
