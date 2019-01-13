package com.viasat.wildblue.common.exception;

/**
 * Provides exception information.
 */
public class ExceptionInfo
{
    /** Code. */
    private String m_code;

    /** Exception classname. */
    private String m_exceptionClassname;

    /** Reason. */
    private String m_reason;

    /**
     * @return  the code
     */
    public String getCode()
    {
        return m_code;
    }

    /**
     * @return  the exceptionClassname
     */
    public String getExceptionClassname()
    {
        return m_exceptionClassname;
    }

    /**
     * @return  the reason
     */
    public String getReason()
    {
        return m_reason;
    }

    /**
     * @param  code  the code to set
     */
    public void setCode(String code)
    {
        m_code = code;
    }

    /**
     * @param  exceptionClassname  the exceptionClassname to set
     */
    public void setExceptionClassname(String exceptionClassname)
    {
        m_exceptionClassname = exceptionClassname;
    }

    /**
     * @param  reason  the reason to set
     */
    public void setReason(String reason)
    {
        m_reason = reason;
    }
}
