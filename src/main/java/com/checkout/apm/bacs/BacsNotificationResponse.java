package com.checkout.apm.bacs;

import com.checkout.HttpMetadata;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Bacs Direct Debit pre-notification response.
 */
@Data
@EqualsAndHashCode(callSuper = true)
public final class BacsNotificationResponse extends HttpMetadata {

    /**
     * The unique identifier of the notification event.
     * [Required]
     */
    private String eventId;

}
