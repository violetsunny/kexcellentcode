/**
 * OYO.com Inc.
 * Copyright (c) 2017-2019 All Rights Reserved.
 */
package top.kexcellent.back.code.service;

/**
 * @author kanglele
 * @version $Id: AbstractErrorFactory, v 0.1 2019-02-19 15:27 oyo Exp $
 */
public abstract class AbstractErrorFactory extends Error {

    abstract String provideErrorBundleName();

    public static Error createError(String code,String msg){
        return new Error(code+msg);
    }
}
