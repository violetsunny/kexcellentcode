/**
 * LY.com Inc.
 * Copyright (c) 2004-2020 All Rights Reserved.
 */
package top.kexcellent.back.code.handlers.order.handle.app;

import org.springframework.stereotype.Service;
import top.kexcellent.back.code.handlers.HandleMessage;
import top.kexcellent.back.code.handlers.IHandler;
import top.kexcellent.back.code.handlers.IHandlerProcess;

import java.util.function.Function;

/**
 * @author kanglele01
 * @version $Id: PCTransformHandle, v 0.1 2020/7/2 17:09 kanglele01 Exp $
 */
@Service("appAmountHandle")
public class APPAmountHandle implements IHandler, IHandlerProcess {

    @Override
    public <T, E> HandleMessage<T, E> operation(Function handler) throws Exception {
        System.out.println(handler.apply(",要钱要钱"));

        return null;
    }

    @Override
    public <T, E> void exec(HandleMessage<T, E> msg) throws Exception {
        operation((x)->{
            msg.setT((T)((String)msg.getT()+x));
            msg.setE((E)(msg.getE()+",钱算好了"));
            return msg.getT();
        });

        System.out.println(msg.getE());
    }
}
