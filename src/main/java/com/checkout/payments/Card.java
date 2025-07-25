package com.checkout.payments;

import com.checkout.common.AccountHolder;
import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class Card {

    @SerializedName("account_holder")
    private AccountHolder accountHolder;
    
    @SerializedName("store_payment_details")
    private StorePaymentDetailsType storePaymentDetails;

}
