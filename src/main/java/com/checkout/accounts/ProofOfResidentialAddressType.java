package com.checkout.accounts;

import com.google.gson.annotations.SerializedName;

/**
 * The document type accepted as a representative's proof of residential address (EEA Sole Trader
 * Full (3.0)). Carries the same {@code proof_of_address} value as {@link ProofOfPrincipalAddressType},
 * but the API defines the two as separate enums on separate documents.
 */
public enum ProofOfResidentialAddressType {

    @SerializedName("proof_of_address")
    PROOF_OF_ADDRESS

}
