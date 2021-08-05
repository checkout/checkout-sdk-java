package com.checkout.customers;

import com.checkout.common.Phone;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.util.Map;

@Data
@Builder
public final class CustomerRequest {
    @NotEmpty
    private String email;
    private String name;
    private Phone phone;
    private Map<String, Object> metadata;
}
