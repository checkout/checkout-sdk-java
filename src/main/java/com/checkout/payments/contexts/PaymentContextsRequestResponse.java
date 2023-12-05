package com.checkout.payments.contexts;

import com.checkout.common.Resource;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@NoArgsConstructor
public final class PaymentContextsRequestResponse extends Resource {

    private String id;

    private PaymentContextsPartnerMetadata partnerMetadata;
}
