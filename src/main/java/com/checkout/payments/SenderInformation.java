package com.checkout.payments;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SenderInformation {
	private String firstName;
	private String lastName;
	private String address;
	private String city;
	private String postalCode;
	private String state;
	private String country;
	private String sourceOfFunds;
	private String accountNumber;
	private String reference;
}
