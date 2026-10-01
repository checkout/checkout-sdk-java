package com.checkout.accounts;

import com.google.gson.annotations.SerializedName;

/**
 * The document type accepted as proof of the company's principal place of business. Carries the
 * same {@code proof_of_address} value as {@link ProofOfResidentialAddressType}, but the API defines
 * the two as separate enums on separate documents.
 */
public enum ProofOfPrincipalAddressType {

    @SerializedName("proof_of_address")
    PROOF_OF_ADDRESS

}