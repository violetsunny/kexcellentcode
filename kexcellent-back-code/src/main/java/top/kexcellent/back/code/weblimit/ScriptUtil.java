/**
 * LY.com Inc.
 * Copyright (c) 2004-2018 All Rights Reserved.
 */
package top.kexcellent.back.code.weblimit;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.exception.ExceptionUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * @author kll49556
 * @version $Id: ScriptUtil, v 0.1 2018/7/24 16:19 kll49556 Exp $
 */
@Slf4j
public class ScriptUtil {

    /**
     * return lua script String
     *
     * @param path
     * @return
     */
    public static String getScript(String path) {
        StringBuilder sb = new StringBuilder();

        InputStream stream = ScriptUtil.class.getClassLoader().getResourceAsStream(path);
        BufferedReader br = new BufferedReader(new InputStreamReader(stream));
        try {

            String str = "";
            while ((str = br.readLine()) != null) {
                sb.append(str).append(System.lineSeparator());
            }

        } catch (IOException e) {
            log.error("ScriptUtil IOException:{}" + ExceptionUtils.getFullStackTrace(e));
        }
        return sb.toString();
    }
}
