package com.checkout.accounts;

import com.google.gson.annotations.SerializedName;

/**
 * The document type accepted as tax verification: an IRS-issued Employer Identification Number
 * letter.
 */
public enum TaxVerificationType {

    @SerializedName("ein_letter")
    EIN_LETTER,
}
