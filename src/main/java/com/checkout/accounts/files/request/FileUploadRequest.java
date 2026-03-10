package com.checkout.accounts.files.request;


import com.checkout.accounts.files.entities.FilePurpose;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FileUploadRequest {

    private FilePurpose purpose;
}