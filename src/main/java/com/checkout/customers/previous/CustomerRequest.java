package com.checkout.customers.previous;

import com.checkout.common.Phone;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Data;

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
