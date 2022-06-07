/**
 * LY.com Inc.
 * Copyright (c) 2004-2020 All Rights Reserved.
 */
package top.kexcellent.back.code.handlers;

import java.util.function.Function;

/**
 * @author kanglele01
 * @version $Id: handlerContext, v 0.1 2020/7/8 16:45 kanglele01 Exp $
 */
public class HandlerContext {
    private IHandler iHandler;

    public HandlerContext(IHandler iHandler){
        this.iHandler = iHandler;
    }

    public HandleMessage doStrategy(Function handler) throws Exception {
        return iHandler.operation(handler);
    }
}
