package com.checkout.marketplace;

import com.checkout.common.AbstractFileRequest;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.http.entity.ContentType;

import java.io.File;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class MarketplaceFileRequest extends AbstractFileRequest {

    private MarketplaceFilePurpose purpose;

    @Builder
    public MarketplaceFileRequest(final File file, final ContentType contentType, final MarketplaceFilePurpose purpose) {
        super(file, contentType);
        this.purpose = purpose;
    }

}
