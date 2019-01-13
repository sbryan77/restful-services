package com.viasat.wildblue.common.exception.fault;

import com.viasat.wildblue.common.exception.ExceptionDetail;
import com.viasat.wildblue.common.exception.HasExceptionDetail;

import org.springframework.dao.DataAccessException;

import java.util.Date;


/**
 * Custom data access exception that represents bad sql grammar
 */
public class BadSqlGrammarException extends DataAccessException
    implements HasExceptionDetail, FaultException
{
    /** serial Version UID */
    private static final long serialVersionUID = 1L;
    private static final String DETAIL =
        "This exception indicates a problem with the SQL such as an "
        + "invalid table or view name.";
    private ExceptionDetail m_exceptionDetail = new ExceptionDetail();

    /**
     * @param  msg
     * @param  cause
     */
    public BadSqlGrammarException(String msg, Throwable cause)
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
