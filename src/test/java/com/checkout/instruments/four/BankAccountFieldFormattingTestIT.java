package com.checkout.instruments.four;

import com.checkout.PlatformType;
import com.checkout.SandboxTestFixture;
import com.checkout.common.CountryCode;
import com.checkout.common.Currency;
import com.checkout.common.four.AccountHolderType;
import com.checkout.instruments.four.get.BankAccountFieldQuery;
import com.checkout.instruments.four.get.BankAccountFieldResponse;
import com.checkout.instruments.four.get.PaymentNetwork;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class BankAccountFieldFormattingTestIT extends SandboxTestFixture {


    BankAccountFieldFormattingTestIT() {
        super(PlatformType.FOUR_OAUTH);
    }

    @Test
    void shouldGetBankAccountFieldFormatting() {
        final BankAccountFieldQuery query = BankAccountFieldQuery.builder()
                .accountHolderType(AccountHolderType.INDIVIDUAL)
                .paymentNetwork(PaymentNetwork.LOCAL)
                .build();
        final BankAccountFieldResponse response = blocking(fourApi.instrumentsClient().getBankAccountFieldFormatting(CountryCode.GB, Currency.GBP, query));
        assertNotNull(response);
        assertNotNull(response.getSections());
        assertFalse(response.getSections().isEmpty());
        response.getSections().forEach(section -> {
            assertNotNull(section.getName());
            assertNotNull(section.getFields());
            assertFalse(section.getFields().isEmpty());
            section.getFields().forEach(field -> {
                assertNotNull(field.getId());
                assertNotNull(field.getDisplay());
                assertNotNull(field.getType());
            });
        });
    }

}