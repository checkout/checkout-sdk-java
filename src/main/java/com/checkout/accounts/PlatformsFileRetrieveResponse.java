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
public class PlatformsFileRetrieveResponse extends Resource {

    private String id;

    private String status;

    @SerializedName("status_reasons")
    private List<String> statusReasons;

    @SerializedName("file_name")
    private String fileName;

    private Integer size;

    @SerializedName("mime_type")
    private String mimeType;

    @SerializedName("uploaded_on")
    private String uploadedOn;

    private String purpose;
}
