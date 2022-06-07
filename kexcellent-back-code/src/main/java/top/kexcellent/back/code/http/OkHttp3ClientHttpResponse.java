package top.kexcellent.back.code.http;

import okhttp3.Response;
import okhttp3.ResponseBody;
import org.springframework.http.HttpHeaders;
import org.springframework.http.client.AbstractClientHttpResponse;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.util.Assert;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * {@link ClientHttpResponse} implementation based on OkHttp 3.x.
 *
 * @author JimmyJin
 * @version Id : HttpRequestFactoryService, v 0.1 2018/4/24 11:00 JimmyJin Exp $
 */
public class OkHttp3ClientHttpResponse extends AbstractClientHttpResponse {

    /**
     * The Response.
     */
    private final Response       response;

    /**
     * The Headers.
     */
    private volatile HttpHeaders headers;

    /**
     * Instantiates a new Ok http 3 client http response.
     *
     * @param response the response
     */
    public OkHttp3ClientHttpResponse(Response response) {
        Assert.notNull(response, "Response must not be null");
        this.response = response;
    }

    /**
     * Gets raw status code.
     *
     * @return the raw status code
     */
    @Override
    public int getRawStatusCode() {
        return this.response.code();
    }

    /**
     * Gets status text.
     *
     * @return the status text
     */
    @Override
    public String getStatusText() {
        return this.response.message();
    }

    /**
     * Gets body.
     *
     * @return the body
     * @throws IOException the io exception
     */
    @Override
    public InputStream getBody() throws IOException {
        ResponseBody body = this.response.body();
        return (body != null ? body.byteStream() : new ByteArrayInputStream(new byte[0]));
    }

    /**
     * Gets headers.
     *
     * @return the headers
     */
    @Override
    public HttpHeaders getHeaders() {
        HttpHeaders headers = this.headers;
        if (headers == null) {
            headers = new HttpHeaders();
            for (String headerName : this.response.headers().names()) {
                for (String headerValue : this.response.headers(headerName)) {
                    headers.add(headerName, headerValue);
                }
            }
            this.headers = headers;
        }
        return headers;
    }

    /**
     * Close.
     */
    @Override
    public void close() {
        ResponseBody body = this.response.body();
        if (body != null) {
            body.close();
        }
    }

}
