package com.checkout.accounts.files.response;

import com.checkout.common.Resource;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class FileUploadResponse extends Resource {

    private String id;

    private Long maximumSizeInBytes;

    private List<String> documentTypesForPurpose;
}