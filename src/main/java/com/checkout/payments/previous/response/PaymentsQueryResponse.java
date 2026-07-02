package com.checkout.payments.previous.response;

import com.checkout.HttpMetadata;
import javax.validation.constraints.Size;
import java.util.List;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public final class PaymentsQueryResponse extends HttpMetadata {

    @Size(min = 1, max = 100)
    private Integer limit;

    private Integer skip;

    private Integer totalCount;

    private List<GetPaymentResponse> data;
}
