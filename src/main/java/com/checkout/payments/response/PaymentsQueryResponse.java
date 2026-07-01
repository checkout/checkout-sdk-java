package com.checkout.payments.response;

import com.checkout.HttpMetadata;
import lombok.Data;
import lombok.EqualsAndHashCode;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public final class PaymentsQueryResponse extends HttpMetadata {

    @Size(min = 1, max = 100)
    private Integer limit;

    private Integer skip;

    private Integer totalCount;

    private List<GetPaymentResponse> data;
}
