/**
 * LY.com Inc.
 * Copyright (c) 2004-2024 All Rights Reserved.
 */
package top.kexcellent.back.code.algorithm;

/**
 * 简单存储是否存在的bitmap
 * @author kanglele
 * @version $Id: BitMap, v 0.1 2024/11/12 11:02 kanglele Exp $
 */
public class BitMap {
    private byte[] bits;
    private int size;

    public BitMap(int size) {
        this.size = size;
        // 计算需要的字节数，每个字节8位
        int bytesLength = (size + 7) / 8;
        bits = new byte[bytesLength];
    }

    // 设置指定位置的位为1
    public void set(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
        int byteIndex = index / 8;
        int bitIndex = index % 8;
        // 将指定字节的指定位设为1
        bits[byteIndex] |= (1 << bitIndex);
    }

    // 检查指定位置的位是否为1
    public boolean get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
        int byteIndex = index / 8;
        int bitIndex = index % 8;
        // 检查指定字节的指定位是否为1
        return ((bits[byteIndex] & (1 << bitIndex))!= 0);
    }
}
