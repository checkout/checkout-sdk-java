package com.checkout.payments.response;

import com.checkout.HttpMetadata;
import com.google.gson.annotations.SerializedName;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.util.List;

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
