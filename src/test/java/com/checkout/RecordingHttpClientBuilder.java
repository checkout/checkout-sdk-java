package com.checkout;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.HttpVersion;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicHttpResponse;
import org.apache.http.message.BasicStatusLine;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HttpContext;

/**
 * HTTP client builder whose client records the URI of every request and answers 200 with an empty
 * JSON object; nothing leaves the process.
 */
final class RecordingHttpClientBuilder extends HttpClientBuilder {

    final List<String> requests = Collections.synchronizedList(new ArrayList<>());

    @Override
    public CloseableHttpClient build() {
        return new CloseableHttpClient() {
            @Override
            protected CloseableHttpResponse doExecute(final HttpHost target, final HttpRequest request, final HttpContext context) {
                requests.add(((HttpUriRequest) request).getURI().toString());
                final StubResponse response = new StubResponse();
                response.setEntity(new StringEntity("{}", ContentType.APPLICATION_JSON.withCharset(StandardCharsets.UTF_8)));
                return response;
            }

            @Override
            public void close() {
                // nothing to release
            }

            @Override
            @SuppressWarnings("deprecation")
            public HttpParams getParams() {
                throw new UnsupportedOperationException();
            }

            @Override
            @SuppressWarnings("deprecation")
            public ClientConnectionManager getConnectionManager() {
                throw new UnsupportedOperationException();
            }
        };
    }

    private static final class StubResponse extends BasicHttpResponse implements CloseableHttpResponse {

        private StubResponse() {
            super(new BasicStatusLine(HttpVersion.HTTP_1_1, 200, "OK"));
        }

        @Override
        public void close() {
            // nothing to release
        }
    }
}
