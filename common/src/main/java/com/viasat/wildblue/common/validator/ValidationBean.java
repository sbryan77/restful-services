package com.viasat.wildblue.common.validator;

import java.util.HashMap;
import java.util.Map;


/**
 * POJO class provided as a simple object to construct and send to the
 * ValidatorTool for validation.
 */
public class ValidationBean
{
    private String formName;
    private Map<String, Object> validationFieldsMap =
        new HashMap<String, Object>();

    /**
     * @param  formName
     */
    public ValidationBean(String formName)
    {
        this.formName = formName;
    }

    /**
     * @return  The formName value.
     */
    public String getFormName()
    {
        return formName;
    }

    /**
     * @return  Map of the validation fields set.
     */
    public Map<String, Object> getValidationFieldsMap()
    {
        return validationFieldsMap;
    }

    /**
     * @param  fieldName
     * @param  fieldValue
     */
    public void setField(String fieldName, Object fieldValue)
    {
        this.validationFieldsMap.put(fieldName, fieldValue);
    }
}
