/**
 * llkang.com Inc.
 * Copyright (c) 2010-2023 All Rights Reserved.
 */
package top.kexcellent.back.code.fileop;

/**
 * @author kanglele
 * @version $Id: SplitFileUtils, v 0.1 2023/7/14 11:18 kanglele Exp $
 * <p>
 * 面向对象思想封装 分割文件并合并
 * 1.根据数据源 src,输出文件夹 destDir,分割后文件存储路径 destPaths,块大小 blockSize，size 块数,初始化分割文件对象
 * 2. 构造方法(数据源,输出源)  构造方法(数据源,输出源,分割块大小)
 * 3. 初始化文件分割对象时,调用init() 计算切分块数,切分后所有文件名 ,如果保存切分后文件的目录不存在,则创建
 * 4. 调用split()方法根据文件总长度以及分割块大小blockSize ,调用splitDetails()将分割后每一块的文件输出到 destDir 下面
 * 5. 调用 merge(String destPath) 将 destPaths 每一个分割文件,声明一个缓冲输入流,然后保存到 Vector<InputStream>中,
 * 然后使用SequenceInputStream将Vector<InputStream>合并到一个输入流中,
 * 最后使用destPath,创建一个缓冲输出流,将合并输入流读取字节,全部写入输出流中
 */

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

/**
 * @ClassName 面向对象思想封装 分割文件并合并
 * @Date 2019/12/22 19:56
 * @Version v1.0
 */
public class SplitFileUtils {
    private File src;//输入源
    private String destDir;//分割子文件输出的目录
    private List<String> destPaths;//保存每个分割子文件的路径
    private int blockSize;//切割大小
    private int size;//切割总块数

    public SplitFileUtils(String srcDir, String destDir) {
        this(srcDir, destDir, 1024);
    }

    public SplitFileUtils(String srcDir, String destDir, int blockSize) {
        this.src = new File(srcDir);//初始化输入源
        this.destDir = destDir;//初始化分割子文件输出的目录
        this.destPaths = new ArrayList<>();//初始化保存分割子文件的路径容器
        this.blockSize = blockSize;//初始化切割大小

        //初始化对象参数
        init();
        System.out.println("初始化SplitFileUtils完成");
    }

    /**
     * 初始化SplitFileUtils 参数
     */
    private void init() {
        long len = this.src.length();//文件总长度

        this.size = (int) Math.ceil(len * 1.0 / this.blockSize);// 四舍五入计算分割总总次数

        //根据size循环保存 切分子文件路径
        for (int i = 0; i < size; i++) {
            this.destPaths.add(this.destDir + File.separator + i + "-" + this.src.getName());
        }

        // 如果保存切分子文件目录不存在
        if (len > 0) {
            File destFile = new File(this.destDir);
            if (!destFile.exists()) {
                destFile.mkdirs();
            }
        }
    }

    public void split() {
        //总长度
        long len = src.length();

        //起始位置和实际大小
        int beginPos = 0;
        int actualSize = (int) (blockSize > len ? len : blockSize);

        for (int i = 0; i < size; i++) {
            beginPos = i * blockSize;
            if (i == size - 1) { //最后一块
                actualSize = (int) len;
            } else {
                actualSize = blockSize;
                len -= actualSize; //剩余量
            }

            splitDetail(i, beginPos, actualSize);
        }
        System.out.println("子文件切分后保存至" + this.destDir);
    }

