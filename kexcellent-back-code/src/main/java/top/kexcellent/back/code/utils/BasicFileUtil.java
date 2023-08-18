/**
 * llkang.com Inc.
 * Copyright (c) 2010-2023 All Rights Reserved.
 */
package top.kexcellent.back.code.utils;

import org.apache.commons.lang3.StringUtils;

/**
 * @author kanglele
 * @version $Id: BasicFileUtil, v 0.1 2023/8/17 16:24 kanglele Exp $
 */
public class BasicFileUtil {

    public static boolean isAbsFile(String fileName) {
        if (OSUtil.isWinOS()) {
            // windows 操作系统时，绝对地址形如  c:\descktop
            return fileName.contains(":") || fileName.startsWith("\\");
        } else {
            // mac or linux
            return fileName.startsWith("/");
        }
    }

    /**
     * 将用户目录下地址~/xxx 转换为绝对地址
     *
     * @param path
     * @return
     */
    public static String parseHomeDir2AbsDir(String path) {
        String homeDir = System.getProperties().getProperty("user.home");
        return StringUtils.replace(path, "~", homeDir);
    }

    /**
     * 根据文件的mime获取文件类型
     *
     * @return
     */
    public static MediaType getMediaType(String path) {
        String magicNum = FileReadUtil.getMagicNum(path);
        if (magicNum == null) {
            return null;
        }

        return MediaType.typeOfMagicNum(magicNum);
    }

}
