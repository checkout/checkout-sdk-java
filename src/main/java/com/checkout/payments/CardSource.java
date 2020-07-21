package com.checkout.payments;

import com.checkout.common.Address;
import com.checkout.common.CheckoutUtils;
import com.checkout.common.Phone;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class CardSource implements RequestSource {
    public static final String TYPE_NAME = "card";
    private final String number;
    private final int expiryMonth;
    private final int expiryYear;
    private final String type = TYPE_NAME;
    private String name;
    private String firstName;
    private String lastName;
    private String cvv;
    private Address billingAddress;
    private Phone phone;
    private boolean stored;

    public CardSource(String number, int expiryMonth, int expiryYear) {
        if (CheckoutUtils.isNullOrWhitespace(number)) {
            throw new IllegalArgumentException("The card number is required.");
        }
        if (expiryMonth < 1 || expiryMonth > 12) {
            throw new IllegalArgumentException("The expiry month must be between 1 and 12");
        }

        this.number = number;
        this.expiryMonth = expiryMonth;
        this.expiryYear = expiryYear;
    }

    @Override
    public String getType() {
        return type;
    }

    /**
     * Set the full name of a payin card source object.
     * When using card as a payout destination you should use
     * {@link #setFirstName(String)} and {@link #setLastName(String)}
     *
     * @param name the full name of the source card holder
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Set the first name of a payout card destination object.
     * When using card as a payin source you should use {@link #setName(String)}
     *
     * @param firstName the first name of the destination card holder
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Set the last name of a payout card destination object.
     * When using card as a payin source you should use {@link #setName(String)}
     *
     * @param lastName the last name of the destination card holder
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}