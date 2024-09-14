/**
 * LY.com Inc.
 * Copyright (c) 2004-2020 All Rights Reserved.
 */
package top.kexcellent.back.code.handlers.order;

import top.kexcellent.back.code.handlers.HandleMessage;
import top.kexcellent.back.code.handlers.IHandleChain;
import top.kexcellent.back.code.handlers.IHandlerProcess;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author kanglele01
 * @version $Id: IHandleChainOrder, v 0.1 2020/6/24 9:44 kanglele01 Exp $
 */
@Service("appOrderHandleChain")
public class IHandleChainAPPOrder implements IHandleChain {

    @Resource
    private IHandlerProcess appTransformHandle;

    @Resource
    private IHandlerProcess appAmountHandle;


    @Override
    public boolean doProcess(HandleMessage msg) throws Exception {

        appTransformHandle.exec(msg);

        appAmountHandle.exec(msg);

        return true;
    }


}
