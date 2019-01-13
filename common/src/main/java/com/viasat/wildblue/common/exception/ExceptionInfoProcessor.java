package com.viasat.wildblue.common.exception;

import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.ResourceBundle;


/**
 * Provides a processor for exception information.
 */
public class ExceptionInfoProcessor
{
    /** Code property name suffix. */
    private static final String CODE_SUFFIX = ".code";

    /** Reason key property name suffix. */
    private static final String REASON_KEY_SUFFIX = ".reason";

    /** Default reason key prefix. */
    private static final String DEFAULT_REASON_KEY_PREFIX = "STRING_";

    /** Map of exception classname to exception info. */
    private Map<String, ExceptionInfo> m_exceptionClassnameToExceptionInfo =
        new HashMap<String, ExceptionInfo>();

    /** Exception info data provider. */
    private ExceptionInfoProvider m_exceptionInfoProvider;

    /**
     * Construct this object with the given parameter.
     *
     * @param  exceptionInfoProvider  Exception info data provider.
     */
    public ExceptionInfoProcessor(ExceptionInfoProvider exceptionInfoProvider)
    {
        if (exceptionInfoProvider == null)
        {
            throw new IllegalArgumentException("exceptionInfoProvider == null");
        }

        m_exceptionInfoProvider = exceptionInfoProvider;
    }

    /**
     * @param   clientLocale  Client locale.
     *
     * @return  all exception infos for the given parameter.
     */
    public List<ExceptionInfo> getAllExceptionInfos(Locale clientLocale)
    {
        List<ExceptionInfo> answer = new ArrayList<ExceptionInfo>();

        Map<String, String> exceptionProperties = getExceptionMap();

        for (Entry<String, String> entry : exceptionProperties.entrySet())
        {
            String entryKey = entry.getKey();

            if (entryKey.endsWith(CODE_SUFFIX))
            {
                String exceptionClassname = entryKey.substring(0,
                        entryKey.length() - CODE_SUFFIX.length());
                ExceptionInfo exceptionInfo = getExceptionInfo(
                        exceptionClassname, clientLocale);
                answer.add(exceptionInfo);
            }
        }

        return answer;
    }

    /**
     * @param   exceptionClassname  Exception classname.
     * @param   clientLocale        Client locale.
     *
     * @return  a new exception info object for the given parameters.
     */
    public ExceptionInfo getExceptionInfo(String exceptionClassname,
        Locale clientLocale)
    {
        ExceptionInfo answer = m_exceptionClassnameToExceptionInfo.get(
                exceptionClassname);

        if (answer == null)
        {
            answer = createExceptionInfo(exceptionClassname, clientLocale);
            m_exceptionClassnameToExceptionInfo.put(exceptionClassname, answer);
        }

        return answer;
    }

    //    /**
    //     * @param   reason  Reason.
    //     * @param   detail  Detail.
    //     *
    //     * @return  a new exception detail object with the given parameters.
    //     */
    //    private ExceptionDetail createExceptionDetail(String reason, String detail)
    //    {
    //        ExceptionDetail answer = new ExceptionDetail();
    //
    //        answer.setReason(reason);
    //        answer.setDetail(detail);
    //
    //        return answer;
    //    }

    /**
     * @param   exceptionClassname  Exception classname.
     * @param   clientLocale        Client locale.
     *
     * @return  a new exception info object for the given parameters.
     */
    private ExceptionInfo createExceptionInfo(String exceptionClassname,
        Locale clientLocale)
    {
        String code = getCodeFor(exceptionClassname);
        String reasonKey = getReasonKeyFor(exceptionClassname);
        String reason = getReasonFor(reasonKey);
        boolean isCodeEmpty = StringUtils.isEmpty(code);
        boolean isReasonEmpty = StringUtils.isEmpty(reason);

        if (isCodeEmpty || isReasonEmpty)
        {
            //            String message = null;
            //
            //            if (isCodeEmpty && isReasonEmpty)
            //            {
            //                message = "Missing code and reason.";
            //            }
            //            else if (isCodeEmpty)
            //            {
            //                message = "Missing code.";
            //            }
            //            else
            //            {
            //                message = "Missing reason.";
            //            }
            //
            //            String detail = "exceptionClassname = " + exceptionClassname
            //                + ", reasonKey = " + reasonKey;
            //            ExceptionDetail exceptionDetail = createExceptionDetail(message,
            //                    detail);
            //            throw new WildBlueFaultException(exceptionDetail,
            //                exceptionClassname);
            return null;
        }

        return createExceptionInfo(exceptionClassname, code, reason);
    }

    /**
     * @param   exceptionClassname  Exception classname.
     * @param   code                Code.
     * @param   reason              Reason.
     *
     * @return  a new exception info object with the given parameters.
     */
    private ExceptionInfo createExceptionInfo(String exceptionClassname,
        String code, String reason)
    {
        ExceptionInfo answer = new ExceptionInfo();

        answer.setExceptionClassname(exceptionClassname);
        answer.setCode(code);
        answer.setReason(reason);

        return answer;
    }

    /**
     * @param   exceptionClassname  Exception classname.
     *
     * @return  the code for the given parameter.
     */
    private String getCodeFor(String exceptionClassname)
    {
        Map<String, String> exceptionProperties = getExceptionMap();

        return exceptionProperties.get(exceptionClassname + CODE_SUFFIX);
    }

    /**
     * @return  the exception map.
     */
    private Map<String, String> getExceptionMap()
    {
        return m_exceptionInfoProvider.getExceptionMap();
    }

    /**
     * @param   reasonKey  Reason key.
     *
     * @return  the reason for the given parameter.
     */
    private String getReasonFor(String reasonKey)
    {
        String answer = null;

        if (!StringUtils.isEmpty(reasonKey))
        {
            answer = getResourceBundle().getString(reasonKey);
        }

        return answer;
    }

    /**
     * @param   exceptionClassname  Exception classname.
     *
     * @return  the reason key for the given parameter.
     */
    private String getReasonKeyFor(String exceptionClassname)
    {
        Map<String, String> exceptionProperties = getExceptionMap();
        String answer = exceptionProperties.get(exceptionClassname
                + REASON_KEY_SUFFIX);
        boolean isReasonKeyEmpty = StringUtils.isEmpty(answer);

        if (isReasonKeyEmpty)
        {
            String code = getCodeFor(exceptionClassname);
            boolean isCodeEmpty = StringUtils.isEmpty(code);

            if (!isCodeEmpty)
            {
                answer = DEFAULT_REASON_KEY_PREFIX + code;
            }
        }

        return answer;
    }

    /**
     * @return  the resource bundle.
     */
    private ResourceBundle getResourceBundle()
    {
        return m_exceptionInfoProvider.getResourceBundle();
    }
}
