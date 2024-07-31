package com.checkout.payments.response.source.contexts;

import com.checkout.accounts.payout.schedule.ScheduleFrequency;
import com.checkout.common.AccountHolder;
import com.checkout.common.PaymentSourceType;
import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.ToString;

@Data
@ToString(callSuper = true)
public abstract class AbstractPaymentContextsResponseSource {

    private final PaymentSourceType type;

    @SerializedName("account_holder")
    protected AccountHolder accountHolder;

    protected AbstractPaymentContextsResponseSource(final PaymentSourceType type) {
        this.type = type;
    }

}
