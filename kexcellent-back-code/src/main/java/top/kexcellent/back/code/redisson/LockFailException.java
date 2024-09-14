/**
 * llkang.com Inc.
 * Copyright (c) 2010-2023 All Rights Reserved.
 */
package top.kexcellent.back.code.redisson;

/**
 * @author kanglele
 * @version $Id: LockFailException, v 0.1 2023/8/17 16:37 kanglele Exp $
 */
public class LockFailException extends Exception {
    private static final long serialVersionUID = -3816322588329303318L;

    private String message;

    public LockFailException(String message) {
        super(message);
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
