package top.kexcellent.back.code.http;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.client.AbstractClientHttpRequest;
import org.springframework.http.client.ClientHttpRequest;
import org.springframework.http.client.ClientHttpResponse;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;

/**
 * {@link ClientHttpRequest} implementation based on OkHttp 3.x.
 * <p>
 * <p>Created via the {@link OkHttp3ClientHttpRequestFactory}.
 *
 * @author JimmyJin
 * @version Id : HttpRequestFactoryService, v 0.1 2018/4/24 11:00 JimmyJin Exp $
 */
public class OkHttp3ClientHttpRequest extends AbstractClientHttpRequest {

    /**
     * The Client.
     */
    private final OkHttpClient    client;

    /**
     * The Uri.
     */
    private final URI             uri;

    /**
     * The Method.
     */
    private final HttpMethod      method;

    /**
     * The Buffered output.
     */
    private ByteArrayOutputStream bufferedOutput = new ByteArrayOutputStream(1024);

    /**
     * Instantiates a new Ok http 3 client http request.
     *
     * @param client the client
     * @param uri    the uri
     * @param method the method
     */
    public OkHttp3ClientHttpRequest(OkHttpClient client, URI uri, HttpMethod method) {
        this.client = client;
        this.uri = uri;
        this.method = method;
    }

    /**
     * Gets body internal.
     *
     * @param headers the headers
     * @return the body internal
     * @throws IOException the io exception
     */
    @Override
    protected OutputStream getBodyInternal(HttpHeaders headers) throws IOException {
        return this.bufferedOutput;
    }

    /**
     * Execute internal client http response.
     *
     * @param headers the headers
     * @return the client http response
     * @throws IOException the io exception
     */
    @Override
    protected ClientHttpResponse executeInternal(HttpHeaders headers) throws IOException {
        byte[] bytes = this.bufferedOutput.toByteArray();
        if (headers.getContentLength() < 0) {
            headers.setContentLength(bytes.length);
        }
        ClientHttpResponse result = executeInternal(headers, bytes);
        this.bufferedOutput = null;
        return result;
    }

    /**
     * Gets method.
     *
     * @return the method
     */
    @Override
    public HttpMethod getMethod() {
        return this.method;
    }

    /**
     * Gets uri.
     *
     * @return the uri
     */
    @Override
    public URI getURI() {
        return this.uri;
    }

    /**
     * Execute internal client http response.
     *
     * @param headers the headers
     * @param content the content
     * @return the client http response
     * @throws IOException the io exception
     */
    protected ClientHttpResponse executeInternal(HttpHeaders headers, byte[] content) throws IOException {
        Request request = OkHttp3ClientHttpRequestFactory.buildRequest(headers, content, this.uri, this.method);
        return new OkHttp3ClientHttpResponse(this.client.newCall(request).execute());
    }

    @Override
    public String getMethodValue() {
        return this.method.name();
    }
}
