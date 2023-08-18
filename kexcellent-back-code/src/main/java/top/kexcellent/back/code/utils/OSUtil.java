/**
 * llkang.com Inc.
 * Copyright (c) 2010-2023 All Rights Reserved.
 */
package top.kexcellent.back.code.utils;

/**
 * @author kanglele
 * @version $Id: OSUtil, v 0.1 2023/8/17 16:25 kanglele Exp $
 */
public class OSUtil {

    /**
     * 是否windows系统
     */
    public static boolean isWinOS() {
        boolean isWinOS = false;
        try {
            String osName = System.getProperty("os.name").toLowerCase();
            String sharpOsName = osName.replaceAll("windows", "{windows}").replaceAll("^win([^a-z])", "{windows}$1")
                    .replaceAll("([^a-z])win([^a-z])", "$1{windows}$2");
            isWinOS = sharpOsName.contains("{windows}");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isWinOS;
    }
}
