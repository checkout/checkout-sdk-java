package com.checkout.instruments.update;

import com.checkout.common.AccountHolder;
import com.checkout.common.CountryCode;
import com.checkout.common.Currency;
import com.checkout.common.InstrumentType;
import com.checkout.payments.request.source.apm.MandateType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public final class UpdateInstrumentSepaRequest extends UpdateInstrumentRequest {

    /**
     * SEPA-specific instrument data to update.
     * [Optional]
     */
    private SepaInstrumentData instrumentData;

    /**
     * The account holder details.
     * [Optional]
     */
    private AccountHolder accountHolder;

    @Builder
    private UpdateInstrumentSepaRequest(final SepaInstrumentData instrumentData,
                                        final AccountHolder accountHolder) {
        super(InstrumentType.SEPA);
        this.instrumentData = instrumentData;
        this.accountHolder = accountHolder;
    }

    public UpdateInstrumentSepaRequest() {
        super(InstrumentType.SEPA);
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static final class SepaInstrumentData {

        /**
         * The SEPA mandate type.
         * [Optional]
         */
        private MandateType type;

        /**
         * The IBAN account number.
         * [Optional]
         */
        private String accountNumber;

        /**
         * The two-letter ISO country code.
         * [Optional]
         */
        private CountryCode country;

        /**
         * The three-letter ISO currency code.
         * [Optional]
         */
        private Currency currency;

        /**
         * The SEPA payment type.
         * [Optional]
         */
        private SepaPaymentType paymentType;

        /**
         * The SEPA mandate identifier.
         * [Optional]
         */
        private String mandateId;

        /**
         * The date the SEPA mandate was signed.
         * [Optional]
         * Format: yyyy-MM-dd
         */
        private LocalDate dateOfSignature;

    }

}
