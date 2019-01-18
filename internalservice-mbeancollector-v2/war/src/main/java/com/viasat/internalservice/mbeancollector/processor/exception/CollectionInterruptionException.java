package com.viasat.internalservice.mbeancollector.processor.exception;

public class CollectionInterruptionException extends RuntimeException
{
    public CollectionInterruptionException(String message)
    {
        super(message);
    }

    public CollectionInterruptionException(Throwable throwable)
    {
        super(throwable);
    }
}
