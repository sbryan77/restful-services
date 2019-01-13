package com.viasat.wildblue.common.validator;

import com.viasat.common.fault.AbstractValidatorTool;

import org.apache.commons.validator.Field;
import org.apache.commons.validator.Validator;
import org.apache.commons.validator.ValidatorResults;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;


public abstract class AbstractValidator
{
    private static final String VALIDATOR_TOOL = "validatorTool";

    public static void setValidatorTool(Validator validator,
        AbstractValidatorTool validatorTool)
    {
        validator.setParameter(VALIDATOR_TOOL, validatorTool);
    }

    /**
     * Validate nested types.
     *
     * @param  bean       the bean
     * @param  validator  the validator
     * @param  fieldName  the field name
     */
    protected void validateNestedTypes(Object bean, Validator validator,
        Field field)
    {
        boolean skipValidateNestedComplexTypes = new Boolean(field.getVarValue(
                    "skipValidateNestedComplexTypes"));

        if (!skipValidateNestedComplexTypes)
        {
            String fieldName = field.getProperty();

            Object nestedBean = AbstractValidatorTool.getPropertyValue(bean,
                    fieldName);

            if (nestedBean instanceof Collection)
            {
                if (!((Collection<?>)nestedBean).isEmpty())
                {
                    for (Object listBeanItem : ((Collection<?>)nestedBean))
                    {
                        validateNestedTypes(listBeanItem, validator);
                    }
                }
            }
            else if (!(nestedBean instanceof String))
            {
                validateNestedTypes(nestedBean, validator);
            }
        }
    }

    private void validateNestedTypes(Object nestedBean, Validator validator)
    {
        AbstractValidatorTool validatorTool = (AbstractValidatorTool)
            validator.getParameterValue(VALIDATOR_TOOL);
        String formName = AbstractValidatorTool.getFormNameForObjectClass(
                nestedBean);

        if (validatorTool.validationRulesExist(formName))
        {
            ValidatorResults nestedErrors = validatorTool.perform(nestedBean,
                    formName, validator);

            if ((nestedErrors != null) && !nestedErrors.isEmpty())
            {
                Map<Object, ValidatorResults> existingNestedErrors =
                    validatorTool.getNestedValidatorResults(validator);

                if (existingNestedErrors == null)
                {
                    existingNestedErrors =
                        new HashMap<Object, ValidatorResults>();
                }

                existingNestedErrors.put(nestedBean, nestedErrors);
                validatorTool.setNestedValidatorResults(validator,
                    existingNestedErrors);
            }
        }
    }
}
