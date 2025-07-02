package com.checkout.handlepaymentsandpayouts.flow.paymentsessions.requests;

import com.checkout.handlepaymentsandpayouts.flow.paymentsessions.enums.StorePaymentDetailsType;
import com.checkout.handlepaymentsandpayouts.flow.paymentsessions.requests.accountholder.AbstractAccountHolder;
import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class Applepay {

    /**
     * Default: "disabled"
     * Specifies whether you intend to store the cardholder's payment details. If you set this fieldto enabled, you
     * must: have obtained consent from the cardholder to store their payment details provide a customer ID or email
     * address in the request If the request's payment_type is set to one of the following values, you do not need to
     * provide this field: Installment Recurring Unscheduled
     */
    @Builder.Default
    @SerializedName("store_payment_details")
    private StorePaymentDetailsType storePaymentDetails = StorePaymentDetailsType.DISABLED;

    /**
     * The account holder's details.
     */
    @SerializedName("account_holder")
    private AbstractAccountHolder accountHolder;

}
