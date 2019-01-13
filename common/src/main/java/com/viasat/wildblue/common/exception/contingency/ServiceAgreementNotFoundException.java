package com.viasat.wildblue.common.exception.contingency;

/**
 * Provides a service provider not found contingency exception class.
 */
public class ServiceAgreementNotFoundException
    extends WildBlueContingencyException
{
    /** Serial version UID. */
    private static final long serialVersionUID = 1L;

    /** Service agreement reference. */
    private String m_servicAgreementReference;

    /** Source system reference. */
    private String m_sourceSystemReference;

    /**
     * Constructs a new exception with null as its detail message. The cause is
     * not initialized, and may subsequently be initialized by a call to
     * Throwable.initCause(java.lang.Throwable).
     *
     * @param  sourceSystemReference      Source system reference.
     * @param  serviceAgreementReference  Service agreement reference.
     *
     * @see    java.lang.Throwable#initCause(Throwable)
     */
    public ServiceAgreementNotFoundException(String sourceSystemReference,
        String serviceAgreementReference)
    {
        super();
        init(sourceSystemReference, serviceAgreementReference);
    }

    /**
     * Constructs a new exception with the specified detail message. The cause
     * is not initialized, and may subsequently be initialized by a call to
     * Throwable.initCause(java.lang.Throwable).
     *
     * @param  sourceSystemReference      Source system reference.
     * @param  serviceAgreementReference  Service agreement reference.
     * @param  message                    the detail message. The detail message
     *                                    is saved for later retrieval by the
     *                                    Throwable.getMessage() method.
     *
     * @see    java.lang.Throwable#initCause(Throwable)
     * @see    java.lang.Throwable#getMessage()
     */
    public ServiceAgreementNotFoundException(String sourceSystemReference,
        String serviceAgreementReference, String message)
    {
        super(message);
        init(sourceSystemReference, serviceAgreementReference);
    }

    /**
     * Constructs a new exception with the specified cause and a detail message
     * of (cause==null ? null : cause.toString()) (which typically contains the
     * class and detail message of cause). This constructor is useful for
     * exceptions that are little more than wrappers for other throwables (for
     * example, PrivilegedActionException).
     *
     * @param  sourceSystemReference      Source system reference.
     * @param  serviceAgreementReference  Service agreement reference.
     * @param  cause                      the cause (which is saved for later
     *                                    retrieval by the Throwable.getCause()
     *                                    method). (A null value is permitted,
     *                                    and indicates that the cause is
     *                                    nonexistent or unknown.)
     *
     * @see    java.lang.Throwable#getCause()
     */
    public ServiceAgreementNotFoundException(String sourceSystemReference,
        String serviceAgreementReference, Throwable cause)
    {
        super(cause);
        init(sourceSystemReference, serviceAgreementReference);
    }

    /**
     * Constructs a new exception with the specified detail message and cause.
     * Note that the detail message associated with cause is not automatically
     * incorporated in this exception's detail message.
     *
     * @param  sourceSystemReference      Source system reference.
     * @param  serviceAgreementReference  Service agreement reference.
     * @param  message                    the detail message (which is saved for
     *                                    later retrieval by the
     *                                    Throwable.getMessage() method).
     * @param  cause                      the cause (which is saved for later
     *                                    retrieval by the Throwable.getCause()
     *                                    method). (A null value is permitted,
     *                                    and indicates that the cause is
     *                                    nonexistent or unknown.)
     *
     * @see    java.lang.Throwable#getMessage()
     * @see    java.lang.Throwable#getCause()
     */
    public ServiceAgreementNotFoundException(String sourceSystemReference,
        String serviceAgreementReference, String message, Throwable cause)
    {
        super(message, cause);
        init(sourceSystemReference, serviceAgreementReference);
    }

    /**
     * @return  the servicAgreementReference
     */
    public String getServicAgreementReference()
    {
        return m_servicAgreementReference;
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
     * @param  sourceSystemReference      Source system reference.
     * @param  serviceAgreementReference  Service agreement reference.
     */
    private void init(String sourceSystemReference,
        String serviceAgreementReference)
    {
        m_sourceSystemReference = sourceSystemReference;
        m_servicAgreementReference = serviceAgreementReference;
    }
}
