package com.checkout.payments;

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
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class AccommodationData {

    /**
     * The name of the accommodation property.
     * [Optional]
     */
    private String name;

    /**
     * The booking reference for this accommodation.
     * [Optional]
     */
    private String bookingReference;

    /**
     * The check-in date.
     * [Optional]
     * Format: date (yyyy-MM-dd)
     */
    private LocalDate checkInDate;

    /**
     * The check-out date.
     * [Optional]
     * Format: date (yyyy-MM-dd)
     */
    private LocalDate checkOutDate;

    /**
     * The address details of the accommodation.
     * [Optional]
     * <p>
     * The specification defines only {@code address_line1} and {@code zip} on this object. The
     * wider {@link Address} type is reused for consistency with the rest of the SDK; the
     * remaining members are not read by the API on this property.
     */
    private Address address;

    /**
     * The state or region code where the property is located, as an ISO 3166-2 subdivision code.
     * [Optional]
     */
    private String state;

    /**
     * The ISO country code of the address.
     * [Optional]
     * <p>
     * A free-form string rather than an ISO 3166-1 alpha-2 enum: the specification's example is
     * the three-letter code {@code USA}, which no alpha-2 enum can represent. Mapping as string.
     */
    private String country;

    /**
     * The city where the property is located.
     * [Optional]
     */
    private String city;

    /**
     * The number of rooms booked.
     * [Optional]
     */
    private Integer numberOfRooms;

    /**
     * The list of guests for this booking.
     * [Optional]
     */
    private List<AccommodationGuest> guests;

    /**
     * The room details for this booking.
     * [Optional]
     */
    private List<AccommodationRoom> room;

    /**
     * The property's phone numbers.
     * [Optional]
     */
    private List<Phone> propertyPhone;

    /**
     * The customer service phone numbers for the property.
     * [Optional]
     */
    private List<Phone> customerServicePhone;

}
