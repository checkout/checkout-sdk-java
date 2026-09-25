package com.checkout.payments.contexts;

import com.checkout.common.Address;
import com.checkout.common.Phone;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

/**
 * Contains information about the accommodation booked by the customer.
 *
 * @deprecated Duplicates {@link com.checkout.payments.AccommodationData}, which maps the same
 * specification schema. Payment contexts, {@code POST /payments} and the
 * {@code GET /payments/{id}} response all resolve {@code accommodation_data} to that one schema,
 * and maintaining two classes for it let them drift. Use
 * {@link com.checkout.payments.AccommodationData} instead. Retained for backwards compatibility
 * and will be removed in a future version.
 */
@Deprecated
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class PaymentContextsAccommodationData {

    /**
     * The name of the accommodation.
     * [Optional]
     */
    private String name;

    /**
     * The booking reference.
     * [Optional]
     */
    private String bookingReference;

    /**
     * The actual or scheduled check-in date. For cruise: the cruise departure (sail) date.
     * [Optional]
     * Format: yyyy-MM-dd
     */
    private LocalDate checkInDate;

    /**
     * The actual or scheduled check-out date. For cruise: the cruise return (sail end) date.
     * [Optional]
     * Format: yyyy-MM-dd
     */
    private LocalDate checkOutDate;

    /**
     * The address of the accommodation.
     * [Optional]
     */
    private Address address;

    /**
     * The state or province of the address country
     * (ISO 3166-2 code of up to two alphanumeric characters).
     * [Optional]
     * <p>
     * A free-form string, not a country code. The specification's own example is {@code FL},
     * which is a US state rather than a country.
     */
    private String state;

    /**
     * The ISO country code of the address.
     * [Optional]
     * <p>
     * A free-form string rather than an ISO 3166-1 alpha-2 enum: the specification's example is
     * the three-letter code {@code USA}, which no alpha-2 enum can represent.
     */
    private String country;

    /**
     * The city of the accommodation.
     * [Optional]
     */
    private String city;

    /**
     * The number of rooms booked.
     * [Optional]
     */
    private Integer numberOfRooms;

    /**
     * Information about the guests staying at the accommodation.
     * [Optional]
     */
    private List<PaymentContextsGuests> guests;

    /**
     * Information about the rooms booked by the customer.
     * [Optional]
     */
    private List<PaymentContextsAccommodationRoom> room;

    /**
     * The property's phone information.
     * [Optional]
     */
    private List<Phone> propertyPhone;

    /**
     * The customer service phone information.
     * [Optional]
     */
    private List<Phone> customerServicePhone;
}
