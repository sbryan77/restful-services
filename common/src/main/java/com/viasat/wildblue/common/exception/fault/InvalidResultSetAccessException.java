package com.viasat.wildblue.common.exception.fault;

import com.viasat.wildblue.common.exception.ExceptionDetail;
import com.viasat.wildblue.common.exception.HasExceptionDetail;

import org.springframework.dao.DataAccessException;

import java.util.Date;


/**
 * Custom data access exception that represents an error accessing a ResultSet
 */
public class InvalidResultSetAccessException extends DataAccessException
    implements HasExceptionDetail, FaultException
{
    /** serial Version UID */
    private static final long serialVersionUID = 1L;

    private static final String DETAIL = "This exception is thrown when a "
        + "ResultSet has been accessed in an invalid fashion. This typically "
        + "happens when an invalid ResultSet column index or name has been "
        + "specified. It is also thrown by disconnected SqlRowSets.";
    private ExceptionDetail m_exceptionDetail = new ExceptionDetail();

    /**
     * @param  msg
     * @param  cause
     */
    public InvalidResultSetAccessException(String msg, Throwable cause)
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
