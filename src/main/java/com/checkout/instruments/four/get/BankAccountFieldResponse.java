package com.checkout.instruments.four.get;

import com.checkout.HttpMetadata;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public final class BankAccountFieldResponse extends HttpMetadata {

    private List<BankAccountSection> sections;

}
