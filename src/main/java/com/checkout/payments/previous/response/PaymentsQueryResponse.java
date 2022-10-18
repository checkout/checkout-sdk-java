package com.checkout.payments.previous.response;

import com.checkout.HttpMetadata;
import com.google.gson.annotations.SerializedName;
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

    @SerializedName("total_count")
    private Integer totalCount;

    private List<GetPaymentResponse> data;
}
