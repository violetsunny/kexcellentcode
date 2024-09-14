/**
 * LY.com Inc.
 * Copyright (c) 2004-2020 All Rights Reserved.
 */
package top.kexcellent.back.code.handlers.order.handle;

import top.kexcellent.back.code.handlers.*;
import top.kexcellent.back.code.handlers.order.handle.pc.PCAmountHandle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author kanglele01
 * @version $Id: TransformHandlerProcess, v 0.1 2020/7/2 17:09 kanglele01 Exp $
 */
@Service("amountHandlerProcess")
public class AmountHandlerProcess implements IHandlerProcess {

    @Autowired
    private HandleFactorStrategy factorStrategy;

    @Override
    public <T, E> void exec(HandleMessage<T, E> msg) throws Exception {

        IHandler appIHandler = factorStrategy.createHandler(PCAmountHandle.class);

        HandlerContext context = new HandlerContext(appIHandler);
        context.doStrategy((x)->{
            return x+"strategy";
        });

    }
}
