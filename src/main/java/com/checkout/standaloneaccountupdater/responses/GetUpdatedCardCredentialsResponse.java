package com.checkout.standaloneaccountupdater.responses;

import com.checkout.common.Resource;
import com.checkout.standaloneaccountupdater.entities.AccountUpdateStatus;
import com.checkout.standaloneaccountupdater.entities.AccountUpdateFailureCode;
import com.checkout.standaloneaccountupdater.entities.CardUpdated;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public final class GetUpdatedCardCredentialsResponse extends Resource {

    /**
     * Result of the update operation.
     * [Required]
     */
    private AccountUpdateStatus accountUpdateStatus;

    /**
     * This field is returned when the update fails and the scheme returns an appropriate reason code.
     * For more information, see Standalone Account Updater
     */
    private AccountUpdateFailureCode accountUpdateFailureCode;

    /**
     * Updated card details. Fields vary depending on PCI compliance level.
     */
    private CardUpdated card;
}