package com.viasat.wildblue.common.logging;

import org.apache.commons.lang.StringUtils;

import org.apache.log4j.AppenderSkeleton;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.apache.log4j.spi.LoggerRepository;
import org.apache.log4j.xml.DOMConfigurator;

import java.io.IOException;
import java.io.PrintWriter;

import java.net.MalformedURLException;
import java.net.URL;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class Log4jConfigServlet extends HttpServlet
{
    /** Serial version UID. */
    private static final long serialVersionUID = 1L;

    /** List of recognized levels. */
    private static final List<String> LEVELS = new ArrayList<String>();

    /** XML content type. */
    public static final String CONTENT_TYPE_XML = "text/xml";

    static
    {
        // OFF, FATAL, ERROR, WARN, INFO, DEBUG and ALL
        LEVELS.add(Level.OFF.toString());
        LEVELS.add(Level.FATAL.toString());
        LEVELS.add(Level.ERROR.toString());
        LEVELS.add(Level.WARN.toString());
        LEVELS.add(Level.INFO.toString());
        LEVELS.add(Level.DEBUG.toString());
        LEVELS.add(Level.ALL.toString());
        //LEVELS.add( Level.TRACE.toString());
    }

    /** Init-param name for the default Log4j configuration file. */
    private static final String CONFIGURATION_INIT_PARAM_NAME =
        "log4j-init-file";

    /**
     * Handle get requests.
     *
     * @param   request
     * @param   response
     *
     * @throws  ServletException
     * @throws  IOException
     */
    @Override public void doGet(HttpServletRequest request,
        HttpServletResponse response) throws ServletException, IOException
    {
        doPost(request, response);
    }

    /**
     * Handle post requests.
     *
     * @param   request
     * @param   response
     *
     * @throws  ServletException
     * @throws  IOException
     */
    @Override public void doPost(HttpServletRequest request,
        HttpServletResponse response) throws ServletException, IOException
    {
        String level = request.getParameter("level");

        if (!StringUtils.isEmpty(level) && LEVELS.contains(level))
        {
            adjustLoggingLevelTo(response, level);
        }
        else
        {
            writeResponse(response,
                "<log4j>Unrecognized log level " + level + "</log4j>",
                CONTENT_TYPE_XML);
        }
    }

    /**
     * Initialize.
     *
     * @throws  ServletException
     */
    @Override public void init() throws ServletException
    {
        log("Log4jInitServlet.init()");

        try
        {
            setDefaultConfiguration(null);
        }
        catch (IOException e)
        {
            throw new ServletException(e);
        }
    }

    /**
     * Set Log4j to the default configuration.
     *
     * @param   response
     *
     * @throws  ServletException
     * @throws  IOException
     */
    public void setDefaultConfiguration(HttpServletResponse response)
        throws ServletException, IOException
    {
        String file = getInitParameter(CONFIGURATION_INIT_PARAM_NAME);
        log("file = " + file);

        if (file != null)
        {
            try
            {
                URL url = getServletContext().getResource(file);

                log("url=" + url);

                if (file.endsWith("xml") || file.endsWith("XML"))
                {
                    DOMConfigurator.configure(url);
                }
                else
                {
                    PropertyConfigurator.configure(url);
                }
            }
            catch (MalformedURLException e)
            {
                log(e.getMessage(), e);
                throw new ServletException(e);
            }
            catch (Throwable e)
            {
                log(e.getMessage(), e);
                throw new ServletException(e);
            }
        }
    }

    /**
     * Adjust the logging level of all Loggers and Appenders to the given value.
     *
     * @param   response  HTTP response.
     * @param   levelStr  Level string.
     *
     * @throws  IOException
     */
    @SuppressWarnings("unchecked")
    protected void adjustLoggingLevelTo(HttpServletResponse response,
        String levelStr) throws IOException
    {
        log("levelStr = " + levelStr);

        Level level = Level.toLevel(levelStr);
        log("Setting all loggers to " + level);

        Logger rootLogger = Logger.getRootLogger();
        LoggerRepository loggerRepository = rootLogger.getLoggerRepository();
        Enumeration<Logger> loggers = loggerRepository.getCurrentLoggers();

        while (loggers.hasMoreElements())
        {
            Logger logger = loggers.nextElement();
            logger.setLevel(level);

            Enumeration<AppenderSkeleton> appenders = logger.getAllAppenders();

            while (appenders.hasMoreElements())
            {
                AppenderSkeleton appender = appenders.nextElement();
                appender.setThreshold(level);
            }
        }

        writeResponse(response,
            "<log4j>Reconfigured to level " + level + "</log4j>",
            CONTENT_TYPE_XML);
    }

    /**
     * Write the response.
     *
     * @param   response        HTTP response.
     * @param   responseString  Response XML string.
     * @param   contentType     Response content type.
     *
     * @throws  IOException
     */
    protected void writeResponse(HttpServletResponse response,
        String responseString, String contentType) throws IOException
    {
        // Log the response.
        log(responseString);

        // Set the content type.
        response.setContentType(contentType);

        PrintWriter writer = response.getWriter();
        writer.println(responseString);
    }
}
