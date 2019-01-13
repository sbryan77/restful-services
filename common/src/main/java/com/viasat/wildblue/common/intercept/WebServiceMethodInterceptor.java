package com.viasat.wildblue.common.intercept;

import com.viasat.wildblue.common.commondata.ValidationError;
import com.viasat.wildblue.common.exception.ExceptionDetailUtilities;
import com.viasat.wildblue.common.exception.WebServiceException;
import com.viasat.wildblue.common.exception.WildBlueWebServiceException;
import com.viasat.wildblue.common.header.WildBlueHeader;
import com.viasat.wildblue.common.validator.WebServiceValidationException;
import com.viasat.wildblue.common.validator.WildBlueHeaderValidator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;

import java.util.List;


/**
 * Interceptor for handling Facade's service method calls. For now it validates
 * {@link WildBlueHeader} and catches/handles unhandled runtime exceptions<br/>
 */
public class WebServiceMethodInterceptor extends BaseMethodInterceptor
{
    /** The WildBlueHeaderValidator. */
    private static WildBlueHeaderValidator wbhValidator =
        new WildBlueHeaderValidator();

    /** The LOGGER. */
    private static final Logger LOGGER = LoggerFactory.getLogger(
            WebServiceMethodInterceptor.class);

    /**
     * @param   method  Method.
     * @param   args    Arguments.
     *
     * @throws  WebServiceValidationException  if a validation problem is found.
     */
    public void performWildBlueHeaderValidation(Method method, Object[] args)
        throws WebServiceValidationException
    {
        if ((args != null) && (args.length > 0))
        {
            Class<?>[] argTypes = method.getParameterTypes();
            int wbhIndex = -1;

            for (int i = 0; i < argTypes.length; i++)
            {
                if (WildBlueHeader.class.isAssignableFrom(argTypes[i]))
                {
                    wbhIndex = i;
                    break;
                }
            }

            WildBlueHeader wbh = null;

            if (wbhIndex >= 0)
            {
                wbh = (WildBlueHeader)args[wbhIndex];
            }

            if (LOGGER.isInfoEnabled())
            {
                LOGGER.info("header=" + wbh);
            }

            // validate WildBlueHeader...
            // This above code assumes EVERY WS request has a WildBlueHeader argument. If not, then
            // null will be sent to the WildBlueHeaderValidator, which will fail validation.
            List<ValidationError> errors = wbhValidator.validate(wbh);

            if ((errors != null) && !errors.isEmpty())
            {
                throw new WebServiceValidationException(errors);
            }
        }
    }

    /**
     * After throwing.
     *
     * @param   t  the t
     *
     * @return  the throwable
     *
     * @throws  Throwable  the throwable
     *
     * @see     com.viasat.wildblue.common.intercept.BaseMethodInterceptor#afterThrowing(java.lang.Throwable)
     */
    @Override protected Throwable afterThrowing(Throwable t) throws Throwable
    {
        // make sure that all exceptions that are thrown from a service method are WebServiceException
        if (t instanceof WebServiceException)
        {
            if (t instanceof WildBlueWebServiceException)
            {
                // Moves code to message so it appears in faultString
                String code = ((WebServiceException)t).getFaultInfo().getCode();
                String msg = t.getMessage();
                WebServiceException e = new WildBlueWebServiceException(code,
                        t);
                e.getFaultInfo().setDetail(msg);

                return super.afterThrowing(e);
            }

            return super.afterThrowing(t);
        }

        LOGGER.error(t.getMessage(), t);

        WebServiceException e;
        WebServiceException e2 = null;

        try
        {
            e = new WildBlueWebServiceException(t.getMessage(), t);

            // Moves code to message so it appears in faultString
            String code = e.getFaultInfo().getCode();
            String msg = e.getMessage();
            e2 = new WildBlueWebServiceException(code, t);
            e2.getFaultInfo().setDetail(msg);
        }
        catch (Exception e1)
        {
            LOGGER.error(e1.getMessage(), e1);
            e = new WebServiceException("SYSTEM_ERROR",
                    ExceptionDetailUtilities.getInstance()
                    .createExceptionDetail());
        }

        return super.afterThrowing(e2);
    }

    /**
     * Before.
     *
     * @param   method  the method
     * @param   args    the args
     * @param   target  the target
     *
     * @return  the object
     *
     * @throws  Throwable  the Throwable
     *
     * @see     com.viasat.wildblue.common.intercept.BaseMethodInterceptor#before(java.lang.reflect.Method,
     *          java.lang.Object[], java.lang.Object)
     */
    @Override protected Object before(Method method, Object[] args,
        Object target) throws Throwable
    {
        performWildBlueHeaderValidation(method, args);
        return super.before(method, args, target);
    }

    /**
     * Get the validation method name from a method
     *
     * @param   methodName  method name
     *
     * @return  validation method name
     */
    protected final String getValidationMethodName(String methodName)
    {
        StringBuilder sb = new StringBuilder();
        sb.append("validate");
        sb.append(Character.toUpperCase(methodName.charAt(0)));
        sb.append(methodName.substring(1));
        return sb.toString();
    }

    /**
     * @param   methodName
     *
     * @return
     */
    protected final boolean isValidationMethod(String methodName)
    {
        return methodName.startsWith("validate");
    }
}
