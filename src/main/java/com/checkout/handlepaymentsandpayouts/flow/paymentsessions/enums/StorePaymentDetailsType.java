package com.checkout.handlepaymentsandpayouts.flow.paymentsessions.enums;

import com.google.gson.annotations.SerializedName;

/** Default: "disabled" Specifies whether you intend to store the cardholder's payment details. If you set this field to
 *  enabled, you must: have obtained consent from the cardholder to store their payment details provide a customer ID or
 *  email address in the request If the request's payment_type is set to one of the following values, you do not need to
 *  provide this field: Installment Recurring Unscheduled
 */
public enum StorePaymentDetailsType {
    @SerializedName("disabled")
    DISABLED,
    @SerializedName("enabled")
    ENABLED,
    @SerializedName("collect_consent")
    COLLECT_CONSENT
}
