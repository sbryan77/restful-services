package com.viasat.wildblue.common.exception;

import com.viasat.wildblue.common.exception.fault.WildBlueFaultException;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import javax.servlet.ServletContext;


/**
 * Provides functionality to construct an ExceptionDetail object. This object is
 * used to convey detailed information regarding an exception.
 */
public class ExceptionUtilities
{
    /**
     * Constructs a ExceptionDetail object from various sources.  This method is
     * used on the server side to provide detailed diagnostic information, about
     * a fault, to the client.
     *
     * @param       detail          Supporting detail for the error.
     * @param       ex              The exception that was the cause.
     * @param       servletContext  The Servlet Context
     *
     * @return      Populated ExceptionDetail object
     *
     * @deprecated  jkent Use {@link ExceptionDetailUtilities} methods, then
     *              setDetail
     */
    @Deprecated public static ExceptionDetail buildExceptionDetail(
        final String detail, final Exception ex,
        final ServletContext servletContext)
    {
        ExceptionDetail xd = ExceptionDetailUtilities.getInstance()
            .createExceptionDetail(ex, servletContext);

        // Application-specific error information related to the  codes describing the problem.
        xd.setDetail(detail);

        return xd;
    }

    /**
     * Retrieves information from the WebServiceException and re-constructs the
     * contained cause exception.  This method is used by a service client to
     * gain diagnostic information about a fault.
     *
     * @param   ex  The WebServiceException caught.
     *
     * @return  The re-constructed cause exception.
     */
    public static Exception getException(final WebServiceException ex)
    {
        Exception except = null;
        ExceptionDetail xd;
        String code;
        String detail;

        xd = ex.getFaultInfo();
        code = xd.getCode();
        detail = xd.getDetail();

        try
        {
            Class<?> clazz = Class.forName(code);

            // Re-construct a RuntimeException
            if (clazz.isInstance(new RuntimeException()))
            {
                except = new WildBlueFaultException(xd, detail);
            }
            else  // Re-construct a checked exception
            {
                // This code uses the constructor with a single String argument
                Constructor<?> constructor = clazz.getConstructor(String.class);
                except = (Exception)constructor.newInstance(detail);
            }
        }
        catch (InstantiationException e)
        {
            e.printStackTrace();
        }
        catch (IllegalAccessException e)
        {
            e.printStackTrace();
        }
        catch (ClassNotFoundException e)
        {
            e.printStackTrace();
        }
        catch (NoSuchMethodException e)
        {
            e.printStackTrace();
        }
        catch (InvocationTargetException e)
        {
            e.printStackTrace();
        }

        return except;
    }
}
