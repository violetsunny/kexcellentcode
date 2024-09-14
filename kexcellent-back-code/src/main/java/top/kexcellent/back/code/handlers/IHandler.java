/**
 * LY.com Inc.
 * Copyright (c) 2004-2016 All Rights Reserved.
 */
package top.kexcellent.back.code.handlers;


import java.util.function.Function;

/**
 * 具体处理器接口
 * @author xy23087
 * @version $Id: IProcess.java, v 0.1 2016年10月25日 下午4:04:44 xy23087 Exp $
 */
public interface IHandler {

    /**
     * 执行操作
     * @param handler
     * @param <T>
     * @param <E>
     * @return
     * @throws Exception
     */
    <T, E> HandleMessage<T, E> operation(Function handler) throws Exception;
}
