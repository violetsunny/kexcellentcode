/**
 * LY.com Inc.
 * Copyright (c) 2004-2020 All Rights Reserved.
 */
package top.kexcellent.back.code.handlers;

/**
 * @author kanglele01
 * @version $Id: HandleFactor, v 0.1 2020/7/8 16:39 kanglele01 Exp $
 */
public abstract class HandleFactor {
    abstract IHandler createHandler(Class t) throws Exception;
}
