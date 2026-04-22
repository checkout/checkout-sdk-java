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

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public final class UpdateInstrumentAchRequest extends UpdateInstrumentRequest {

    private AchInstrumentData instrumentData;

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

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static final class AchInstrumentData {

        private AchInstrumentAccountType accountType;

        private String accountNumber;

        private String bankCode;

        private Currency currency;

        private CountryCode country;

    }

}