    /**
     * 根据 循环次数,偏移量,实际读取量, 使用随机流输入模式读取文件字节,并使用随机流读写模式写入读取字节
     *
     * @param i
     * @param beginPos
     * @param actualSize
     */
    private void splitDetail(int i, int beginPos, int actualSize) {
        try (
                RandomAccessFile readRaf = new RandomAccessFile(this.src, "r");
                RandomAccessFile writeRaf = new RandomAccessFile(this.destPaths.get(i), "rw");
        ) {
            // 设置随机读取流的偏移量
            readRaf.seek(beginPos);

            // 设置缓存容器
            byte[] flush = new byte[actualSize];

            int len = -1; //接收长度
            while ((len = readRaf.read(flush)) != -1) {
                if (actualSize > len) { //获取本次读取的所有内容
                    writeRaf.write(flush, 0, len);
                    actualSize -= len;
                } else {
                    writeRaf.write(flush, 0, actualSize);
                    break;
                }
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 5. 调用 merge(String destPath) 将 destPaths 每一个分割文件,声明一个缓冲输入流,然后保存到 Vector<InputStream>中,
     * 然后使用SequenceInputStream将Vector<InputStream>合并到一个输入流中,
     * 最后使用destPath,创建一个缓冲输出流,将合并输入流读取字节,全部写入输出流中
     *
     * @param destPath
     */
    public void merge(String destPath) {

        //声明向量集合保存 InputStream
        Vector<InputStream> vis = new Vector<>();
        SequenceInputStream sis = null;
        BufferedOutputStream bos = null;
        try {
            // 将每一个切分后的子文件使用输入流 保存到 向量集合中
            for (int i = 0; i < this.destPaths.size(); i++) {
                vis.add(new BufferedInputStream(new FileInputStream(this.destPaths.get(i))));
            }

            // 将向量集合中的输入流合并成一个 合并流 SequenceInputStream
            sis = new SequenceInputStream(vis.elements());

            // 声明缓冲输出流
            bos = new BufferedOutputStream(new FileOutputStream(destPath, true));

            //拷贝
            //3、操作 (分段读取)
            byte[] flush = new byte[1024]; //缓冲容器
            int len = -1; //接收长度
            while ((len = sis.read(flush)) != -1) {
                bos.write(flush, 0, len); //分段写出
            }
            bos.flush();
            ;
            System.out.println("子文件" + this.destDir + "合并完成");
            delFileByPath(new File(this.destDir));
            System.out.println("删除子文件夹" + this.destDir + "完成");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {

            try {
                if (bos != null) {
                    bos.close();
                }
                if (sis != null) {
                    sis.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }


    /**
     * 5. 调用 merge(String destPath) 将 destPaths 每一个分割文件,声明一个缓冲输入流,然后保存到 Vector<InputStream>中,
     * 然后使用SequenceInputStream将Vector<InputStream>合并到一个输入流中,
     * 最后使用destPath,创建一个缓冲输出流,将合并输入流读取字节,全部写入输出流中
     *
     * @param destPath
     */
/*    public void merge(String destPath) {
        //输出流
        OutputStream os = new BufferedOutputStream(new FileOutputStream(destPath, true));
        Vector<InputStream> vi = new Vector<>();
        SequenceInputStream sis = null;
        //输入流
        for (int i = 0; i < destPaths.size(); i++) {
            vi.add(new BufferedInputStream(new FileInputStream(destPaths.get(i))));
        }
        sis = new SequenceInputStream(vi.elements());
        //拷贝
        //3、操作 (分段读取)
        byte[] flush = new byte[1024]; //缓冲容器
        int len = -1; //接收长度
        while ((len = sis.read(flush)) != -1) {
            os.write(flush, 0, len); //分段写出
        }
        os.flush();
        sis.close();
        os.close();
    }*/


    /**
     * @param src 递归删除文件
     */
    public void delFileByPath(File src) {
        if (null == src || !src.exists()) {
            return;
        }
        if (src.isFile()) {
            src.delete();
        }
        if (src.isDirectory()) { //文件夹
            for (File sub : src.listFiles()) {
                delFileByPath(sub);
            }
            src.delete();
        }
    }

    public void testSplitFile() {

        // 源文件
        String srcDir = "random.txt";
        // 保存子文件目录
        String destDir = "random";
        // 输出文件
        String destPath = "devRandom.txt";

        //初始化 SplitFileUtils
        SplitFileUtils splitFileUtils = new SplitFileUtils(srcDir, destDir);

        // 读取  srcDir ,切分子文件到 destDir目录 下面
        splitFileUtils.split();

        // 合并 destDir目录下的子文件并输出到  destPath 中
        splitFileUtils.merge(destPath);

    }
}
