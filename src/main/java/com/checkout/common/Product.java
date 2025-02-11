package com.checkout.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public final class Product {

    private String name;

    private Long quantity;

    private Long price;

    private String reference;
}
