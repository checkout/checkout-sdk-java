package com.checkout.handlepaymentsandpayouts.flow.requests;

import com.checkout.payments.ProductRequest;
import com.checkout.payments.PaymentType;
import com.checkout.payments.ThreeDSRequest;
import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.experimental.SuperBuilder;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Base class for all payment session requests containing common properties
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public abstract class PaymentSessionBase {

    /**
     * The payment amount. Provide a value of 0 to perform a card verification.
     * The amount must be provided in the minor currency unit.
     * For example, provide 10000 for £100.00, or provide 100 for ¥100 (a zero-decimal currency).
     * [Optional]
     * min 0
     */
    private Long amount;

    /**
     * A reference you can use to identify the payment. For example, an order number.
     * For Amex payments, this must be at most 30 characters.
     * For Benefit payments, the reference must be a unique alphanumeric value.
     * For iDEAL payments, the reference is required and must be an alphanumeric value with a 35-character limit.
     * [Optional]
     */
    private String reference;

    /**
     * The line items in the order.
     * [Optional]
     */
    private List<ProductRequest> items;

    /**
     * Information required for 3D Secure authentication payments.
     * [Optional]
     */
    @SerializedName("3ds")
    private ThreeDSRequest threeDS;

    /**
     * Must be specified for card-not-present (CNP) payments. Default: "Regular"
     * [Optional]
     * Enum: "Regular" "Recurring" "MOTO" "Installment" "PayLater" "Unscheduled"
     */
    @Builder.Default
    private PaymentType paymentType = PaymentType.REGULAR;
}
