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
@Service("pcOrderHandleChain")
public class IHandleChainPCOrder implements IHandleChain {

    @Resource
    private IHandlerProcess pcTransformHandle;

    @Resource
    private IHandlerProcess pcAmountHandle;


    @Override
    public boolean doProcess(HandleMessage msg) throws Exception {

        pcTransformHandle.exec(msg);

        pcAmountHandle.exec(msg);

        return true;
    }


}
