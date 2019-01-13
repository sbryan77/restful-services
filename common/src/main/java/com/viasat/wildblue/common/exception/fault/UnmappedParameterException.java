package com.viasat.wildblue.common.exception.fault;

import com.viasat.wildblue.common.exception.ExceptionDetail;
import com.viasat.wildblue.common.exception.HasExceptionDetail;

import org.springframework.dao.DataAccessException;

import java.util.Date;


/**
 * Custom data access exception that represents when a sql statment is expecting
 * more parameters than were passed in
 */
public class UnmappedParameterException extends DataAccessException
    implements HasExceptionDetail, FaultException
{
    /** Generated serial Version UID */
    private static final long serialVersionUID = 7101250598467726452L;
    private static final String DETAIL =
        "This exception occurs when a stored procedure or function is expecting"
        + " more parameters than were actually passed in";
    private ExceptionDetail m_exceptionDetail = new ExceptionDetail();

    /**
     * @param  msg
     * @param  cause
     */
    public UnmappedParameterException(String msg, Throwable cause)
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
