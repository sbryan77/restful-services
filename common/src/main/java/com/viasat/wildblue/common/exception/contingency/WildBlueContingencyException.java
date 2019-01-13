package com.viasat.wildblue.common.exception.contingency;

/**
 * Provides a base contingency exception class. Based upon an <a
 * href="http://www.oracle.com/technetwork/articles/entarch/effective-exceptions-092345.html">
 * article</a> by Barry Ruzek.
 *
 * <dl>
 * <dt>Contingency</dt>
 * <dd>An expected condition demanding an alternative response from a method
 * that can be expressed in terms of the method's intended purpose. The caller
 * of the method expects these kinds of conditions and has a strategy for coping
 * with them.</dd>
 *
 * <dt>Fault</dt>
 * <dd>An unplanned condition that prevents a method from achieving its intended
 * purpose that cannot be described without reference to the method's internal
 * implementation.</dd>
 * </dl>
 */
public class WildBlueContingencyException extends Exception
    implements ContingencyException
{
    /** Serial version UID. */
    private static final long serialVersionUID = 1L;

    /**
     * Constructs a new exception with null as its detail message. The cause is
     * not initialized, and may subsequently be initialized by a call to
     * Throwable.initCause(java.lang.Throwable).
     *
     * @see  java.lang.Throwable#initCause(Throwable)
     */
    public WildBlueContingencyException()
    {
        super();
    }

    /**
     * Constructs a new exception with the specified detail message. The cause
     * is not initialized, and may subsequently be initialized by a call to
     * Throwable.initCause(java.lang.Throwable).
     *
     * @param  message  the detail message. The detail message is saved for
     *                  later retrieval by the Throwable.getMessage() method.
     *
     * @see    java.lang.Throwable#initCause(Throwable)
     * @see    java.lang.Throwable#getMessage()
     */
    public WildBlueContingencyException(String message)
    {
        super(message);
    }

    /**
     * Constructs a new exception with the specified cause and a detail message
     * of (cause==null ? null : cause.toString()) (which typically contains the
     * class and detail message of cause). This constructor is useful for
     * exceptions that are little more than wrappers for other throwables (for
     * example, PrivilegedActionException).
     *
     * @param  cause  the cause (which is saved for later retrieval by the
     *                Throwable.getCause() method). (A null value is permitted,
     *                and indicates that the cause is nonexistent or unknown.)
     *
     * @see    java.lang.Throwable#getCause()
     */
    public WildBlueContingencyException(Throwable cause)
    {
        super(cause);
    }

    /**
     * Constructs a new exception with the specified detail message and cause.
     * Note that the detail message associated with cause is not automatically
     * incorporated in this exception's detail message.
     *
     * @param  message  the detail message (which is saved for later retrieval
     *                  by the Throwable.getMessage() method).
     * @param  cause    the cause (which is saved for later retrieval by the
     *                  Throwable.getCause() method). (A null value is
     *                  permitted, and indicates that the cause is nonexistent
     *                  or unknown.)
     *
     * @see    java.lang.Throwable#getMessage()
     * @see    java.lang.Throwable#getCause()
     */
    public WildBlueContingencyException(String message, Throwable cause)
    {
        super(message, cause);
    }
}
