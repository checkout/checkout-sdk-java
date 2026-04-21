package com.checkout.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class Product {

    /**
     * The name of the product.
     * [Optional]
     */
    private String name;

    /**
     * The quantity of the product.
     * [Optional]
     */
    private Long quantity;

    /**
     * The price of the product in minor currency units.
     * [Optional]
     */
    private Long price;

    /**
     * A reference you can use to identify the product.
     * [Optional]
     */
    private String reference;

    /**
     * The URL of the product.
     * [Optional]
     */
    private String url;
}
