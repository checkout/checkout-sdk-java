package com.checkout.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class Destination {

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