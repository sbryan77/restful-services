package com.viasat.wildblue.common.validator;

import com.viasat.wildblue.common.commondata.ValidationError;
import com.viasat.wildblue.common.exception.ExceptionDetail;
import com.viasat.wildblue.common.exception.WildBlueWebServiceException;

import org.apache.cxf.common.util.StringUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;


public class WebServiceValidationException extends WildBlueWebServiceException
{
    public static final String VALIDATION_ERROR = "VALIDATION_ERROR";

    @SuppressWarnings("unused")
    private static Logger LOGGER = LoggerFactory.getLogger(
            WebServiceValidationException.class);

    /** Default serialVersionUID */
    private static final long serialVersionUID = 1L;

    public WebServiceValidationException(List<ValidationError> errors)
    {
        super(VALIDATION_ERROR);

        if ((errors != null) && !errors.isEmpty())
        {
            StringBuilder reason = new StringBuilder();

            for (ValidationError validationError : errors)
            {
                if (!StringUtils.isEmpty(validationError.getErrorCode()))
                {
                    reason.append(validationError.getErrorCode()).append(":");
                }

                if (!StringUtils.isEmpty(validationError.getMessage()))
                {
                    reason.append(validationError.getMessage()).append("; ");
                }
            }

            ExceptionDetail exceptionDetail = getExceptionDetail();
            exceptionDetail.setCode(VALIDATION_ERROR);
            exceptionDetail.setDetail(null);
            exceptionDetail.setReason(reason.toString());
        }
        else
        {
            throw new IllegalArgumentException(
                "errors list must not be null or empty");
        }
    }
}
