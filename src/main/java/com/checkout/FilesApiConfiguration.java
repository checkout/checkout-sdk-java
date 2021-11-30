package com.checkout;

import com.checkout.common.CheckoutUtils;

public final class FilesApiConfiguration {

    private final Environment filesApiEnvironment;

    public FilesApiConfiguration(final Environment filesApiEnvironment) {
        CheckoutUtils.validateParams("filesApiEnvironment", filesApiEnvironment);
        this.filesApiEnvironment = filesApiEnvironment;
    }

    public Environment getFilesApiEnvironment() {
        return filesApiEnvironment;
    }

}
