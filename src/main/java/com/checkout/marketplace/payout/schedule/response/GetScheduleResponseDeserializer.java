package com.checkout.marketplace.payout.schedule.response;

import com.checkout.common.Currency;
import com.checkout.common.Resource;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.Map;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public final class GetScheduleResponseDeserializer extends Resource {

    private Map<Currency, CurrencySchedule> currency;

}
