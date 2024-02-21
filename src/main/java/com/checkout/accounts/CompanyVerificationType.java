package com.checkout.accounts;

import com.google.gson.annotations.SerializedName;

public enum CompanyVerificationType {

    @SerializedName("incorporation_document")
    INCORPORATION_DOCUMENT,
    @SerializedName("articles_of_association")
    ARTICLES_OF_ASSOCIATION,
}
