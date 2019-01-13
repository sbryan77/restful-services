package com.viasat.wildblue.common.exception.contingency;

/**
 * Provides an account not found contingency exception class.
 */
public class AccountNotFoundException extends WildBlueContingencyException
{
    /** Serial version UID. */
    private static final long serialVersionUID = 1L;

    /** Account reference. */
    private String m_accountReference;

    /** Source system reference. */
    private String m_sourceSystemReference;

    /**
     * Constructs a new exception with null as its detail message. The cause is
     * not initialized, and may subsequently be initialized by a call to
     * Throwable.initCause(java.lang.Throwable).
     *
     * @param  sourceSystemReference  Source system reference.
     * @param  accountReference       Account reference.
     *
     * @see    java.lang.Throwable#initCause(Throwable)
     */
    public AccountNotFoundException(String sourceSystemReference,
        String accountReference)
    {
        super();
        init(sourceSystemReference, accountReference);
    }

    /**
     * Constructs a new exception with the specified detail message. The cause
     * is not initialized, and may subsequently be initialized by a call to
     * Throwable.initCause(java.lang.Throwable).
     *
     * @param  sourceSystemReference  Source system reference.
     * @param  accountReference       Account reference.
     * @param  message                the detail message. The detail message is
     *                                saved for later retrieval by the
     *                                Throwable.getMessage() method.
     *
     * @see    java.lang.Throwable#initCause(Throwable)
     * @see    java.lang.Throwable#getMessage()
     */
    public AccountNotFoundException(String sourceSystemReference,
        String accountReference, String message)
    {
        super(message);
        init(sourceSystemReference, accountReference);
    }

    /**
     * Constructs a new exception with the specified cause and a detail message
     * of (cause==null ? null : cause.toString()) (which typically contains the
     * class and detail message of cause). This constructor is useful for
     * exceptions that are little more than wrappers for other throwables (for
     * example, PrivilegedActionException).
     *
     * @param  sourceSystemReference  Source system reference.
     * @param  accountReference       Account reference.
     * @param  cause                  the cause (which is saved for later
     *                                retrieval by the Throwable.getCause()
     *                                method). (A null value is permitted, and
     *                                indicates that the cause is nonexistent or
     *                                unknown.)
     *
     * @see    java.lang.Throwable#getCause()
     */
    public AccountNotFoundException(String sourceSystemReference,
        String accountReference, Throwable cause)
    {
        super(cause);
        init(sourceSystemReference, accountReference);
    }

    /**
     * Constructs a new exception with the specified detail message and cause.
     * Note that the detail message associated with cause is not automatically
     * incorporated in this exception's detail message.
     *
     * @param  sourceSystemReference  Source system reference.
     * @param  accountReference       Account reference.
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
    public AccountNotFoundException(String sourceSystemReference,
        String accountReference, String message, Throwable cause)
    {
        super(message, cause);
        init(sourceSystemReference, accountReference);
    }

    /**
     * @return  the accountReference
     */
    public String getAccountReference()
    {
        return m_accountReference;
    }

    @Override public String getMessage()
    {
        String message = super.getMessage();

        if (message == null)
        {
            StringBuilder sb = new StringBuilder();

            if (m_sourceSystemReference != null)
            {
                sb.append("sourceSystemReference:");
                sb.append(m_sourceSystemReference + "; ");
            }

            sb.append("accountReference:");
            sb.append(m_accountReference);
            return sb.toString();
        }
        else
        {
            return message;
        }
    }

    /**
     * @return  the sourceSystemReference
     */
    public String getSourceSystemReference()
    {
        return m_sourceSystemReference;
    }

    /**
     * Initialize.
     *
     * @param  sourceSystemReference  Source system reference.
     * @param  accountReference       Account reference.
     */
    private void init(String sourceSystemReference, String accountReference)
    {
        m_sourceSystemReference = sourceSystemReference;
        m_accountReference = accountReference;
    }
}
