package com.checkout.beta;

import com.checkout.CheckoutConfiguration;
import com.checkout.Environment;

public class TestHelper {

    public static final String VALID_CLASSIC_SK = "sk_test_fde517a8-3f01-41ef-b4bd-4282384b0a64";
    public static final String VALID_CLASSIC_PK = "pk_test_fe70ff27-7c32-4ce1-ae90-5691a188ee7b";
    public static final String INVALID_CLASSIC_SK = "sk_test_asdsad3q4dq";
    public static final String INVALID_CLASSIC_PK = "pk_test_q414dasds";
    public static final String VALID_FOUR_SK = "sk_sbox_m73dzbpy7cf3gfd46xr4yj5xo4e";
    public static final String VALID_FOUR_PK = "pk_sbox_pkhpdtvmkgf7hdnpwnbhw7r2uic";
    public static final String INVALID_FOUR_SK = "sk_sbox_m73dzbpy7c-f3gfd46xr4yj5xo4e";
    public static final String INVALID_FOUR_PK = "pk_sbox_pkh";

    public static CheckoutConfiguration mockClassicConfiguration() {
        return new CheckoutConfiguration(VALID_CLASSIC_SK, true, VALID_CLASSIC_PK);
    }

    public static CheckoutConfiguration mockFourConfiguration() {
        return new CheckoutConfiguration(VALID_FOUR_PK, VALID_FOUR_SK, Environment.SANDBOX);
    }

}