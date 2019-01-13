package com.viasat.wildblue.common.exception.contingency;

/**
 * Provides a contingency exception to signal an invalid parameter.
 */
public class InvalidParameterException extends WildBlueContingencyException
{
    /** Serial version UID. */
    private static final long serialVersionUID = 1L;

    /** Flag indicating if a message was supplied in the constructor. */
    private boolean m_isMessageSupplied;

    /** Parameter name. */
    private String m_parameterName;

    /** Parameter value. */
    private Object m_parameterValue;

    /**
     * Construct this object with the given parameters.
     *
     * @param  parameterName   parameter name.
     * @param  parameterValue  offending value.
     */
    public InvalidParameterException(String parameterName,
        Object parameterValue)
    {
        super();

        init(false, parameterName, parameterValue);
    }

    /**
     * Construct this object with the given parameters.
     *
     * @param  parameterName   parameter name.
     * @param  parameterValue  offending value.
     * @param  message         the detail message (which is saved for later
     *                         retrieval by the Throwable.getMessage() method).
     */
    public InvalidParameterException(String parameterName,
        Object parameterValue, String message)
    {
        super(message);

        init(true, parameterName, parameterValue);
    }

    /**
     * Construct this object with the given parameters.
     *
     * @param  parameterName   parameter name.
     * @param  parameterValue  offending value.
     * @param  cause           the cause (which is saved for later retrieval by
     *                         the Throwable.getCause() method). (A null value
     *                         is permitted, and indicates that the cause is
     *                         nonexistent or unknown.)
     */
    public InvalidParameterException(String parameterName,
        Object parameterValue, Throwable cause)
    {
        super(cause);

        init(false, parameterName, parameterValue);
    }

    /**
     * Construct this object with the given parameters.
     *
     * @param  parameterName   parameter name.
     * @param  parameterValue  offending value.
     * @param  message         the detail message (which is saved for later
     *                         retrieval by the Throwable.getMessage() method).
     * @param  cause           the cause (which is saved for later retrieval by
     *                         the Throwable.getCause() method). (A null value
     *                         is permitted, and indicates that the cause is
     *                         nonexistent or unknown.)
     */
    public InvalidParameterException(String parameterName,
        Object parameterValue, String message, Throwable cause)
    {
        super(message, cause);

        init(true, parameterName, parameterValue);
    }

    @Override public String getMessage()
    {
        String message = super.getMessage();

        if (message == null)
        {
            StringBuilder sb = new StringBuilder();

            sb.append("parameter name = ").append(m_parameterName);
            sb.append(", parameter value = ").append(m_parameterValue);

            return sb.toString();
        }
        else
        {
            return message;
        }
    }

    /**
     * @return  the parameterName
     */
    public String getParameterName()
    {
        return m_parameterName;
    }

    /**
     * @return  the parameterValue
     */
    public Object getParameterValue()
    {
        return m_parameterValue;
    }

    /**
     * @see  java.lang.Throwable#toString()
     */
    @Override public String toString()
    {
        StringBuilder sb = new StringBuilder(getClass().getName());

        sb.append(": ");

        if (m_isMessageSupplied)
        {
            String message = getMessage();

            if ((message != null) && (message.length() > 0))
            {
                sb.append(message).append(" ");
            }
        }

        sb.append("parameter name = ").append(m_parameterName);
        sb.append(", parameter value = ").append(m_parameterValue);

        return sb.toString();
    }

    /**
     * Initialize.
     *
     * @param  isMessageSupplied  Flag indicating if a message was supplied in
     *                            the constructor.
     * @param  parameterName      parameter name.
     * @param  parameterValue     offending value.
     */
    private void init(boolean isMessageSupplied, String parameterName,
        Object parameterValue)
    {
        m_isMessageSupplied = isMessageSupplied;
        m_parameterName = parameterName;
        m_parameterValue = parameterValue;
    }
}
