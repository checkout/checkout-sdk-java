package com.checkout.sources.previous;

import com.checkout.common.CustomerRequest;
import com.checkout.common.Phone;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public abstract class SourceRequest {

    private SourceType type;

    private String reference;

    private Phone phone;

    private CustomerRequest customer;

}
