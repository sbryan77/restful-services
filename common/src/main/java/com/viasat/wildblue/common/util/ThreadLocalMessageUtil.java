package com.viasat.wildblue.common.util;

/**
 * Utility class to manage a reference to a message received by the current
 * thread. This will allow access to the raw message throughout the processing
 * of the request. For example, this can be used to retrieve the initial
 * in-bound SOAP message for logging/tracing (i.e. XMLLog data to
 * BusinessTransactionService) from processes where the unmarshalled message is
 * not otherwise available.
 */
public class ThreadLocalMessageUtil
{
    /** Static private instance. */
    private static ThreadLocalMessageUtil instance =
        new ThreadLocalMessageUtil();

    /** The set of thread local properties. */
    private ThreadLocal<Boolean> externalValidationCall =
        new ThreadLocal<Boolean>();

    private ThreadLocal<String> facadeTransactionReference =
        new ThreadLocal<String>();
    private ThreadLocal<String> inboundMessageContent =
        new ThreadLocal<String>();
    private ThreadLocal<String> outboundMessageContent =
        new ThreadLocal<String>();
    private ThreadLocal<String> soaTransactionReference =
        new ThreadLocal<String>();

    /**
     * Pure static implementation, private constructor.
     */
    private ThreadLocalMessageUtil()
    {
    }

    /**
     * Clears all ThreadLocal values.
     */
    public static void clearAll()
    {
        setFacadeTransactionReference(null);
        setInboundMessageContent(null);
        setOutboundMessageContent(null);
        setSoaTransactionReference(null);
        setExternalValidationCall(null);
    }

    /**
     * Gets the facade transaction reference.
     *
     * @return  the facade transaction reference
     */
    public static String getFacadeTransactionReference()
    {
        return instance.facadeTransactionReference.get();
    }

    /**
     * Get the ThreadLocal message, must have been "set" or will return null.
     *
     * @return  Message content associated to the current Thread.
     */
    public static String getInboundMessageContent()
    {
        return instance.inboundMessageContent.get();
    }

    /**
     * Get the ThreadLocal message, must have been "set" or will return null.
     *
     * @return  Message content associated to the current Thread.
     */
    public static String getOutboundMessageContent()
    {
        return instance.outboundMessageContent.get();
    }

    /**
     * Gets the soa transaction reference.
     *
     * @return  the soa transaction reference
     */
    public static String getSoaTransactionReference()
    {
        return instance.soaTransactionReference.get();
    }

    /**
     * Checks if the operation is an external validation method call.
     *
     * @return  true, if the operation is an external validation method call.
     */
    public static boolean isExternalValidationCall()
    {
        Boolean isValidationOperation = instance.externalValidationCall.get();

        if (isValidationOperation != null)
        {
            return isValidationOperation;
        }
        else
        {
            return false;
        }
    }

    /**
     * Sets the externalCalidationCall boolean.
     *
     * @param  externalValidationCall  the new external validation call
     */
    public static void setExternalValidationCall(Boolean externalValidationCall)
    {
        instance.externalValidationCall.set(externalValidationCall);
    }

    /**
     * Sets the facade transaction reference.
     *
     * @param  facadeTransactionReference  the new facade transaction reference
     */
    public static void setFacadeTransactionReference(
        String facadeTransactionReference)
    {
        instance.facadeTransactionReference.set(facadeTransactionReference);
    }

    /**
     * Sets the inbound message content.
     *
     * @param  messageContent  the new inbound message content
     */
    public static void setInboundMessageContent(String messageContent)
    {
        instance.inboundMessageContent.set(messageContent);
    }

    /**
     * Sets the outbound message content.
     *
     * @param  messageContent  the new outbound message content
     */
    public static void setOutboundMessageContent(String messageContent)
    {
        instance.outboundMessageContent.set(messageContent);
    }

    /**
     * Sets the soa transaction reference.
     *
     * @param  soaTransactionReference  the new soa transaction reference
     */
    public static void setSoaTransactionReference(
        String soaTransactionReference)
    {
        instance.soaTransactionReference.set(soaTransactionReference);
    }
}
