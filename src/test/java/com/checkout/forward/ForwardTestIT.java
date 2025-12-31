package com.checkout.forward;

import com.checkout.PlatformType;
import com.checkout.SandboxTestFixture;
import com.checkout.forward.requests.DestinationRequest;
import com.checkout.forward.requests.ForwardRequest;
import com.checkout.forward.requests.Headers;
import com.checkout.forward.requests.MethodType;
import com.checkout.forward.requests.NetworkToken;
import com.checkout.forward.requests.signatures.DlocalParameters;
import com.checkout.forward.requests.signatures.DlocalSignature;
import com.checkout.forward.requests.sources.IdSource;
import com.checkout.forward.responses.ForwardAnApiResponse;
import com.checkout.forward.responses.GetForwardResponse;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ForwardTestIT extends SandboxTestFixture {

    public ForwardTestIT() {
        super(PlatformType.DEFAULT_OAUTH);
    }

    @Disabled("This test requires a valid id or Token source")
    @Test
    void shouldForwardAnApiRequest() {

        final ForwardRequest request = createForwardRequest();

        final ForwardAnApiResponse response = blocking(() -> checkoutApi.forwardClient().forwardAnApiRequest(request));

        validateForwardAnApiResponse(response);

    }

    @Disabled("This test requires a valid id or Token source")
    @Test
    void shouldGetForwardRequest() {

        final ForwardRequest request = createForwardRequest();

        final ForwardAnApiResponse forwardResponse = blocking(() -> checkoutApi.forwardClient().forwardAnApiRequest(request));

        final GetForwardResponse response = blocking(() -> checkoutApi.forwardClient().getForwardRequest(forwardResponse.getRequestId()));

        validateGetForwardResponse(response);

    }

    // Sync methods
    @Disabled("This test requires a valid id or Token source")
    @Test
    void shouldForwardAnApiRequestSync() {

        final ForwardRequest request = createForwardRequest();

        final ForwardAnApiResponse response = checkoutApi.forwardClient().forwardAnApiRequestSync(request);

        validateForwardAnApiResponse(response);

    }

    @Disabled("This test requires a valid id or Token source")
    @Test
    void shouldGetForwardRequestSync() {

        final ForwardRequest request = createForwardRequest();

        final ForwardAnApiResponse forwardResponse = checkoutApi.forwardClient().forwardAnApiRequestSync(request);
        final GetForwardResponse response = checkoutApi.forwardClient().getForwardRequestSync(forwardResponse.getRequestId());

        validateGetForwardResponse(response);

    }

    // Common methods
    private static ForwardRequest createForwardRequest() {
        final IdSource source = IdSource.builder()
                .id("src_v5rgkf3gdtpuzjqesyxmyodnya")
                .build();

        final NetworkToken networkToken = NetworkToken.builder()
                .enabled(true)
                .requestCryptogram(false)
                .build();

        final Map<String, String> raw = new HashMap<>();
        raw.put("Idempotency-Key", "xe4fad12367dfgrds");
        raw.put("Content-Type", "application/json");

        final Headers headers = Headers.builder()
                .raw(raw)
                .build();

        final DlocalParameters dlocalParameters = DlocalParameters.builder()
                .SecretKey("9f439fe1a9f96e67b047d3c1a28c33a2e")
                .build();

        final DlocalSignature dlocalSignature = DlocalSignature.builder()
                .dlocalParameters(dlocalParameters)
                .build();

        final DestinationRequest destinationRequest = DestinationRequest.builder()
                .method(MethodType.POST)
                .url("https://example.com/payments")
                .headers(headers)
                .body("{\"amount\": 1000, \"currency\": \"USD\", \"reference\": \"some_reference\", \"source\": {\"type\": \"card\", \\\"number\\\": \\\"{{card_number}}\\\", \\\"expiry_month\\\": \\\"{{card_expiry_month}}\\\", \\\"expiry_year\\\": \\\"{{card_expiry_year_yyyy}}\\\", \\\"name\\\": \\\"Ali Farid\\\"}, \\\"payment_type\\\": \\\"Regular\\\", \\\"authorization_type\\\": \\\"Final\\\", \\\"capture\\\": true, \\\"processing_channel_id\\\": \\\"pc_xxxxxxxxxxx\\\", \\\"risk\\\": {\\\"enabled\\\": false}, \\\"merchant_initiated\\\": true}")
                .signature(dlocalSignature)
                .build();

        return ForwardRequest.builder()
                .source(source)
                .reference("ORD-5023-4E89")
                .processingChannelId(requireNonNull(System.getenv("CHECKOUT_PROCESSING_CHANNEL_ID")))
                .networkToken(networkToken)
                .destinationRequest(destinationRequest)
                .build();
    }

    private void validateForwardAnApiResponse(final ForwardAnApiResponse response) {
        assertNotNull(response);
        assertNotNull(response.getRequestId());
        assertNotNull(response.getDestinationResponse());
    }

    private void validateGetForwardResponse(final GetForwardResponse response) {
        assertNotNull(response);
        assertNotNull(response.getRequestId());
        assertNotNull(response.getEntityId());
        assertNotNull(response.getDestinationRequest());
        assertNotNull(response.getCreatedOn());
        assertNotNull(response.getReference());
        assertNotNull(response.getDestinationResponse());
    }
}
