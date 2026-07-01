package com.checkout.instruments.create;

import com.checkout.common.BankDetails;
import com.checkout.common.InstrumentType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public final class CreateInstrumentBankAccountResponse extends CreateInstrumentResponse {

    private final InstrumentType type = InstrumentType.BANK_ACCOUNT;

    private BankDetails bank;

    private String swiftBic;

    private String accountNumber;

    private String bankCode;

    private String iban;

}
