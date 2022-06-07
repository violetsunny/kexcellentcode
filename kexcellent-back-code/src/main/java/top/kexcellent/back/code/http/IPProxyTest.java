/**
 * OYO.com Inc.
 * Copyright (c) 2017-2019 All Rights Reserved.
 */
package top.kexcellent.back.code.http;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.InputStream;
import java.net.*;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

/**
 * @author kanglele
 * @version $Id: ProxyTest2, v 0.1 2019-02-15 14:42 oyo Exp $
 */
public class IPProxyTest {
    public static String ProxyAddr;
    public static int ProxyPort;
    public static String ProxyUser;
    public static String ProxyPasswd;

    public static InputStream httpDownloadStreamUsingProxy(String url) throws Exception {

        boolean proxied = ProxyAddr != null && !ProxyAddr.isEmpty();

        URL u = new URL(url);
        HttpURLConnection hc;
        if (proxied) {

            Proxy p = new Proxy(Proxy.Type.HTTP, InetSocketAddress.createUnresolved(ProxyAddr, ProxyPort));
            hc = (HttpURLConnection)u.openConnection(p);
            Authenticator.setDefault(new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(ProxyUser, ProxyPasswd.toCharArray());
                }
            });
        } else {

            Proxy p = new Proxy(Proxy.Type.HTTP, InetSocketAddress.createUnresolved(ProxyAddr, ProxyPort));
            hc = (HttpURLConnection)u.openConnection(p);
        }

        String p = u.getProtocol() ;
        if ("https".equals(p)) {

            TrustManager[] tm = { new X509TrustManager(){
                @Override
                public void checkClientTrusted(X509Certificate[] xcs, String string) throws CertificateException {
                }
                @Override
                public void checkServerTrusted(X509Certificate[] xcs, String string) throws CertificateException {
                }
                @Override
                public X509Certificate[] getAcceptedIssuers() {
                    return null;
                }

            }};
            SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
            sslContext.init(null, tm, new java.security.SecureRandom());
            ((sun.net.www.protocol.https.HttpsURLConnectionImpl)hc).setSSLSocketFactory(sslContext.getSocketFactory());
        } else {

            hc.setRequestProperty("Proxy-Connection", "Close");
        }

        hc.setDoOutput(true);
        hc.setDoInput(true);
        hc.setUseCaches(false);
        hc.setRequestMethod("GET");

        int sc = hc.getResponseCode();
        if (sc != 200) {
            throw new Exception("Exception Http Status Code: " + sc);
        }
        return hc.getInputStream();
    }

    public static String readStreamText(InputStream i, String charset, int bufSize) throws Exception {

        int blen = bufSize;
        int slen = 0;
        byte[] buf = new byte[blen];

        while(blen > slen) {
            int l = i.read(buf, slen, blen - slen);
            if (l <= 0 ) {
                break;
            }
            slen += l;
        }
        return new String(buf, 0, slen, charset);
    }

    public static void main(String[] args) throws Exception {


        String charset = "gb2312";
        String Url = "http://2018.ip138.com/ic.asp";

        ProxyAddr = "171.11.137.219";     // 代理地址
        ProxyPort = 57114;        // 端口
        ProxyUser = "oyoproxy";       // 平台帐号
        ProxyPasswd = "oyo123456";     // 密码


        InputStream sin = httpDownloadStreamUsingProxy(Url);

        String body = readStreamText(sin, charset, 8192);

        System.out.println(body);

    }
}
