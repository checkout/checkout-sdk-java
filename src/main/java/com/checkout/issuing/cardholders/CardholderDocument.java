package com.checkout.issuing.cardholders;

import com.checkout.common.DocumentType;
import com.google.gson.annotations.SerializedName;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CardholderDocument {

    public DocumentType type;

    @SerializedName("front_document_id")
    public String frontDocumentId;

    @SerializedName("back_document_id")
    public String backDocumentId;
}
