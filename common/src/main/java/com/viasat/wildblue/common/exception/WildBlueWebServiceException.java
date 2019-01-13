package com.viasat.wildblue.common.exception;

import javax.servlet.ServletContext;


/**
 * WebServiceException that correctly constructs the fault info, based on
 * construction parameters
 */
public class WildBlueWebServiceException extends WebServiceException
    implements HasExceptionDetail
{
    /** Serial version UID */
    private static final long serialVersionUID = 1574775234435046381L;

    /**
     * Constructor<BR/>
     * The ExceptionDetail may contain node, timestamp and tracking key
     */
    public WildBlueWebServiceException()
    {
        super();
    }

    /**
     * Constructor<BR/>
     * The ExceptionDetail may contain node, timestamp and tracking key
     *
     * @param  message  the detail message. The detail message is saved for
     *                  later retrieval by the Throwable.getMessage() method.
     */
    public WildBlueWebServiceException(String message)
    {
        super(message);
    }

    /**
     * Constructor<BR/>
     * The ExceptionDetail may contain node, role, timestamp and tracking key
     *
     * @param  context  Servlet context.
     */
    public WildBlueWebServiceException(ServletContext context)
    {
        super();
        setFaultInfo(ExceptionDetailUtilities.getInstance()
            .createExceptionDetail(context));
    }

    /**
     * Constructor<BR/>
     * The ExceptionDetail may contain node, role, timestamp and tracking key
     *
     * @param  message  the detail message. The detail message is saved for
     *                  later retrieval by the Throwable.getMessage() method.
     * @param  context  Servlet context.
     */
    public WildBlueWebServiceException(String message, ServletContext context)
    {
        super(message);
        setFaultInfo(ExceptionDetailUtilities.getInstance()
            .createExceptionDetail(context));
    }

    /**
     * Constructor<BR/>
     * The ExceptionDetail is constructed from the following<BR/>
     * 1. The cause's class name, and message.<BR/>
     * 2. If the cause implements HasExceptionDetail, populated fields in the
     * Cause's ExceptionDetail will override values from #1.<BR/>
     * 3. If node is still not populated, it will be retrieved from a system
     * property.<BR/>
     * 4. If timestamp and trackingkey are not populated, they will be generated
     * from the current time.<BR/>
     *
     * @param  message  the detail message. The detail message is saved for
     *                  later retrieval by the Throwable.getMessage() method.
     * @param  cause    the cause (which is saved for later retrieval by the
     *                  Throwable.getCause() method). (A null value is
     *                  permitted, and indicates that the cause is nonexistent
     *                  or unknown.)
     */
    public WildBlueWebServiceException(String message, Throwable cause)
    {
        super(message, cause);
        setFaultInfo(ExceptionDetailUtilities.getInstance()
            .createExceptionDetail(cause));
    }

    /**
     * Constructor<BR/>
     * The ExceptionDetail is constructed from the following<BR/>
     * 1. exceptionDetail values, if populated<BR/>
     * 2. If node is still not populated, it will be retrieved from a system
     * property<BR/>
     * 3. If timestamp and trackingkey are not populated, they will be generated
     * from the current time.<BR/>
     *
     * @param  message          the detail message. The detail message is saved
     *                          for later retrieval by the
     *                          Throwable.getMessage() method.
     * @param  exceptionDetail  Exception detail.
     */
    public WildBlueWebServiceException(String message,
        com.viasat.wildblue.common.exception.ExceptionDetail exceptionDetail)
    {
        super(message);
        setFaultInfo(ExceptionDetailUtilities.getInstance()
            .createExceptionDetail(exceptionDetail));
    }

    /**
     * Constructor<BR/>
     * The ExceptionDetail is constructed from the following<BR/>
     * 1. The cause's class name, and message.<BR/>
     * 2. If the cause implements HasExceptionDetail, populated fields in the
     * Cause's ExceptionDetail will override values from #1.<BR/>
     * 3. If node is still not populated, it will be retrieved from a system
     * property or context.<BR/>
     * 4. If role is still not populated, it will be retrieved from context<BR/>
     * 5. If timestamp and trackingkey are not populated, they will be generated
     * from the current time.<BR/>
     *
     * @param  message  the detail message. The detail message is saved for
     *                  later retrieval by the Throwable.getMessage() method.
     * @param  cause    the cause (which is saved for later retrieval by the
     *                  Throwable.getCause() method). (A null value is
     *                  permitted, and indicates that the cause is nonexistent
     *                  or unknown.)
     * @param  context  Servlet context.
     */
    public WildBlueWebServiceException(String message, Throwable cause,
        ServletContext context)
    {
        super(message, cause);
        setFaultInfo(ExceptionDetailUtilities.getInstance()
            .createExceptionDetail(cause, context));
    }

    /**
     * Constructor<BR/>
     * The ExceptionDetail is constructed from the following<BR/>
     * 1. exceptionDetail values, if populated<BR/>
     * 2. If node is still not populated, it will be retrieved from a system
     * property or the context's realpath.<BR/>
     * 3. If role is still not populated, it will be retrieved from the context
     * <BR/>
     * 4. If timestamp and trackingkey are not populated, they will be generated
     * from the current time.<BR/>
     *
     * @param  message          the detail message. The detail message is saved
     *                          for later retrieval by the
     *                          Throwable.getMessage() method.
     * @param  exceptionDetail  Exception detail.
     * @param  context          Servlet context.
     */
    public WildBlueWebServiceException(String message,
        com.viasat.wildblue.common.exception.ExceptionDetail exceptionDetail,
        ServletContext context)
    {
        super(message);
        setFaultInfo(ExceptionDetailUtilities.getInstance()
            .createExceptionDetail(exceptionDetail, context));
    }

    /**
     * Constructor<BR/>
     * The ExceptionDetail is constructed from the following<BR/>
     * 1. The cause's class name, and message.<BR/>
     * 2. If the cause implements HasExceptionDetail, populated fields in the
     * Cause's ExceptionDetail will override values from #1.<BR/>
     * 3. exceptionDetail values, if populated, will overwrite values from #2
     * <BR/>
     * 4. If node is still not populated, it will be retrieved from a system
     * property<BR/>
     * 5. If timestamp and trackingkey are not populated, they will be generated
     * from the current time.<BR/>
     *
     * @param  message          the detail message. The detail message is saved
     *                          for later retrieval by the
     *                          Throwable.getMessage() method.
     * @param  exceptionDetail  Exception detail.
     * @param  cause            the cause (which is saved for later retrieval by
     *                          the Throwable.getCause() method). (A null value
     *                          is permitted, and indicates that the cause is
     *                          nonexistent or unknown.)
     */
    public WildBlueWebServiceException(String message,
        com.viasat.wildblue.common.exception.ExceptionDetail exceptionDetail,
        Throwable cause)
    {
        super(message, cause);
        setFaultInfo(ExceptionDetailUtilities.getInstance()
            .createExceptionDetail(exceptionDetail, cause));
    }

    /**
     * Constructor<BR/>
     * The ExceptionDetail is constructed from the following<BR/>
     * 1. The cause's class name, and message.<BR/>
     * 2. If the cause implements HasExceptionDetail, populated fields in the
     * Cause's ExceptionDetail will override values from #1.<BR/>
     * 3. exceptionDetail values, if populated, will overwrite values from #2
     * <BR/>
     * 4. If node is still not populated, it will be retrieved from a system
     * property or the context's realpath.<BR/>
     * 5. If role is still not populated, it will be retrieved from a system
     * property<BR/>
     * 6. If timestamp and trackingkey are not populated, they will be generated
     * from the current time.<BR/>
     *
     * @param  message          the detail message. The detail message is saved
     *                          for later retrieval by the
     *                          Throwable.getMessage() method.
     * @param  exceptionDetail  Exception detail.
     * @param  cause            the cause (which is saved for later retrieval by
     *                          the Throwable.getCause() method). (A null value
     *                          is permitted, and indicates that the cause is
     *                          nonexistent or unknown.)
     * @param  context          Servlet context.
     */
    public WildBlueWebServiceException(String message,
        com.viasat.wildblue.common.exception.ExceptionDetail exceptionDetail,
        Throwable cause, ServletContext context)
    {
        super(message, cause);
        setFaultInfo(ExceptionDetailUtilities.getInstance()
            .createExceptionDetail(exceptionDetail, cause, context));
    }

    /**
     * @see  com.viasat.wildblue.common.exception.HasExceptionDetail#getExceptionDetail()
     */
    @Override public ExceptionDetail getExceptionDetail()
    {
        return getFaultInfo();
    }
}
