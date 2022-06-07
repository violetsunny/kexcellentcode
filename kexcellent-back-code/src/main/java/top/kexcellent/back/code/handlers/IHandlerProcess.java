/**
 * LY.com Inc.
 * Copyright (c) 2004-2020 All Rights Reserved.
 */
package top.kexcellent.back.code.handlers;

/**
 * 模块执行器
 * @author kanglele01
 * @version $Id: IHandlerProcess, v 0.1 2020/6/19 16:23 kanglele01 Exp $
 */
public interface IHandlerProcess {

    <T, E> void exec(HandleMessage<T, E> msg) throws Exception;

}
