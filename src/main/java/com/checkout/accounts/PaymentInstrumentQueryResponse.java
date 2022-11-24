package com.checkout.accounts;

import com.checkout.common.Resource;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.List;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class PaymentInstrumentQueryResponse extends Resource {

    private List<PaymentInstrumentDetailsResponse> data;
}
