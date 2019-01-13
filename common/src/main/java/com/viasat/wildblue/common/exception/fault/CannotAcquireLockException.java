package com.viasat.wildblue.common.exception.fault;

import com.viasat.wildblue.common.exception.ExceptionDetail;
import com.viasat.wildblue.common.exception.HasExceptionDetail;

import org.springframework.dao.DataAccessException;

import java.util.Date;


/**
 * Custom data access exception that represents db lock problems
 */
public class CannotAcquireLockException extends DataAccessException
    implements HasExceptionDetail, FaultException
{
    /** serial Version UID */
    private static final long serialVersionUID = 1L;
    private static final String DETAIL =
        "This exception indicates a problem with locking. "
        + "When you update the database, someone else might have a lock on the "
        + "row you are trying to update. If you have configured your database "
        + "access to not wait for blocking locks or you executed a \"SELECT .. "
        + "FROM... FOR UPDATE NOWAIT\" statement, this exception may be thrown";
    private ExceptionDetail m_exceptionDetail = new ExceptionDetail();

    /**
     * @param  msg
     * @param  cause
     */
    public CannotAcquireLockException(String msg, Throwable cause)
    {
        super(msg, cause);
        m_exceptionDetail.setCode(this.getClass().getSimpleName());
        m_exceptionDetail.setReason(cause.getMessage());
        m_exceptionDetail.setDetail(DETAIL);
        m_exceptionDetail.setTimestamp(new Date());
    }

    @Override public ExceptionDetail getExceptionDetail()
    {
        return m_exceptionDetail;
    }
}
