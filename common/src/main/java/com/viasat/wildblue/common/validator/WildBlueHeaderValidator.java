package com.viasat.wildblue.common.validator;

import com.viasat.common.fault.AbstractValidatorTool;

import com.viasat.wildblue.common.commondata.ValidationError;
import com.viasat.wildblue.common.header.WildBlueHeader;

import org.apache.cxf.common.util.StringUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;


/**
 * Validation class to validate a WildBlueHeader instance. This is not a
 * validator, in the sense that it isn't used by the ValidatorTool and
 * validation.xml It is called by the WebServiceMethodInterceptor, and is
 * specific to the old schema
 */
public class WildBlueHeaderValidator
{
    private static Logger LOGGER = LoggerFactory.getLogger(
            WildBlueHeaderValidator.class);

    private ValidatorTool m_validatorTool;

    public WildBlueHeaderValidator()
    {
        m_validatorTool = ValidatorTool.getInstance();
    }

    /**
     * Perform validation on the given WildBlueHeader instance.
     */
    public List<ValidationError> validate(WildBlueHeader header)
    {
        ValidationBean validationBean = new ValidationBean(AbstractValidatorTool
                .getFormNameForObjectClass(WildBlueHeader.class));

        if (header != null)
        {
            validationBean.setField("invokedBy", header.getInvokedBy());
        }

        List<ValidationError> errors = m_validatorTool.perform(validationBean);

        if (LOGGER.isInfoEnabled())
        {
            LOGGER.info("WildBlueHeader ValidationError(s)="
                + (((errors != null) && !errors.isEmpty())
                    ? getErrorReason(errors) : null));
        }

        return errors;
    }

    private static String getErrorReason(List<ValidationError> errors)
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

        return reason.toString();
    }
}
