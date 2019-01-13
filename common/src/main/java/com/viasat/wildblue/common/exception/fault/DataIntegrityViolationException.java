package com.viasat.wildblue.common.exception.fault;

import com.viasat.wildblue.common.exception.ExceptionDetail;
import com.viasat.wildblue.common.exception.HasExceptionDetail;

import org.springframework.dao.DataAccessException;

import java.util.Date;


/**
 * Custom data access exception that represents a problem with data integrity
 */
public class DataIntegrityViolationException extends DataAccessException
    implements HasExceptionDetail, FaultException
{
    /** serial Version UID */
    private static final long serialVersionUID = 1L;
    private static final String DETAIL =
        "This exception indicates a problem with data integrity. You might have "
        + "tried to insert a record where no data was supplied for a column "
        + "that requires a value to be specified.";
    private ExceptionDetail m_exceptionDetail = new ExceptionDetail();

    /**
     * @param  msg
     * @param  cause
     */
    public DataIntegrityViolationException(String msg, Throwable cause)
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
