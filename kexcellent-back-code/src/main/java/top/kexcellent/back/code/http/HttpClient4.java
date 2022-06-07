/**
 * OYO.com Inc.
 * Copyright (c) 2017-2019 All Rights Reserved.
 */
package top.kexcellent.back.code.http;

import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.util.Locale;

/**
 * @author kanglele
 * @version $Id: HttpClient42, v 0.1 2019-02-15 14:41 oyo Exp $
 */
public class HttpClient4 {
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)  throws Exception {
        // TODO code application logic here

        // 此例子需要 httpclient4.5.3 以上版本
        String ProxyAddr = "代理ip";         // 代理地址
        int ProxyPort = 57114;               // 端口
        String ProxyUser = "账号";           // 平台帐号
        String ProxyPasswd = "密码";         // 密码

        CredentialsProvider credsProvider = new BasicCredentialsProvider();
        credsProvider.setCredentials(

                new AuthScope(ProxyAddr, ProxyPort),
                new UsernamePasswordCredentials(ProxyUser, ProxyPasswd));
        CloseableHttpClient httpclient = HttpClients.custom()
                .setDefaultCredentialsProvider(credsProvider).build();
        try {
            HttpHost target = new HttpHost("2018.ip138.com", 80);
            HttpHost proxy = new HttpHost(ProxyAddr, ProxyPort);

            RequestConfig config = RequestConfig.custom()
                    .setProxy(proxy)
                    .build();
            HttpGet httpget = new HttpGet("/ic.asp");
            httpget.setConfig(config);

            System.out.println("Executing request " + httpget.getRequestLine() + " to " + target + " via " + proxy);

            CloseableHttpResponse response = httpclient.execute(target, httpget);
            response.setLocale(Locale.CHINESE);
            try {
                System.out.println("----------------------------------------");
                System.out.println(response.getStatusLine());
                System.out.println(EntityUtils.toString(response.getEntity()));
            } finally {
                response.close();
            }
        } finally {
            httpclient.close();
        }
    }
}
