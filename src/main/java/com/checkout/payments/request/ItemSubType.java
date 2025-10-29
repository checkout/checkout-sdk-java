package com.checkout.payments.request;

import com.google.gson.annotations.SerializedName;

public enum ItemSubType {

    @SerializedName("blockchain")
    BLOCKCHAIN,
    @SerializedName("cbdc")
    CBDC,
    @SerializedName("cryptocurrency")
    CRYPTOCURRENCY,
    @SerializedName("nft")
    NFT,
    @SerializedName("stablecoin")
    STABLECOIN

}
