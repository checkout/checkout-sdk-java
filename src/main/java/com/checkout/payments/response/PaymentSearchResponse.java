package com.checkout.payments.response;

import com.checkout.common.Resource;

import lombok.Data;
import lombok.Builder;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@Builder
@EqualsAndHashCode(callSuper = true)
public final class PaymentSearchResponse extends Resource {

    /**
     * Array of payment objects matching the search query.
     * Can contain Payin, BankPayout, or CardPayout responses.
     */
    private List<GetPaymentResponse> data;
}