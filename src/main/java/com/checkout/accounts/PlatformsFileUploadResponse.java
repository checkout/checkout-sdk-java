package com.checkout.accounts;

import com.checkout.common.Resource;
import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.List;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
public final class PlatformsFileUploadResponse extends Resource {

    private String id;

    private String status;

    @SerializedName("maximum_size_in_bytes")
    private Integer maximumSizeInBytes;

    @SerializedName("document_types_for_purpose")
    private List<String> documentTypesForPurpose;
}
