package com.viasat.common.intercept;

import com.viasat.common.fault.ValidationError;
import com.viasat.common.fault.ValidatorTool;
import com.viasat.common.fault.WebServiceFault;

import com.viasat.wildblue.common.header.WildBlueHeader;
import com.viasat.wildblue.common.intercept.BaseMethodInterceptor;
import com.viasat.wildblue.common.validator.ValidationBean;
import com.viasat.wildblue.common.validator.WebServiceValidationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.BeansException;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.lang.reflect.Method;

import java.util.List;


/**
 * Interceptor for handling Facade's service method calls. For now it validates
 * {@link WildBlueHeader} and catches/handles unhandled runtime exceptions<br/>
 */
public class WebServiceMethodInterceptor extends BaseMethodInterceptor
    implements ApplicationContextAware
{
    /** The LOGGER. */
    private static final Logger LOGGER = LoggerFactory.getLogger(
            WebServiceMethodInterceptor.class);
    private ApplicationContext applicationContext = null;

    public void setApplicationContext(ApplicationContext applicationContext)
        throws BeansException
    {
        // Assign the ApplicationContext into a static variable
        this.applicationContext = applicationContext;
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
        // make sure that all exceptions that are thrown from a service method
        // are WebServiceException
        if (!(t instanceof WebServiceFault))
        {
            super.afterThrowing(new WebServiceFault(applicationContext,
                    t.getMessage()));
        }

        return super.afterThrowing(t);
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

    /**
     * @param   method  Method.
     * @param   args    Arguments.
     *
     * @throws  WebServiceValidationException  if a validation problem is found.
     */
    private void performWildBlueHeaderValidation(Method method, Object[] args)
        throws WebServiceFault
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
            // This above code assumes EVERY WS request has a WildBlueHeader
            // argument. If not, then
            // null will be sent to the WildBlueHeaderValidator, which will fail
            // validation.
            List<ValidationError> errors = validate(wbh);

            if ((errors != null) && !errors.isEmpty())
            {
                throw new WebServiceFault(applicationContext, errors);
            }
        }
    }

    private List<ValidationError> validate(WildBlueHeader header)
    {
        ValidationBean validationBean = new ValidationBean("Header");

        validationBean.setField("wildBlueHeader", header);

        List<ValidationError> errors = ValidatorTool.getInstance().perform(
                validationBean);

        if ((errors != null) && errors.isEmpty() && (header != null))
        {
            validationBean = new ValidationBean(ValidatorTool
                    .getFormNameForObjectClass(WildBlueHeader.class));
            validationBean.setField("invokedBy", header.getInvokedBy());
            errors = ValidatorTool.getInstance().perform(validationBean);

            if ((errors != null) && errors.isEmpty()
                && (header.getInvokedBy() != null))
            {
                validationBean = new ValidationBean("InvokedBy");
                validationBean.setField("username",
                    header.getInvokedBy().getUsername());
                validationBean.setField("application",
                    header.getInvokedBy().getApplication());
                errors = ValidatorTool.getInstance().perform(validationBean);
            }
        }

        return errors;
    }
}
