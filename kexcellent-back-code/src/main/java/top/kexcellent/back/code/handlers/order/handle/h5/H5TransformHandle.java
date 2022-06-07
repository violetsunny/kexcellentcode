/**
 * LY.com Inc.
 * Copyright (c) 2004-2020 All Rights Reserved.
 */
package top.kexcellent.back.code.handlers.order.handle.h5;

import top.kexcellent.back.code.handlers.HandleMessage;
import top.kexcellent.back.code.handlers.IHandler;

import java.util.function.Function;

/**
 * @author kanglele01
 * @version $Id: APPTransformHandle, v 0.1 2020/7/8 16:36 kanglele01 Exp $
 */
public class H5TransformHandle implements IHandler {
    @Override
    public <T, E> HandleMessage<T, E> operation(Function handler) throws Exception {
        System.out.println("h5"+handler.apply("是我就是我"));
        return new HandleMessage();
    }
}
