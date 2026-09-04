package com.checkout.instruments;

import com.checkout.instruments.InstrumentAccountHolderType;
import com.checkout.instruments.create.CreateSepaAccountHolder;
import com.checkout.instruments.create.CreateSepaBillingAddress;
import com.checkout.common.CountryCode;
import com.checkout.common.Currency;
import com.checkout.instruments.create.CreateInstrumentSepaRequest;
import com.checkout.instruments.create.CreateInstrumentSepaResponse;
import com.checkout.instruments.create.InstrumentData;
import com.checkout.instruments.update.SepaPaymentType;
import com.checkout.payments.AbstractPaymentsTestIT;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class SepaInstrumentsTestIT extends AbstractPaymentsTestIT {

    @Test
    void shouldCreateInstrumentSepa() {

        final CreateInstrumentSepaRequest request = CreateInstrumentSepaRequest.builder()
                .instrumentData(InstrumentData.builder()
                        .accoountNumber("FR7630006000011234567890189")
                        .country(CountryCode.FR)
                        .currency(Currency.EUR)
                        .paymentType(SepaPaymentType.RECURRING)
                        .build())
                .accountHolder(CreateSepaAccountHolder.builder()
                        .type(InstrumentAccountHolderType.INDIVIDUAL)
                        .firstName("Ali")
                        .lastName("Farid")
                        .billingAddress(CreateSepaBillingAddress.builder()
                                .addressLine1("Rue Exemple")
                                .addressLine2("1")
                                .city("Paris")
                                .zip("1234")
                                .country(CountryCode.FR)
                                .build())
                        .build())
                .build();
        final CreateInstrumentSepaResponse response = blocking(() -> checkoutApi.instrumentsClient().create(request));
        assertNotNull(response);
        assertNotNull(response.getId());
        assertNotNull(response.getFingerprint());
    }
}