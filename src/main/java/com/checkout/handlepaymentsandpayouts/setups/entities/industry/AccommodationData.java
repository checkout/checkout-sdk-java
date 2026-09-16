package com.checkout.handlepaymentsandpayouts.setups.entities.industry;

import com.checkout.common.Address;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

/**
 * Accommodation industry-specific data for hotel and cruise bookings and related payments.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class AccommodationData {

    /**
     * For lodging, the lodging name that appears on the storefront and customer receipts.
     * For cruise, the ship name booked for the cruise.
     * [Optional]
     */
    private String name;

    /**
     * A unique identifier for the booking.
     * [Optional]
     */
    private String bookingReference;

    /**
     * For lodging bookings, the customer's check-in date.
     * For cruise bookings, the cruise departure date (sail date).
     * [Optional]
     * Format: yyyy-MM-dd
     */
    private LocalDate checkInDate;

    /**
     * For lodging bookings, the customer's check-out date.
     * For cruise bookings, the cruise return date.
     * [Optional]
     * Format: yyyy-MM-dd
     */
    private LocalDate checkOutDate;

    /**
     * The accommodation's address.
     * [Optional]
     */
    private Address address;

    /**
     * The total number of rooms booked for the accommodation.
     * [Optional]
     */
    private Long numberOfRooms;

    /**
     * The list of guests staying at the accommodation.
     * [Optional]
     */
    private List<AccommodationGuest> guests;

    /**
     * The list of rooms booked by the customer.
     * [Optional]
     */
    private List<AccommodationRoom> room;

    /**
     * The total number of guests on the booking.
     * [Optional]
     */
    private Long totalNumberOfGuests;

    /**
     * Specifies whether the booking is refundable.
     * [Optional]
     */
    private Boolean refundable;

    /**
     * The recipient the booking confirmation is delivered to.
     * [Optional]
     */
    private String deliveryRecipient;

    /**
     * Details about the host of the accommodation.
     * [Optional]
     */
    private AccommodationHost host;
}
