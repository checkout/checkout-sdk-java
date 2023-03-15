package com.checkout.financial;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Size;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FinancialActionsQueryFilter {

    @SerializedName("payment_id")
    private String paymentId;

    @SerializedName("action_id")
    private String actionId;

    @Size(min = 1, max = 100)
    private Integer limit;

    @SerializedName("pagination_token")
    private String paginationToken;
}
