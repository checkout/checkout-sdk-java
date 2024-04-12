package com.checkout.instruments;

import com.checkout.common.AccountHolder;
import com.checkout.common.AccountHolderType;
import com.checkout.common.Address;
import com.checkout.common.CountryCode;
import com.checkout.common.Currency;
import com.checkout.common.Phone;
import com.checkout.instruments.create.CreateInstrumentSepaRequest;
import com.checkout.instruments.create.CreateInstrumentSepaResponse;
import com.checkout.instruments.create.InstrumentData;
import com.checkout.payments.AbstractPaymentsTestIT;
import com.checkout.payments.PaymentType;
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
                        .paymentType(PaymentType.RECURRING)
                        .build())
                .accountHolder(AccountHolder.builder()
                        .type(AccountHolderType.INDIVIDUAL)
                        .firstName("Ali")
                        .lastName("Farid")
                        .dateOfBirth("1986-01-01T00:00:00.000Z")
                        .billingAddress(Address.builder()
                                .addressLine1("Rue Exemple")
                                .addressLine2("1")
                                .city("Paris")
                                .zip("1234")
                                .country(CountryCode.FR)
                                .build())
                        .phone(Phone.builder()
                                .countryCode("33")
                                .number("123456789")
                                .build())
                        .build())
                .build();
        final CreateInstrumentSepaResponse response = blocking(() -> checkoutApi.instrumentsClient().create(request));
        assertNotNull(response);
        assertNotNull(response.getId());
        assertNotNull(response.getFingerprint());
    }
}