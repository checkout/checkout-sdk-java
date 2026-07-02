package com.checkout.payments;

import com.checkout.common.AccountHolder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class Applepay {

    private AccountHolder accountHolder;

    private StorePaymentDetailsType storePaymentDetails;

}