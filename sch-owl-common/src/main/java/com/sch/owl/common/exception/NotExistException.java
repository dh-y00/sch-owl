package com.sch.owl.common.exception;

/**
 * 不存在
 * 
 * @author Ydh
 */
public class NotExistException extends RuntimeException
{
    private static final long serialVersionUID = 8247610319171014183L;

    public NotExistException(Throwable e)
    {
        super(e.getMessage(), e);
    }

    public NotExistException(String message)
    {
        super(message);
    }

    public NotExistException(String message, Throwable throwable)
    {
        super(message, throwable);
    }
}
