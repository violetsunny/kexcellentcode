/**
 * LY.com Inc.
 * Copyright (c) 2004-2017 All Rights Reserved.
 */
package top.kexcellent.back.code.http;


import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 签名规则工具类
 * @author jt39175
 * @version $Id: SignRuleUtil.java, v 0.1 2017年7月10日 下午1:57:40 jt39175 Exp $
 */
public class SignRuleUtil {
    

    public static String getSignRule(String requestParam, String secret, String url, String customerSign, String requestId) throws UnsupportedEncodingException {
        // URL
        String urlCode = URLEncoder.encode(url,"utf-8").toLowerCase();
        String requestUrl = replaceBlank(urlCode);
        // TimeStamp
        Long TimeStamp = System.currentTimeMillis();
        // hmac
        String string = customerSign + requestParam + requestId + TimeStamp.toString() + requestUrl + secret;
        // 对 { CustomerSign}{RequestParam}{RequestId}{TimeStamp}{RequestUrl}{Secret} 字符串加密
        String hmac = MD5Util.encrypt(string);
        // Authorization : hmac {customerSign}:{hmac}:{requestId}:{requestTimeStamp}
        return "hmac" + " " + customerSign + ":" + hmac + ":" + requestId + ":" + TimeStamp.toString();
    }
    
    /**
     * java去除字符串中的空格、回车、换行符、制表符
     * @param str
     * @return
     */
    public static String replaceBlank(String str) {
        String dest = "";
        if (str!=null) {
            Pattern p = Pattern.compile("\\s*|\t|\r|\n");
            Matcher m = p.matcher(str);
            dest = m.replaceAll("");
        }
        return dest;
    }

}
