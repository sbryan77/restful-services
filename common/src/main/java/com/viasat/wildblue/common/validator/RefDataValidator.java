package com.viasat.wildblue.common.validator;

import org.apache.commons.validator.Field;
import org.apache.commons.validator.GenericValidator;
import org.apache.commons.validator.util.ValidatorUtils;


/**
 * Validation class to check that a value given is valid within the range of
 * values that exist for a specified reference data type.<br>
 * ENHANCEMENT OPPORTUNITY: Validate various expected values (upon which Billing
 * depends to function correctly) from the referenceData service.
 */
public class RefDataValidator
{
    /**
     * Validates that a value is valid within the range of values that exist for
     * a specified reference data type. The type of reference data must be
     * provided for the field in the xml configuration using var-name
     * "refDataType".
     *
     * @param   bean   The Object having the field/property to check.
     * @param   field  The Field object specifying which field/property to check
     *                 on the given bean.
     *
     * @return  A value of true/false indicating if the value is valid.
     */
    public boolean validateRefData(Object bean, Field field)
    {
        boolean isValid = true;

        // Assumes Sting?... maybe correct for redData
        String value = ValidatorUtils.getValueAsString(bean,
                field.getProperty());

        if (!GenericValidator.isBlankOrNull(value))
        {
            String refDataType = field.getVarValue("refDataType");

            if (GenericValidator.isBlankOrNull(refDataType))
            {
                throw new RuntimeException(
                    "Refdata Type value is missing for RefData validation.");
            }
        }

        return isValid;
    }
}
