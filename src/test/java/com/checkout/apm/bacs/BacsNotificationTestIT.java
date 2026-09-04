package com.checkout.apm.bacs;

import com.checkout.PlatformType;
import com.checkout.SandboxTestFixture;
import com.checkout.common.Currency;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class BacsNotificationTestIT extends SandboxTestFixture {

    BacsNotificationTestIT() {
        super(PlatformType.DEFAULT);
    }

    @Test
    @Disabled("Requires a merchant enabled for Bacs Direct Debit and an existing Bacs instrument")
    void shouldSendNotification() {

        final BacsNotificationRequest request = BacsNotificationRequest.builder()
                .sourceId("src_wmlfc3zyhqzehihu7giusaaawu")
                .notificationType(BacsNotificationType.ADVANCE_NOTICE)
                .collectionDate(LocalDate.of(2026, 7, 15))
                .amount(4999L)
                .currency(Currency.GBP)
                .reference("INV-12345")
                .customerEmail("customer@example.com")
                .billingDescriptor("CHECKOUT")
                .supportEmail("support@test.com")
                .supportPhone("+447700900123")
                .build();

        final BacsNotificationResponse response =
                blocking(() -> checkoutApi.bacsClient().sendNotification(request));

        assertNotNull(response);
        assertNotNull(response.getEventId());
    }
}
