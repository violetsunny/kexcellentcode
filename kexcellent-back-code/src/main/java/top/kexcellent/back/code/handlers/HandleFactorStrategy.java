/**
 * LY.com Inc.
 * Copyright (c) 2004-2020 All Rights Reserved.
 */
package top.kexcellent.back.code.handlers;

import org.springframework.stereotype.Component;

/**
 * @author kanglele01
 * @version $Id: HandleFactorStrategy, v 0.1 2020/7/8 16:42 kanglele01 Exp $
 */
@Component
public class HandleFactorStrategy extends HandleFactor {
    @Override
    public IHandler createHandler(Class t) throws Exception {
        return (IHandler)Class.forName(t.getName()).newInstance();
    }
}
