package com.checkout.instruments.four.get;

import lombok.Data;

import java.util.List;

@Data
public final class BankAccountFieldResponse {

    private List<BankAccountSection> sections;

}
