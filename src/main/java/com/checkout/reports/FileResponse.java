package com.checkout.reports;

import com.checkout.common.Resource;
import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class FileResponse extends Resource {

    private String id;

    @SerializedName("filename")
    private String fileName;

    private String format;
}
