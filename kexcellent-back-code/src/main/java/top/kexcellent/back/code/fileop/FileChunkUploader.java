/**
 * llkang.com Inc.
 * Copyright (c) 2010-2023 All Rights Reserved.
 */
package top.kexcellent.back.code.fileop;

import java.io.*;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;

/**
 * @author kanglele
 * @version $Id: FileChunkUploader, v 0.1 2023/7/14 11:10 kanglele Exp $
 */
public class FileChunkUploader {

    public static void main(String[] args) throws Exception {
        File file = new File("path/to/your/file.ext");
        int chunkSize = 1024 * 1024; // 1 MB
        long fileSize = file.length();
        int numberOfChunks = (int) Math.ceil((double) fileSize / chunkSize);

        try (RandomAccessFile raf = new RandomAccessFile(file, "r")) {
            for (int i = 0; i < numberOfChunks; i++) {
                long offset = i * chunkSize;
                int bufferSize = (int) Math.min(chunkSize, fileSize - offset);
                byte[] buffer = new byte[bufferSize];

                raf.seek(offset);
                raf.read(buffer);

                // Upload the chunk (buffer) to your server or cloud storage
                // ...
            }
        }
    }


    public void upload(File SrcFile, File DesFile) throws Exception {
        RandomAccessFile randomAccessFile = null;
        try {
            // 可读写
            randomAccessFile = new RandomAccessFile(new File("d:\\new.txt"), "rw");

            // 写
            for (int i = 1; i <= 10; i++) {
                randomAccessFile.write((i + " 设备名/devicename,设备数量/devicenum\n\r").getBytes());
                System.out.println("当前指针位置：" + randomAccessFile.getFilePointer());
            }

//            randomAccessFile.writeBoolean(true);
//            randomAccessFile.writeByte(11);
//            randomAccessFile.writeDouble(12);
//            randomAccessFile.writeUTF("操作");

            // 读
            randomAccessFile.seek(0);// 读时指针重新置为开始位置,事实上可以从文件内容的任何位置开始
            byte[] bs = new byte[1024];
            int len = 0;
            while ((len = randomAccessFile.read(bs)) != -1) {
                System.out.println(new String(bs, 0, len));
            }
//            System.out.println("readLine:"+ randomAccessFile.readLine());
//            System.out.println("readDouble:"+randomAccessFile.readDouble());
//            System.out.println("readByte:"+randomAccessFile.readByte());
//            System.out.println("readUTF:"+randomAccessFile.readUTF());

            // RandomAccessFile的记录指针放在文件尾部，用于追加内容
            randomAccessFile.seek(randomAccessFile.length());// 指针移到文件末尾
            randomAccessFile.write((21 + " 设备名/devicename,设备数量/devicenum\n\r").getBytes());

            // 任意位置插入写
            int position = 102;
            String insetstr = "----------------------";
            randomAccessFile.seek(position);// 指定插入的位置
            // 先把该位置后的所有内容先缓存起来，防止被覆盖
            List<byte[]> blists = new ArrayList<>();
            byte[] bs1 = new byte[1024];
            while (randomAccessFile.read(bs1) != -1) {
                blists.add(bs1);
            }
            randomAccessFile.seek(position);// 再次返回指定位置
            // 插入要插入的内容
            randomAccessFile.write(insetstr.getBytes());
            // 再把缓存的内容写入
            for (int i = 0; i < blists.size(); i++) {
                byte[] cachestr = blists.get(i);
                randomAccessFile.write(cachestr);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (randomAccessFile != null) {
                try {
                    randomAccessFile.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }

    }

    public void uploadM(File SrcFile, File DesFile) throws Exception {
        RandomAccessFile rafi = new RandomAccessFile(SrcFile, "r");

        RandomAccessFile rafo = new RandomAccessFile(DesFile, "rw");

        FileChannel fci = rafi.getChannel();

        FileChannel fco = rafo.getChannel();

        long size = fci.size();

        MappedByteBuffer mbbi = fci.map(FileChannel.MapMode.READ_ONLY, 0, size);

        MappedByteBuffer mbbo = fco.map(FileChannel.MapMode.READ_WRITE, 0, size);

        long start = System.currentTimeMillis();

        for (int i = 0; i < size; i++) {
            mbbo.put(mbbi.get());
        }

        fci.close();

        fco.close();

        rafi.close();

        rafo.close();

        System.out.println("Spend: " + (double) (System.currentTimeMillis() - start) / 1000 + "s");
    }

    public static void copyFileWithMappedByteBuffer(String src, String dest) throws IOException {
        try (
                RandomAccessFile readRaf = new RandomAccessFile(src, "r");
                RandomAccessFile writeRaf = new RandomAccessFile(dest, "rw");
                FileChannel readChannel = readRaf.getChannel();
                FileChannel writeChannel = writeRaf.getChannel()) {
            long fileSize = readChannel.size();
            long position = 0;
            int bufferSize = 8 * 1024 * 1024; // 8MB buffer size

            while (position < fileSize) {
                long remaining = fileSize - position;
                // Map at most bufferSize bytes, or less if it's the end of the file
                long transferSize = Math.min(bufferSize, remaining);

                // map the buffer from readChannel to writeChannel
                MappedByteBuffer readBuffer = readChannel.map(FileChannel.MapMode.READ_ONLY, position, transferSize);
                MappedByteBuffer writeBuffer = writeChannel.map(FileChannel.MapMode.READ_WRITE, position, transferSize);

                // copy the content from readBuffer to writeBuffer
                writeBuffer.put(readBuffer);

                // update the position to the next block
                position += transferSize;
            }
        }
    }

    public void downLoad(long start, File src1, File desc1, long total) {
        try {
            // 创建输入流关联源,因为要指定位置读和写,所以我们需要用随机访问流
            RandomAccessFile src = new RandomAccessFile(src1, "rw");
            RandomAccessFile desc = new RandomAccessFile(desc1, "rw");
            // 源和目的都要从start开始
            src.seek(start);
            desc.seek(start);
            // 开始读写
            byte[] arr = new byte[1024];
            int len;
            long count = 0;
            while ((len = src.read(arr)) != -1) {
                //分三种情况
                if (len + count > total) {
                    //1.当读取的时候操作自己该线程的下载总量的时候,需要改变len
                    len = (int) (total - count);
                    desc.write(arr, 0, len);
                    //证明该线程下载任务已经完毕,结束读写操作
                    break;
                } else if (len + count < total) {
                    //2.证明还没有到下载总量,直接将内容写入
                    desc.write(arr, 0, len);
                    //并且使计数器任务累加
                    count += arr.length;
                } else {
                    //3.证明改好到下载总量
                    desc.write(arr, 0, len);
                    //结束读写
                    break;
                }
            }
            src.close();
            desc.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static class BufferedRandomAccessFile extends RandomAccessFile {

        private long bufstartpos;

        private long bufendpos;

        private long curpos;

        private byte[] buf = new byte[1024];

        private boolean bufdirty;

        private int bufusedsize;

        private long fileendpos;

        private long bufmask;

        private long bufsize;

        private long initfilelen;

        public BufferedRandomAccessFile(String name, String mode) throws FileNotFoundException {
            super(name, mode);
        }

        //  byte read(long pos)：读取当前文件POS位置所在的字节
        //  bufstartpos、bufendpos代表BUF映射在当前文件的首/尾偏移地址。
        //  curpos指当前类文件指针的偏移地址。
        public byte read(long pos) throws IOException {

            if (pos < this.bufstartpos || pos > this.bufendpos) {

                this.flushbuf();

                this.seek(pos);

                if ((pos < this.bufstartpos) || (pos > this.bufendpos))

                    throw new IOException();

            }

            this.curpos = pos;

            return this.buf[(int) (pos - this.bufstartpos)];

        }

        // void flushbuf()：bufdirty为真，把buf[]中尚未写入磁盘的数据，写入磁盘。
        private void flushbuf() throws IOException {

            if (this.bufdirty == true) {

                if (super.getFilePointer() != this.bufstartpos) {

                    super.seek(this.bufstartpos);

                }

                super.write(this.buf, 0, this.bufusedsize);

                this.bufdirty = false;

            }

        }

        // void seek(long pos)：移动文件指针到pos位置，并把buf[]映射填充至POS所在的文件块。
        public void seek(long pos) throws IOException {

            if ((pos < this.bufstartpos) || (pos > this.bufendpos)) { // seek pos not in buf

                this.flushbuf();

                if ((pos >= 0) && (pos <= this.fileendpos) && (this.fileendpos != 0)) {   // seek pos in file (file length > 0)
                    this.bufmask = -((long) this.bufsize);
                    this.bufstartpos = pos & this.bufmask; // this.bufmask = ~((long)this.bufsize - 1);

                    this.bufusedsize = this.fillbuf();

                } else if (((pos == 0) && (this.fileendpos == 0)) || (pos == this.fileendpos + 1)) {   // seek pos is append pos

                    this.bufstartpos = pos;

                    this.bufusedsize = 0;

                }

                this.bufendpos = this.bufstartpos + this.bufsize - 1;

            }

            this.curpos = pos;

        }

        // int fillbuf()：根据bufstartpos，填充buf[]。
        private int fillbuf() throws IOException {

            super.seek(this.bufstartpos);

            this.bufdirty = false;

            return super.read(this.buf);

        }

        //  boolean  write(byte  bw,  long  pos)：向当前文件POS位置写入字节BW。    
        //  根据POS的不同及BUF的位置：存在修改、追加、BUF中、BUF外等情况。在逻辑判断时，把最可能出现的情况，最先判断，这样可提高速度。
        //  fileendpos：指示当前文件的尾偏移地址，主要考虑到追加因素
        public boolean write(byte bw, long pos) throws IOException {

            if ((pos >= this.bufstartpos) && (pos <= this.bufendpos)) {  //  write  pos  in  buf

                this.buf[(int) (pos - this.bufstartpos)] = bw;

                this.bufdirty = true;

                if (pos == this.fileendpos + 1) {  //  write  pos  is  append  pos

                    this.fileendpos++;

                    this.bufusedsize++;

                }

            } else {  //  write  pos  not  in  buf

                this.seek(pos);

                if ((pos >= 0) && (pos <= this.fileendpos) && (this.fileendpos != 0)) {  //  write  pos  is  modify  file

                    this.buf[(int) (pos - this.bufstartpos)] = bw;

                } else if (((pos == 0) && (this.fileendpos == 0)) || (pos == this.fileendpos + 1)) {  //  write  pos  is  append  pos

                    this.buf[0] = bw;

                    this.fileendpos++;

                    this.bufusedsize = 1;

                } else {

                    throw new IndexOutOfBoundsException();

                }

                this.bufdirty = true;

            }

            this.curpos = pos;

            return true;

        }

        public boolean append(byte bw) throws IOException {

            return this.write(bw, this.fileendpos + 1);

        }

        public boolean write(byte bw) throws IOException {

            return this.write(bw, this.curpos);

        }

        public long length() throws IOException {

            return this.max(this.fileendpos + 1, this.initfilelen);

        }

        private long max(long fileendpos, long initfilelen) {
            return Math.max(fileendpos, initfilelen);
        }

        public long getFilePointer() throws IOException {

            return this.curpos;

        }

        public void write(byte b[], int off, int len) throws IOException {

            long writeendpos = this.curpos + len - 1;

            if (writeendpos <= this.bufendpos) { // b[] in cur buf

                System.arraycopy(b, off, this.buf, (int) (this.curpos - this.bufstartpos), len);

                this.bufdirty = true;

                this.bufusedsize = (int) (writeendpos - this.bufstartpos + 1);

            } else { // b[] not in cur buf

                super.seek(this.curpos);

                super.write(b, off, len);

            }

            if (writeendpos > this.fileendpos)

                this.fileendpos = writeendpos;

            this.seek(writeendpos + 1);

        }

        public void write(byte b[]) throws IOException {

            this.write(b, 0, b.length);

        }

        public int read(byte b[], int off, int len) throws IOException {

            long readendpos = this.curpos + len - 1;

            if (readendpos <= this.bufendpos && readendpos <= this.fileendpos) { // read in buf

                System.arraycopy(this.buf, (int) (this.curpos - this.bufstartpos), b, off, len);

            } else { // read b[] size > buf[]

                if (readendpos > this.fileendpos) { // read b[] part in file

                    len = (int) (this.length() - this.curpos + 1);

                }

                super.seek(this.curpos);

                len = super.read(b, off, len);

                readendpos = this.curpos + len - 1;

            }

            this.seek(readendpos + 1);

            return len;

        }

        public int read(byte b[]) throws IOException {

            return this.read(b, 0, b.length);

        }

        public void setLength(long newLength) throws IOException {

            if (newLength > 0) {

                this.fileendpos = newLength - 1;

            } else {

                this.fileendpos = 0;

            }

            super.setLength(newLength);

        }


        public void close() throws IOException {

            this.flushbuf();

            super.close();

        }


    }
}
