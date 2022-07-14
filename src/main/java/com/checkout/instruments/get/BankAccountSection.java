package com.checkout.instruments.get;

import lombok.Data;

import java.util.List;

@Data
public final class BankAccountSection {

    private String name;

    private List<BankAccountField> fields;

}
