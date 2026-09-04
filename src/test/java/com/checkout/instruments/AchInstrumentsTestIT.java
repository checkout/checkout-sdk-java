package com.checkout.instruments;

import com.checkout.common.CountryCode;
import com.checkout.common.Currency;
import com.checkout.instruments.create.CreateAchAccountHolder;
import com.checkout.instruments.create.CreateAchInstrumentData;
import com.checkout.instruments.create.CreateInstrumentAchRequest;
import com.checkout.instruments.create.CreateInstrumentAchResponse;
import com.checkout.instruments.get.GetAchInstrumentResponse;
import com.checkout.instruments.update.AchInstrumentAccountType;
import com.checkout.payments.AbstractPaymentsTestIT;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class AchInstrumentsTestIT extends AbstractPaymentsTestIT {

    @Test
    @Disabled("Requires a merchant enabled for ACH Direct Debit")
    void shouldCreateAndGetInstrumentAch() {

        final CreateInstrumentAchRequest request = CreateInstrumentAchRequest.builder()
                .instrumentData(CreateAchInstrumentData.builder()
                        .accountType(AchInstrumentAccountType.SAVINGS)
                        .accountNumber("4099999992")
                        .bankCode("211370545")
                        .currency(Currency.USD)
                        .country(CountryCode.US)
                        .build())
                .accountHolder(CreateAchAccountHolder.builder()
                        .firstName("John")
                        .lastName("Smith")
                        .companyName("Smith Enterprises")
                        .type(InstrumentAccountHolderType.INDIVIDUAL)
                        .build())
                .build();

        final CreateInstrumentAchResponse created =
                blocking(() -> checkoutApi.instrumentsClient().create(request));

        assertNotNull(created);
        assertNotNull(created.getId());
        assertNotNull(created.getFingerprint());

        final GetAchInstrumentResponse retrieved =
                (GetAchInstrumentResponse) blocking(() -> checkoutApi.instrumentsClient().get(created.getId()));

        assertNotNull(retrieved);
        assertNotNull(retrieved.getInstrumentData());
        assertNotNull(retrieved.getAccountHolder());
        assertNotNull(retrieved.getVaultId());
    }
}
