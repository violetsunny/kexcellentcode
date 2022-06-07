/**
 * LY.com Inc.
 * Copyright (c) 2004-2016 All Rights Reserved.
 */
package top.kexcellent.back.code.http;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;

/**
 * Http后处理类
 * @author jt39175
 * @version $Id: HttpHandle.java, v 0.1 2016年9月5日 下午10:50:33 jt39175 Exp $
 */
public class HttpHandle<T, E> {
    /** http业务请求类 */
    private E          request;
    /** http业务返回类 */
    private T          response;
    /** PostMethod */
    private PostMethod postMethod;
    /** HttpClient */
    private HttpClient httpClient;

    /**
     * 
     */
    public HttpHandle() {
    }

    /**
     * @param request
     * @param response
     * @param postMethod
     * @param httpClient
     */
    public HttpHandle(E request, T response, PostMethod postMethod, HttpClient httpClient) {
        super();
        this.request = request;
        this.response = response;
        this.postMethod = postMethod;
        this.httpClient = httpClient;
    }

    public E getRequest() {
        return request;
    }

    public void setRequest(E request) {
        this.request = request;
    }

    public T getResponse() {
        return response;
    }

    public void setResponse(T response) {
        this.response = response;
    }

    public PostMethod getPostMethod() {
        return postMethod;
    }

    public void setPostMethod(PostMethod postMethod) {
        this.postMethod = postMethod;
    }

    public HttpClient getHttpClient() {
        return httpClient;
    }

    public void setHttpClient(HttpClient httpClient) {
        this.httpClient = httpClient;
    }

}
