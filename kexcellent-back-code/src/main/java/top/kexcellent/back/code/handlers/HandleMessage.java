/**
 * LY.com Inc.
 * Copyright (c) 2004-2016 All Rights Reserved.
 */
package top.kexcellent.back.code.handlers;

/**
 * 处理器消息载体
 * @author xy23087
 * @version $Id: Message.java, v 0.1 2016年10月25日 下午4:09:35 xy23087 Exp $
 */
public class HandleMessage<T, E> {
    /** 业务请求类 */
    private T       t;
    /** 业务返回类 */
    private E       e;
    /** 状态 */
    private boolean success;

    public T getT() {
        return t;
    }

    public void setT(T t) {
        this.t = t;
    }

    public E getE() {
        return e;
    }

    public void setE(E e) {
        this.e = e;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
