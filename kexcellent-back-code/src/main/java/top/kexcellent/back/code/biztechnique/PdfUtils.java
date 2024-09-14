/**
 * LY.com Inc.
 * Copyright (c) 2004-2016 All Rights Reserved.
 */
package top.kexcellent.back.code.biztechnique;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorkerFontProvider;
import com.itextpdf.tool.xml.XMLWorkerHelper;

import java.io.*;
import java.net.URL;
import java.nio.charset.Charset;

/**
 * @author lfy41980
 * @version $Id: PdfUtils, v 0.1 2017/04/18 15:52 jzx41637 Exp $
 */
public class PdfUtils {

    /**
     * 新建document对象
     */
    public static Document createDocument(String path, String fileName) {
        Rectangle rectPageSize = new Rectangle(PageSize.A4);
        //新建document对象
        Document document = new Document(rectPageSize, 50, 50, 10, 50);
        return document;
    }

    /**
     * 打开文件
     *
     * @param document
     */
    public static void openDocument(Document document) {
        //打开文件
        document.open();
    }

    /**
     * 创建 PdfWriter 对象
     *
     * @throws DocumentException
     * @throws IOException
     */
    public static PdfWriter createPdfWriter(Document document, String path, String fileName) throws DocumentException, IOException {
        //判断文保存pdf的文件夹是否存在，如果不存在则新建一个
        File tempFile = new File(path);
        if (!tempFile.exists()) {
            tempFile.mkdirs();
        }
        //创建文件
        File file = new File(path + fileName);
        file.createNewFile();
        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(file));
        return writer;
    }

    /**
     * 创建字体
     *
     * @throws IOException
     * @throws DocumentException
     */
    public static Font createBaseFont() throws DocumentException, IOException {
        //字体
        BaseFont bfChinese = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
        Font font = new Font(bfChinese, 7, Font.NORMAL);
        return font;
    }

    /**
     * 创建列表标题字体
     *
     * @throws IOException
     * @throws DocumentException
     */
    public static Font createTitileFont() throws DocumentException, IOException {
        //字体
        BaseFont bfChinese = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
        Font font = new Font(bfChinese, 7, Font.BOLD);
        return font;
    }

    public static void main(String[] args) {
        try {
            String path = "D:/apache-tomcat/webapps/tmcopplatform/down_pdf/";
            String fileName = System.currentTimeMillis() + ".pdf";
            String temp = path + fileName;
            File file = new File(path);
            if (!file.exists()) {
                file.mkdirs();
            }
            File pdf = new File(temp);
        } catch (Exception e) {

            System.out.println(e);
        }

    }

    //----------------------------------------------------

    /**
     * 通过html生成文件
     *
     * @param htmlContent html格式内容
     * @param file        输出文件file
     */
    public static void createdPdfByItextHtml(String htmlContent, File file) {
        InputStream inputStream = null;
        FileOutputStream outputStream = null;
        PdfWriter writer = null;
        try {
            // 1. 获取生成pdf的html内容
            inputStream = new ByteArrayInputStream(htmlContent.getBytes("utf-8"));
            outputStream = new FileOutputStream(file);
            Document document = new Document();
            writer = PdfWriter.getInstance(document, outputStream);
            document.open();
            // 2. 添加字体
            XMLWorkerFontProvider fontImp = new XMLWorkerFontProvider(XMLWorkerFontProvider.DONTLOOKFORFONTS);
            fontImp.register(getFontPath());
            // 3. 设置编码
            XMLWorkerHelper.getInstance().parseXHtml(writer, document, inputStream, Charset.forName("UTF-8"), fontImp);
            // 4. 关闭,(不关闭则会生成无效pdf)
            document.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (writer != null) {
                    writer.close();
                }
                if (outputStream != null) {
                    outputStream.close();
                }
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    /**
     * 应用场景:
     * 1.在windows下,使用Thread.currentThread()获取路径时,出现空对象，导致不能使用
     * 2.在linux下,使用PdfUtils.class获取路径为null,
     * 获取字体路径
     *
     * @return
     */
    private static String getFontPath() {
        String path = "";
        // 1.
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        URL url = (classLoader == null) ? null : classLoader.getResource("/");
        String threadCurrentPath = (url == null) ? "" : url.getPath();
        // 2. 如果线程获取为null,则使用当前PdfUtils.class加载路径
        if (threadCurrentPath == null || threadCurrentPath.length() == 0) {
            path = PdfUtils.class.getClass().getResource("/").getPath();
        }
        // 3.拼接字体路径
        StringBuffer stringBuffer = new StringBuffer(path);
        stringBuffer.append("/fonts/SIMKAI.TTF");
        path = stringBuffer.toString();
        return path;
    }

}
