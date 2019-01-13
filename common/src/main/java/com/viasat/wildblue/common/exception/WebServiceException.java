package com.viasat.wildblue.common.exception;

import javax.xml.ws.WebFault;


/**
 * Common WebServiceException
 */
@WebFault(
    name = "ExceptionDetail",
    targetNamespace = "http://www.wildblue.viasat.com/XMLSchema/v3.0/Exception"
)
public class WebServiceException extends Exception
{
    /** Serial version UID */
    public static final long serialVersionUID = 20101025090759L;

    /** Fault info */
    private ExceptionDetail m_exceptionDetail;

    /**
     */
    public WebServiceException()
    {
        super();
        initExceptionDetail();
    }

    /**
     * @param  message  the detail message. The detail message is saved for
     *                  later retrieval by the Throwable.getMessage() method.
     */
    public WebServiceException(String message)
    {
        super(message);
        initExceptionDetail();
    }

    /**
     * @param  message  the detail message. The detail message is saved for
     *                  later retrieval by the Throwable.getMessage() method.
     * @param  cause    the cause (which is saved for later retrieval by the
     *                  Throwable.getCause() method). (A null value is
     *                  permitted, and indicates that the cause is nonexistent
     *                  or unknown.)
     */
    public WebServiceException(String message, Throwable cause)
    {
        super(message, cause);

        if (cause == null)
        {
            initExceptionDetail();
        }
    }

    /**
     * @param  message          the detail message. The detail message is saved
     *                          for later retrieval by the
     *                          Throwable.getMessage() method.
     * @param  exceptionDetail  Exception detail.
     */
    public WebServiceException(String message, ExceptionDetail exceptionDetail)
    {
        super(message);
        m_exceptionDetail = exceptionDetail;
    }

    /**
     * @param  message          the detail message. The detail message is saved
     *                          for later retrieval by the
     *                          Throwable.getMessage() method.
     * @param  exceptionDetail  Exception detail.
     * @param  cause            the cause (which is saved for later retrieval by
     *                          the Throwable.getCause() method). (A null value
     *                          is permitted, and indicates that the cause is
     *                          nonexistent or unknown.)
     */
    public WebServiceException(String message, ExceptionDetail exceptionDetail,
        Throwable cause)
    {
        super(message, cause);
        m_exceptionDetail = exceptionDetail;
    }

    /**
     * @return  ExceptionDetail object
     */
    public ExceptionDetail getFaultInfo()
    {
        return m_exceptionDetail;
    }

    /**
     * @see  java.lang.Throwable#toString()
     */
    @Override public String toString()
    {
        StringBuffer sb = new StringBuffer("Message = " + getMessage() + "\n");

        if (getFaultInfo() != null)
        {
            sb.append(getFaultInfo().toString());
        }

        return sb.toString();
    }

    /**
     * Setter for subclasses
     *
     * @param  exceptionDetail  Exception detail.
     */
    protected void setFaultInfo(ExceptionDetail exceptionDetail)
    {
        m_exceptionDetail = exceptionDetail;
    }

    private void initExceptionDetail()
    {
        ExceptionDetailUtilities util = ExceptionDetailUtilities.getInstance();
        m_exceptionDetail = util.createExceptionDetail();
        m_exceptionDetail.setCode("UNKNOWN_CODE");
        m_exceptionDetail.setDetail("Unhandled Exeption");
        m_exceptionDetail.setReason("Unknown Reason");
    }
}
