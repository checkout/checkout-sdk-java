package com.checkout.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
@Builder
@AllArgsConstructor
public final class Product {
    @NotEmpty
    private String name;
    @NotEmpty
    private Long quantity;
    @NotEmpty
    private Long price;
}
