package com.checkout.accounts;

import com.google.gson.annotations.SerializedName;

/**
 * The document types accepted as company verification. {@code articles_of_association} is
 * accepted on the US Company (2.0) variants only; articles of association sent as their own
 * document use {@link ArticlesOfAssociationType} instead.
 */
public enum CompanyVerificationType {

    @SerializedName("incorporation_document")
    INCORPORATION_DOCUMENT,
    @SerializedName("articles_of_association")
    ARTICLES_OF_ASSOCIATION,
}
