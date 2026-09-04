package com.checkout.apm.bacs;

import com.checkout.common.Currency;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * Bacs Direct Debit notification request.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class BacsNotificationRequest {

    /**
     * The ID of the Bacs Direct Debit instrument to notify against.
     * [Required]
     * ^(src)_(\w{26})$
     */
    private String sourceId;

    /**
     * The type of pre-notification being sent to the payer.
     * [Required]
     */
    private BacsNotificationType notificationType;

    /**
     * The date the funds will be collected from the payer's account, in the format yyyy-MM-dd.
     * [Required]
     * Format: yyyy-MM-dd
     */
    private LocalDate collectionDate;

    /**
     * The amount to be collected, in the currency's minor unit.
     * [Required]
     * Format: int64
     * min 1
     */
    private Long amount;

    /**
     * The three-letter ISO 4217 currency code of the collection.
     * [Required]
     * min 3 characters
     * max 3 characters
     */
    private Currency currency;

    /**
     * A reference you can use to identify the collection.
     * [Optional]
     * max 50 characters
     */
    private String reference;

    /**
     * The email address of the payer that the pre-notification is sent to.
     * [Required]
     * Format: email
     */
    private String customerEmail;

    /**
     * The billing descriptor that appears on the payer's bank statement.
     * [Required]
     * max 25 characters
     */
    private String billingDescriptor;

    /**
     * The support email address included in the pre-notification.
     * [Required]
     * Format: email
     */
    private String supportEmail;

    /**
     * The support phone number included in the pre-notification, in E.164 format. The
     * specification declares no pattern for this property.
     * [Optional]
     */
    private String supportPhone;

}
