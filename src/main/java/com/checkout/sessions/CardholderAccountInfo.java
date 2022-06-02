package com.checkout.sessions;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public final class CardholderAccountInfo {

    @SerializedName("purchase_count")
    private Long purchaseCount;

    @SerializedName("account_age")
    private String accountAge;

    @SerializedName("add_card_attempts")
    private Long addCardAttempts;

    @SerializedName("shipping_address_age")
    private String shippingAddressAge;

    @SerializedName("account_name_matches_shipping_name")
    private Boolean accountNameMatchesShippingName;

    @SerializedName("suspicious_account_activity")
    private Boolean suspiciousAccountActivity;

    @SerializedName("transactions_today")
    private Long transactionsToday;

    @SerializedName("authentication_method")
    private AuthenticationMethod authenticationMethod;

}
