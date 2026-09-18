package com.checkout.inventory.response;

import com.checkout.HttpMetadata;
import com.checkout.inventory.InventoryMoney;
import com.checkout.inventory.InventoryProductCondition;
import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.Instant;
import java.util.List;

/**
 * Beta. The product knowledge for a single variant: the merchandising fields an AI agent needs
 * to present, compare and recommend a product, independent of stock and pricing operations.
 * Optional fields are omitted from the response entirely when not set.
 */
@Data
@EqualsAndHashCode(callSuper = true)
public final class InventoryProductKnowledge extends HttpMetadata {

    /**
     * The merchant-provided identifier for the variant.
     * [Required]
     */
    private String variantId;

    /**
     * The product's display title.
     * [Required]
     */
    private String title;

    /**
     * The product's display description.
     * [Required]
     */
    private String description;

    /**
     * The canonical URL for the product page.
     * [Required]
     */
    private String productUrl;

    /**
     * The URL of the primary product image.
     * [Required]
     */
    private String imageUrl;

    /**
     * Additional product image URLs, beyond {@code image_url}.
     * [Optional]
     */
    private List<String> additionalImageUrls;

    /**
     * The URL of a product video.
     * [Optional]
     */
    private String videoUrl;

    /**
     * The URL of a 3D model of the product.
     * [Optional]
     */
    @SerializedName("model_3d_url")
    private String model3dUrl;

    /**
     * The merchant's stock-keeping unit for the product.
     * [Optional]
     */
    private String sku;

    /**
     * The product's Global Trade Item Number (UPC, EAN, ISBN, or JAN).
     * [Optional]
     */
    private String gtin;

    /**
     * The product's Manufacturer Part Number.
     * [Optional]
     */
    private String mpn;

    /**
     * The product's brand name.
     * [Optional]
     */
    private String brand;

    /**
     * The merchant's category for the product.
     * [Optional]
     */
    private String category;

    /**
     * The product's regular price.
     * [Optional]
     */
    private InventoryMoney price;

    /**
     * The product's discounted price.
     * [Optional]
     */
    private InventoryMoney salePrice;

    /**
     * The date and time from which {@code sale_price} applies. Paired with {@code sale_price}.
     * [Optional]
     * Format: date-time (RFC 3339)
     */
    private Instant salePriceStartsAt;

    /**
     * The date and time after which {@code sale_price} no longer applies. Paired with
     * {@code sale_price}.
     * [Optional]
     * Format: date-time (RFC 3339)
     */
    private Instant salePriceEndsAt;

    /**
     * The identifier shared by all variants of the same product (for example, the same belt in
     * different sizes). When set, {@code color} and {@code size} are both required.
     * [Optional]
     */
    private String groupId;

    /**
     * A display title for the variant group.
     * [Optional]
     */
    private String groupTitle;

    /**
     * The variant's color. Required when {@code group_id} is set.
     * [Optional]
     */
    private String color;

    /**
     * The variant's size. Required when {@code group_id} is set.
     * [Optional]
     */
    private String size;

    /**
     * The sizing system that {@code size} is expressed in.
     * [Optional]
     */
    private String sizeSystem;

    /**
     * The target gender for the product.
     * [Optional]
     */
    private String gender;

    /**
     * The product's condition. Always present; defaults to {@code new} when not provided.
     * [Required]
     * Enum: "new" "used" "refurbished"
     */
    private InventoryProductCondition condition;

    /**
     * The product's primary material.
     * [Optional]
     */
    private String material;

    /**
     * The target age group for the product.
     * [Optional]
     */
    private String ageGroup;

    /**
     * The product's length. Provided together with {@code width}, {@code height} and
     * {@code dimension_unit}, or not at all.
     * [Optional]
     */
    private Double length;

    /**
     * The product's width. Provided together with {@code length}, {@code height} and
     * {@code dimension_unit}, or not at all.
     * [Optional]
     */
    private Double width;

    /**
     * The product's height. Provided together with {@code length}, {@code width} and
     * {@code dimension_unit}, or not at all.
     * [Optional]
     */
    private Double height;

    /**
     * The unit that {@code length}, {@code width} and {@code height} are expressed in.
     * [Optional]
     */
    private String dimensionUnit;

    /**
     * The product's weight. Provided together with {@code weight_unit}, or not at all.
     * [Optional]
     */
    private Double weight;

    /**
     * The unit that {@code weight} is expressed in.
     * [Optional]
     */
    private String weightUnit;

    /**
     * The date and time after which the product should no longer be offered.
     * [Optional]
     * Format: date-time (RFC 3339)
     */
    private Instant expirationDate;

    /**
     * The product's Harmonized System (HS) code, for customs purposes.
     * [Optional]
     */
    private String harmonizedSystemCode;

    /**
     * The two-letter ISO 3166-1 alpha-2 country of origin.
     * [Optional]
     */
    private String countryOfOrigin;

    /**
     * The name of the seller of record, when different from the merchant.
     * [Optional]
     */
    private String sellerName;

    /**
     * The URL of the seller of record.
     * [Optional]
     */
    private String sellerUrl;

    /**
     * The URL of the seller's privacy policy.
     * [Optional]
     */
    private String sellerPrivacyPolicy;

    /**
     * The URL of the seller's terms of service.
     * [Optional]
     */
    private String sellerTos;

    /**
     * The date and time the product knowledge was created.
     * [Required]
     * Format: date-time (RFC 3339)
     */
    private Instant createdOn;

    /**
     * The date and time the product knowledge was last modified.
     * [Required]
     * Format: date-time (RFC 3339)
     */
    private Instant modifiedOn;

    /**
     * Links to related operations on the variant's product knowledge.
     * [Required]
     */
    @SerializedName("_links")
    private InventoryProductLinks links;

}
