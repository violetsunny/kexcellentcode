/**
 * LY.com Inc.
 * Copyright (c) 2004-2020 All Rights Reserved.
 */
package top.kexcellent.back.code.handlers.order.handle.app;

import top.kexcellent.back.code.handlers.IHandler;
import top.kexcellent.back.code.handlers.IHandlerProcess;
import org.springframework.stereotype.Service;
import top.kexcellent.back.code.handlers.HandleMessage;

import java.util.function.Function;

/**
 * @author kanglele01
 * @version $Id: APPTransformHandle, v 0.1 2020/7/8 16:36 kanglele01 Exp $
 */
@Service("appTransformHandle")
public class APPTransformHandle implements IHandler, IHandlerProcess {
    @Override
    public <T, E> HandleMessage<T, E> operation(Function handler) throws Exception {
        HandleMessage message = (HandleMessage)handler.apply(",开始操作");
        message.setT(message.getT()+",转换结束");
        message.setE(message.getE()+",转换结束");
        return message;
    }

    @Override
    public <T, E> void exec(HandleMessage<T, E> msg) throws Exception {
        HandleMessage message = operation((x)->{
            msg.setT((T)((String)msg.getT()+x+",转换入参"));
            msg.setE((E)((String)msg.getE()+x+",转换返回"));
            return msg;
        });

        System.out.println(msg.getE());
        System.out.println(msg.equals(message));//true
        System.out.println(msg.toString().equals(message.toString()));//true
    }
}
