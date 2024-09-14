package top.kexcellent.back.code.http;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.client.ClientHttpRequest;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * {@link ClientHttpRequestFactory} implementation that uses
 * <a href="http://square.github.io/okhttp/">OkHttp</a> 3.x to create requests.
 *
 * @author JimmyJin
 * @version Id : HttpRequestFactoryService, v 0.1 2018/4/24 11:00 JimmyJin Exp $
 */
public class OkHttp3ClientHttpRequestFactory implements ClientHttpRequestFactory, DisposableBean {

    /**
     * The Client.
     */
    private OkHttpClient  client;

    /**
     * The Default client.
     */
    private final boolean defaultClient;

    /**
     * Create a factory with a default {@link OkHttpClient} instance.
     */
    public OkHttp3ClientHttpRequestFactory() {
        this.client = new OkHttpClient();
        this.defaultClient = true;
    }

    /**
     * Create a factory with the given {@link OkHttpClient} instance.
     *
     * @param client the client to use
     */
    public OkHttp3ClientHttpRequestFactory(OkHttpClient client) {
        Assert.notNull(client, "OkHttpClient must not be null");
        this.client = client;
        this.defaultClient = false;
    }

    /**
     * Sets the underlying read timeout in milliseconds.
     * A value of 0 specifies an infinite timeout.
     *
     * @param readTimeout the read timeout
     * @see OkHttpClient.Builder#readTimeout(long, TimeUnit) OkHttpClient.Builder#readTimeout(long, TimeUnit)OkHttpClient.Builder#readTimeout(long, TimeUnit)
     */
    public void setReadTimeout(int readTimeout) {
        this.client = this.client.newBuilder().readTimeout(readTimeout, TimeUnit.MILLISECONDS).build();
    }

    /**
     * Sets the underlying write timeout in milliseconds.
     * A value of 0 specifies an infinite timeout.
     *
     * @param writeTimeout the write timeout
     * @see OkHttpClient.Builder#writeTimeout(long, TimeUnit) OkHttpClient.Builder#writeTimeout(long, TimeUnit)OkHttpClient.Builder#writeTimeout(long, TimeUnit)
     */
    public void setWriteTimeout(int writeTimeout) {
        this.client = this.client.newBuilder().writeTimeout(writeTimeout, TimeUnit.MILLISECONDS).build();
    }

    /**
     * Sets the underlying connect timeout in milliseconds.
     * A value of 0 specifies an infinite timeout.
     *
     * @param connectTimeout the connect timeout
     * @see OkHttpClient.Builder#connectTimeout(long, TimeUnit) OkHttpClient.Builder#connectTimeout(long, TimeUnit)OkHttpClient.Builder#connectTimeout(long, TimeUnit)
     */
    public void setConnectTimeout(int connectTimeout) {
        this.client = this.client.newBuilder().connectTimeout(connectTimeout, TimeUnit.MILLISECONDS).build();
    }

    /**
     * Create request client http request.
     *
     * @param uri        the uri
     * @param httpMethod the http method
     * @return the client http request
     */
    @Override
    public ClientHttpRequest createRequest(URI uri, HttpMethod httpMethod) {
        return new OkHttp3ClientHttpRequest(this.client, uri, httpMethod);
    }

    /**
     * Destroy.
     *
     * @throws IOException the io exception
     */
    @Override
    public void destroy() throws IOException {
        if (this.defaultClient) {
            // Clean up the client if we created it in the constructor
            if (this.client.cache() != null) {
                this.client.cache().close();
            }
            this.client.dispatcher().executorService().shutdown();
        }
    }

    /**
     * Build request request.
     *
     * @param headers the headers
     * @param content the content
     * @param uri     the uri
     * @param method  the method
     * @return the request
     * @throws MalformedURLException the malformed url exception
     */
    static Request buildRequest(HttpHeaders headers, byte[] content, URI uri, HttpMethod method) throws MalformedURLException {

        okhttp3.MediaType contentType = getContentType(headers);
        RequestBody body = (content.length > 0 || okhttp3.internal.http.HttpMethod.requiresRequestBody(method.name()) ? RequestBody.create(contentType, content) : null);

        Request.Builder builder = new Request.Builder().url(uri.toURL()).method(method.name(), body);
        for (Map.Entry<String, List<String>> entry : headers.entrySet()) {
            String headerName = entry.getKey();
            for (String headerValue : entry.getValue()) {
                builder.addHeader(headerName, headerValue);
            }
        }
        return builder.build();
    }

    /**
     * Gets content type.
     *
     * @param headers the headers
     * @return the content type
     */
    private static okhttp3.MediaType getContentType(HttpHeaders headers) {
        String rawContentType = headers.getFirst("Content-Type");
        return (StringUtils.hasText(rawContentType) ? okhttp3.MediaType.parse(rawContentType) : null);
    }

}
