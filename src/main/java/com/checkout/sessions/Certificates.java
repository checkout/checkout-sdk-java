package com.checkout.sessions;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public final class Certificates {

    @SerializedName("ds_public")
    private String dsPublic;

    @SerializedName("ca_public")
    private String caPublic;

}
