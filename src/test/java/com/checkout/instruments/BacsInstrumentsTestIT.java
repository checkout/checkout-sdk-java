package com.checkout.instruments;

import com.checkout.common.CountryCode;
import com.checkout.common.Currency;
import com.checkout.instruments.create.CreateBacsAccountHolder;
import com.checkout.instruments.create.CreateBacsBillingAddress;
import com.checkout.instruments.create.CreateBacsInstrumentAccount;
import com.checkout.instruments.create.CreateBacsInstrumentData;
import com.checkout.instruments.create.CreateInstrumentBacsRequest;
import com.checkout.instruments.create.CreateInstrumentBacsResponse;
import com.checkout.payments.AbstractPaymentsTestIT;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class BacsInstrumentsTestIT extends AbstractPaymentsTestIT {

    @Test
    @Disabled("Requires a merchant enabled for Bacs Direct Debit")
    void shouldCreateInstrumentBacs() {

        final CreateInstrumentBacsRequest request = CreateInstrumentBacsRequest.builder()
                .account(CreateBacsInstrumentAccount.builder()
                        .processingChannelId("pc_q4dbxom5jbgudnjzjpz7j2z6uq")
                        .build())
                .instrumentData(CreateBacsInstrumentData.builder()
                        .accountNumber("86753246")
                        .bankCode("040004")
                        .country(CountryCode.GB)
                        .currency(Currency.GBP)
                        .paymentType(BacsPaymentType.RECURRING)
                        .build())
                .accountHolder(CreateBacsAccountHolder.builder()
                        .firstName("John")
                        .lastName("Smith")
                        .billingAddress(CreateBacsBillingAddress.builder()
                                .addressLine1("Cloverfield St.")
                                .addressLine2("23A")
                                .city("London")
                                .zip("SW1A 1AA")
                                .country(CountryCode.GB)
                                .build())
                        .build())
                .build();

        final CreateInstrumentBacsResponse response =
                blocking(() -> checkoutApi.instrumentsClient().create(request));

        assertNotNull(response);
        assertNotNull(response.getId());
        assertNotNull(response.getFingerprint());
    }
}
