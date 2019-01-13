package com.viasat.wildblue.common.exception.contingency;

/**
 * Provides a service item not found contingency exception class.
 */
public class ServiceItemNotFoundException extends WildBlueContingencyException
{
    /** Serial version UID. */
    private static final long serialVersionUID = 1L;

    /** Service item reference. */
    private String m_serviceItemReference;

    /** Source system reference. */
    private String m_sourceSystemReference;

    /**
     * Constructs a new exception with null as its detail message. The cause is
     * not initialized, and may subsequently be initialized by a call to
     * Throwable.initCause(java.lang.Throwable).
     *
     * @param  sourceSystemReference  Source system reference.
     * @param  serviceItemReference   Service item reference.
     *
     * @see    java.lang.Throwable#initCause(Throwable)
     */
    public ServiceItemNotFoundException(String sourceSystemReference,
        String serviceItemReference)
    {
        super();
        init(sourceSystemReference, serviceItemReference);
    }

    /**
     * Constructs a new exception with the specified detail message. The cause
     * is not initialized, and may subsequently be initialized by a call to
     * Throwable.initCause(java.lang.Throwable).
     *
     * @param  sourceSystemReference  Source system reference.
     * @param  serviceItemReference   Service item reference.
     * @param  message                the detail message. The detail message is
     *                                saved for later retrieval by the
     *                                Throwable.getMessage() method.
     *
     * @see    java.lang.Throwable#initCause(Throwable)
     * @see    java.lang.Throwable#getMessage()
     */
    public ServiceItemNotFoundException(String sourceSystemReference,
        String serviceItemReference, String message)
    {
        super(message);
        init(sourceSystemReference, serviceItemReference);
    }

    /**
     * Constructs a new exception with the specified cause and a detail message
     * of (cause==null ? null : cause.toString()) (which typically contains the
     * class and detail message of cause). This constructor is useful for
     * exceptions that are little more than wrappers for other throwables (for
     * example, PrivilegedActionException).
     *
     * @param  sourceSystemReference  Source system reference.
     * @param  serviceItemReference   Service item reference.
     * @param  cause                  the cause (which is saved for later
     *                                retrieval by the Throwable.getCause()
     *                                method). (A null value is permitted, and
     *                                indicates that the cause is nonexistent or
     *                                unknown.)
     *
     * @see    java.lang.Throwable#getCause()
     */
    public ServiceItemNotFoundException(String sourceSystemReference,
        String serviceItemReference, Throwable cause)
    {
        super(cause);
        init(sourceSystemReference, serviceItemReference);
    }

    /**
     * Constructs a new exception with the specified detail message and cause.
     * Note that the detail message associated with cause is not automatically
     * incorporated in this exception's detail message.
     *
     * @param  sourceSystemReference  Source system reference.
     * @param  serviceItemReference   Service item reference.
     * @param  message                the detail message (which is saved for
     *                                later retrieval by the
     *                                Throwable.getMessage() method).
     * @param  cause                  the cause (which is saved for later
     *                                retrieval by the Throwable.getCause()
     *                                method). (A null value is permitted, and
     *                                indicates that the cause is nonexistent or
     *                                unknown.)
     *
     * @see    java.lang.Throwable#getMessage()
     * @see    java.lang.Throwable#getCause()
     */
    public ServiceItemNotFoundException(String sourceSystemReference,
        String serviceItemReference, String message, Throwable cause)
    {
        super(message, cause);
        init(sourceSystemReference, serviceItemReference);
    }

    /**
     * @return  the serviceItemReference
     */
    public String getServiceItemReference()
    {
        return m_serviceItemReference;
    }

    /**
     * @return  the sourceSystemReference
     */
    public String getServiceProviderReference()
    {
        return m_sourceSystemReference;
    }

    /**
     * Initialize.
     *
     * @param  sourceSystemReference  Source system reference.
     * @param  serviceItemReference   Service item reference.
     */
    private void init(String sourceSystemReference, String serviceItemReference)
    {
        m_sourceSystemReference = sourceSystemReference;
        m_serviceItemReference = serviceItemReference;
    }
}
