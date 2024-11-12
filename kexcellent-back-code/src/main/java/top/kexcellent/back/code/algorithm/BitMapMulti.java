/**
 * LY.com Inc.
 * Copyright (c) 2004-2024 All Rights Reserved.
 */
package top.kexcellent.back.code.algorithm;

/**
 * 可以存储多种状态的bitmap
 * @author kanglele
 * @version $Id: BitMapMuiti, v 0.1 2024/11/12 11:03 kanglele Exp $
 */
public class BitMapMulti {
    private final long[] elementData;
    private final int stateBitNum;
    private long size;

    /**
     *
     * @param stateNum 多少种状态类型：开关就是2，红绿黄就是3。。。
     * @param length 初始化长度
     */
    public BitMapMulti(int stateNum, int length) {
        if (stateNum > 1 && length > 0) {
            // 计算需要多少位来表示状态
            stateBitNum = 64 - Long.numberOfLeadingZeros(stateNum - 1);
            // 计算需要多少个long来存储这些位
            long numOfOneLong = 64 / stateBitNum;
            if (length > numOfOneLong * Integer.MAX_VALUE){
                throw new RuntimeException("初始化的位图过大，无法存储！！！");
                }
            this.elementData = new long[(int) (length / numOfOneLong) + 1];
        } else {
            throw new RuntimeException("位图类的初始化传入参数值中没有负整数！！！");
        }
    }

    public boolean add(long state) {
        int index = (int) (size / (64 / stateBitNum));
        int left = (int) (size % (64 / stateBitNum));
        if (state >= 0 && state < (64 / stateBitNum) && index < elementData.length && left + 1 <= (64 / stateBitNum)) {
            elementData[index] |= (1L << (64 - stateBitNum * (left + 1)));
            size++;
            return true;
        } else {
            return false;
        }
    }

    public long find(long index) {
        if (index < 0 || index >= size) return -1;
        int arrayIndex = (int) (index / (64 / stateBitNum));
        int elementIndex = (int) (index % (64 / stateBitNum));
        return (elementData[arrayIndex] >>> (stateBitNum * elementIndex)) & ((1L << stateBitNum) - 1);
    }

    public boolean update(long index, long state) {
        if (index < 0 || index >= size) return false;
        int arrayIndex = (int) (index / (64 / stateBitNum));
        int left = (int) (index % (64 / stateBitNum));
        elementData[arrayIndex] = (elementData[arrayIndex] & ~((1L << stateBitNum) - 1)) | (state << (64 - stateBitNum * (left + 1)));
        return true;
    }

    public long getSize() {
        return size;
    }
}
