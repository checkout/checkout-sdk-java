package com.checkout.instruments.update;

import com.checkout.common.AccountHolder;
import com.checkout.common.CountryCode;
import com.checkout.common.Currency;
import com.checkout.common.InstrumentType;
import com.checkout.payments.PaymentType;
import com.google.gson.annotations.SerializedName;
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
    @SerializedName("instrument_data")
    private SepaInstrumentData instrumentData;

    /**
     * The account holder details.
     * [Optional]
     */
    @SerializedName("account_holder")
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
         * The SEPA instrument type.
         * [Optional]
         */
        private String type;

        /**
         * The IBAN account number.
         * [Optional]
         */
        @SerializedName("account_number")
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
        @SerializedName("payment_type")
        private PaymentType paymentType;

        /**
         * The SEPA mandate identifier.
         * [Optional]
         */
        @SerializedName("mandate_id")
        private String mandateId;

        /**
         * The date the SEPA mandate was signed.
         * [Optional]
         * Format: yyyy-MM-dd
         */
        @SerializedName("date_of_signature")
        private LocalDate dateOfSignature;

    }

}
