/**
 * LY.com Inc.
 * Copyright (c) 2004-2020 All Rights Reserved.
 */
package top.kexcellent.back.code.handlers.order.handle.pc;

import org.springframework.stereotype.Service;
import top.kexcellent.back.code.handlers.HandleMessage;
import top.kexcellent.back.code.handlers.IHandler;
import top.kexcellent.back.code.handlers.IHandlerProcess;

import java.util.function.Function;

/**
 * @author kanglele01
 * @version $Id: PCTransformHandle, v 0.1 2020/7/2 17:09 kanglele01 Exp $
 */
@Service("pcTransformHandle")
public class PCTransformHandle implements IHandler, IHandlerProcess {

    @Override
    public <T, E> HandleMessage<T, E> operation(Function handler) throws Exception {
        System.out.println("pc"+handler.apply("是我就是我"));
        return new HandleMessage();
    }

    @Override
    public <T, E> void exec(HandleMessage<T, E> msg) throws Exception {
        msg = operation((x)->{
            return x+"strategy";
        });
        System.out.println("pc-Transform");
    }
}
