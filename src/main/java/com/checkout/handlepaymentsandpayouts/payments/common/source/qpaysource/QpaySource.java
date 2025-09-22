package com.checkout.handlepaymentsandpayouts.payments.common.source.qpaysource;

import com.checkout.handlepaymentsandpayouts.payments.common.source.AbstractSource;
import com.checkout.handlepaymentsandpayouts.payments.common.source.SourceType;
import com.google.gson.annotations.SerializedName;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * qpay source Class
 * The source of the payment
 */
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public final class QpaySource extends AbstractSource {

    /**
     * Alphanumeric string containing a description of the payment order.
     * [Required]
     */
    private String description;

    /**
     * QPay Payment Unique Number
     * [Required]
     */
    private String pun;

    /**
     * The status code returned from the QPay gateway on payment, if available.
     * [Optional]
     */
    @SerializedName("qpay_status")
    private String qpayStatus;

    /**
     * A message giving further detail on the payment status, for failure/cancelled/success status payments.
     * [Optional]
     */
    @SerializedName("status_message")
    private String statusMessage;

    /**
     * An identifier from the QPay gateway for a successful payment.
     * [Optional]
     */
    @SerializedName("confirmation_id")
    private String confirmationId;

    /**
     * Initializes a new instance of the QpaySource class.
     */
    @Builder
    private QpaySource(
            final String description,
            final String pun,
            final String qpayStatus,
            final String statusMessage,
            final String confirmationId
    ) {
        super(SourceType.QPAY);
        this.description = description;
        this.pun = pun;
        this.qpayStatus = qpayStatus;
        this.statusMessage = statusMessage;
        this.confirmationId = confirmationId;
    }

    public QpaySource() {
        super(SourceType.QPAY);
    }

}
