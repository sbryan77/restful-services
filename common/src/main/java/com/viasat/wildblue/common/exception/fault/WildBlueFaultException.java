package com.viasat.wildblue.common.exception.fault;

import com.viasat.wildblue.common.exception.ExceptionDetail;
import com.viasat.wildblue.common.exception.ExceptionDetailUtilities;
import com.viasat.wildblue.common.exception.HasExceptionDetail;


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
public class WildBlueFaultException extends RuntimeException
    implements HasExceptionDetail, FaultException
{
    /** Serial version UID. */
    private static final long serialVersionUID = 1L;

    /** Exception detail. */
    private ExceptionDetail m_exceptionDetail;

    /**
     * Constructs a new runtime exception with the specified detail message. The
     * cause is not initialized, and may subsequently be initialized by a call
     * to Throwable.initCause(java.lang.Throwable).
     *
     * @param  message  the detail message. The detail message is saved for
     *                  later retrieval by the Throwable.getMessage() method.
     */
    public WildBlueFaultException(String message)
    {
        super(message);

        init(createExceptionDetail());
    }

    /**
     * Constructs a new runtime exception with the specified detail message and
     * cause. Note that the detail message associated with cause is not
     * automatically incorporated in this runtime exception's detail message.
     *
     * @param  message  the detail message (which is saved for later retrieval
     *                  by the Throwable.getMessage() method).
     * @param  cause    the cause (which is saved for later retrieval by the
     *                  Throwable.getCause() method). (A null value is
     *                  permitted, and indicates that the cause is nonexistent
     *                  or unknown.)
     *
     * @see    Throwable#getMessage()
     * @see    Throwable#getCause()
     */
    public WildBlueFaultException(String message, Throwable cause)
    {
        super(message, cause);

        init(createExceptionDetail(cause));
    }

    /**
     * Constructs a new runtime exception with the specified detail message. The
     * cause is not initialized, and may subsequently be initialized by a call
     * to Throwable.initCause(java.lang.Throwable).
     *
     * @param  exceptionDetail  Exception detail.
     * @param  message          the detail message. The detail message is saved
     *                          for later retrieval by the
     *                          Throwable.getMessage() method.
     */
    public WildBlueFaultException(ExceptionDetail exceptionDetail,
        String message)
    {
        super(message);

        init(exceptionDetail);
    }

    /**
     * Constructs a new runtime exception with the specified detail message and
     * cause. Note that the detail message associated with cause is not
     * automatically incorporated in this runtime exception's detail message.
     *
     * @param  exceptionDetail  Exception detail.
     * @param  message          the detail message (which is saved for later
     *                          retrieval by the Throwable.getMessage() method).
     * @param  cause            the cause (which is saved for later retrieval by
     *                          the Throwable.getCause() method). (A null value
     *                          is permitted, and indicates that the cause is
     *                          nonexistent or unknown.)
     *
     * @see    Throwable#getMessage()
     * @see    Throwable#getCause()
     */
    public WildBlueFaultException(ExceptionDetail exceptionDetail,
        String message, Throwable cause)
    {
        super(message, cause);

        init(exceptionDetail);
    }

    /**
     * @see  com.viasat.wildblue.common.exception.HasExceptionDetail#getExceptionDetail()
     */
    @Override public ExceptionDetail getExceptionDetail()
    {
        return m_exceptionDetail;
    }

    /**
     * @return  a new exception detail object.
     */
    private ExceptionDetail createExceptionDetail()
    {
        ExceptionDetailUtilities exceptionUtils = ExceptionDetailUtilities
            .getInstance();

        return exceptionUtils.createExceptionDetail();
    }

    /**
     * @param   cause  the cause (which is saved for later retrieval by the
     *                 Throwable.getCause() method). (A null value is permitted,
     *                 and indicates that the cause is nonexistent or unknown.)
     *
     * @return  a new exception detail object.
     */
    private ExceptionDetail createExceptionDetail(Throwable cause)
    {
        ExceptionDetailUtilities exceptionUtils = ExceptionDetailUtilities
            .getInstance();

        return exceptionUtils.createFaultExceptionDetail(null, cause, null);
    }

    /**
     * @param  exceptionDetail  the exceptionDetail to set
     */
    private void init(ExceptionDetail exceptionDetail)
    {
        m_exceptionDetail = exceptionDetail;
    }
}
