package com.checkout.payments.response.destination;

import com.checkout.common.AccountHolder;
import com.checkout.common.AccountType;
import com.checkout.common.BankDetails;
import com.checkout.common.CountryCode;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public final class PaymentResponseBankAccountDestination extends AbstractPaymentResponseDestination implements PaymentResponseDestination {

    private AccountType accountType;

    private String accountNumber;

    private String bankCode;

    private String branchCode;

    private String iban;

    private String bban;

    private String swiftBic;

    private CountryCode country;

    private AccountHolder accountHolder;

    private BankDetails bank;

}
