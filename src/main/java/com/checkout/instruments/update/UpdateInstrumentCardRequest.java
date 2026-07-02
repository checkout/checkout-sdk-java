package com.checkout.instruments.update;

import com.checkout.common.AccountHolder;
import com.checkout.common.InstrumentType;
import com.checkout.common.UpdateCustomerRequest;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public final class UpdateInstrumentCardRequest extends UpdateInstrumentRequest {

    private Integer expiryMonth;

    private Integer expiryYear;

    private String name;

    private AccountHolder accountHolder;

    private UpdateCustomerRequest customer;

    @Builder
    private UpdateInstrumentCardRequest(final Integer expiryMonth,
                                        final Integer expiryYear,
                                        final String name,
                                        final AccountHolder accountHolder,
                                        final UpdateCustomerRequest customer) {
        super(InstrumentType.CARD);
        this.expiryMonth = expiryMonth;
        this.expiryYear = expiryYear;
        this.name = name;
        this.accountHolder = accountHolder;
        this.customer = customer;
    }

    public UpdateInstrumentCardRequest(final InstrumentType type) {
        super(InstrumentType.CARD);
    }

}
