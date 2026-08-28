package com.checkout.instruments.update;

import com.checkout.common.AccountHolder;
import com.checkout.common.CountryCode;
import com.checkout.common.Currency;
import com.checkout.common.InstrumentType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Update ACH bank account details.
 *
 * <p>Nothing in this request is required by the specification.
 */
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public final class UpdateInstrumentAchRequest extends UpdateInstrumentRequest {

    /**
     * The details of the bank account.
     * [Optional]
     */
    private AchInstrumentData instrumentData;

    /**
     * The account holder details.
     * [Optional]
     */
    private AccountHolder accountHolder;

    @Builder
    private UpdateInstrumentAchRequest(final AchInstrumentData instrumentData,
                                       final AccountHolder accountHolder) {
        super(InstrumentType.ACH);
        this.instrumentData = instrumentData;
        this.accountHolder = accountHolder;
    }

    public UpdateInstrumentAchRequest() {
        super(InstrumentType.ACH);
    }

    /**
     * The details of the ACH account being updated.
     *
     * <p>Every property is optional on update. The shape is identical to the store and retrieve
     * variants, unlike the Bacs Direct Debit instrument data, whose length limits differ per
     * operation.
     */
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static final class AchInstrumentData {

        /**
         * The type of Direct Debit account.
         * [Optional]
         */
        private AchInstrumentAccountType accountType;

        /**
         * The account number of the Direct Debit account.
         * [Optional]
         * min 4 characters
         * max 17 characters
         */
        private String accountNumber;

        /**
         * The bank code of the Direct Debit account, also known as the routing number.
         * [Optional]
         * min 8 characters
         * max 9 characters
         */
        private String bankCode;

        /**
         * The currency of the account.
         * [Optional]
         * min 3 characters
         * max 3 characters
         */
        private Currency currency;

        /**
         * The country of the account.
         * [Optional]
         * min 2 characters
         * max 2 characters
         */
        private CountryCode country;

    }

}
