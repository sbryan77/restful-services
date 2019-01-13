package com.viasat.wildblue.common.simulator;

import com.viasat.wildblue.common.commondata.ValidationError;
import com.viasat.wildblue.common.commondata.ValidationResult;
import com.viasat.wildblue.common.exception.ExceptionDetail;
import com.viasat.wildblue.common.exception.contingency.WildBlueContingencyException;
import com.viasat.wildblue.common.exception.fault.WildBlueFaultException;

import java.util.Date;


/**
 * Abstract common Fault/ValidationError/ValidationWarning generating utility
 * for use in Web Service Simulators.System Faults (WebServiceException),
 * ValidationErrors, and ValidationWarnings will be generated based on a string
 * fault command found in any String argument for the given method being called.
 * Each service will specify the specific "service prefixes" (ie "ALL",
 * "BILLING") which will elicit this behavior (these prefixes, and the fault
 * command itself are NOT case sensitive).
 */

public abstract class FaultSimulator
{
    public static final String SIMULATE_FAULT = "Fault";
    public static final String SIMULATE_VALIDATION_ERROR = "ValidationError";
    public static final String SIMULATE_VALIDATION_WARNING =
        "ValidationWarning";

    /**
     * Examine the method arguments to discover if a
     * Fault/ValidationError/ValidationWarning response should be created.
     */
    public final String getFaultCommand(Object[] args)
    {
        String faultCommandmentArg = null;

        for (Object arg : args)
        {
            if (arg instanceof String)
            {
                String faultCommand = getFaultCommandment((String)arg,
                        getServicePrefixes());

                if (faultCommand != null)
                {
                    if (faultCommand.equals(SIMULATE_FAULT)
                        || faultCommand.equals(SIMULATE_VALIDATION_WARNING)
                        || faultCommand.equals(SIMULATE_VALIDATION_ERROR))
                    {
                        // first one wins!
                        faultCommandmentArg = (String)arg;
                        break;
                    }
                }
            }
        }

        return faultCommandmentArg;
    }

    /**
     * Create and throw the fault/error exception for 'Action' methods
     * (everything other than 'validation' methods).
     */
    public final void getSimulatedActionFaults(String faultCommandment)
        throws Exception
    {
        ValidationResult validationResult = getSimulatedValidationFaults(
                faultCommandment);

        if (validationResult != null)
        {
            // ignore warnings.
            if ((validationResult.getValidationError() != null)
                && !validationResult.getValidationError().isEmpty())
            {
                ValidationError ve = validationResult.getValidationError().get(
                        0);

                WildBlueContingencyException wbce =
                    new WildBlueContingencyException(ve.getErrorCode() + ": "
                        + ve.getMessage());

                ExceptionDetail details = new ExceptionDetail();

                details.setCode("CONT001");
                details.setDetail("Exception Detail Text");
                details.setNode("NODE");
                details.setReason("Exception Detail Reason");
                details.setRole("Simulator Role");
                details.setTimestamp(new Date());
                details.setTrackingKey("ExTrackingKey:" + new Date().getTime());

                throw getWebServiceException("Wild Blue Contingency Condition",
                    details, wbce);
            }
        }
    }

    /**
     * Create the fault/error/warning response/exception for 'Validation'
     * methods.
     */
    public final ValidationResult getSimulatedValidationFaults(
        String prefixedCommandment) throws Exception
    {
        String simulatedFaultCondition = getFaultCommandment(
                prefixedCommandment, getServicePrefixes());

        ValidationResult result = null;

        if (simulatedFaultCondition != null)
        {
            if (SIMULATE_VALIDATION_WARNING.equalsIgnoreCase(
                    simulatedFaultCondition))
            {
                result = new ValidationResult();

                ValidationError ve = new ValidationError();
                ve.setErrorCode("WARNING");
                ve.setMessage("Simulated Validation Warning");
                result.getValidationWarning().add(ve);
            }
            else if (SIMULATE_VALIDATION_ERROR.equalsIgnoreCase(
                    simulatedFaultCondition))
            {
                result = new ValidationResult();

                ValidationError ve = new ValidationError();
                ve.setErrorCode("ERROR");
                ve.setMessage("Simulated Validation Error");
                result.getValidationError().add(ve);
            }
            else if (SIMULATE_FAULT.equalsIgnoreCase(simulatedFaultCondition))
            {
                ExceptionDetail exceptionDetail = new ExceptionDetail();
                exceptionDetail.setTrackingKey("ContextTrackingKey");
                exceptionDetail.setCode("contextCode");
                exceptionDetail.setReason("contextReason");
                exceptionDetail.setNode("contextNode");
                exceptionDetail.setRole("contextRole");
                exceptionDetail.setDetail("contextDetail");

                WildBlueFaultException wbfe = new WildBlueFaultException(
                        exceptionDetail, "Simulated Wild Blue Fault Exception");

                com.viasat.wildblue.common.exception.ExceptionDetail details =
                    new com.viasat.wildblue.common.exception.ExceptionDetail();

                details.setCode("WBFAULT001");
                details.setDetail("Fault Detail Text");
                details.setNode("Node");
                details.setReason("Fault Reason");
                details.setRole("Simulator Role");
                details.setTimestamp(new Date());
                details.setTrackingKey("FaultTrackingKey:"
                    + new Date().getTime());

                throw getWebServiceException("Wild Blue System Fault", details,
                    wbfe);
            }
        }

        return result;
    }

    /**
     * Implemented by implementing classes in specific projects, returns a list
     * of "service prefixes" which will be required to be appended to the fault
     * command in order for the faulting behavior to be activated. These
     * prefixes are not case sensitive.
     */
    protected abstract String[] getServicePrefixes();

    protected abstract Exception getWebServiceException(String message,
        ExceptionDetail details, Throwable cause);

    /**
     * Compare a prefixed fault command with the prefixes for the specific
     * FaultSimulator instance to derive the actual fault command (if any) which
     * will be followed.
     */
    private String getFaultCommandment(String prefixedCommandment,
        String[] myPrefixes)
    {
        String faultCommandment = null;

        for (String prefix : myPrefixes)
        {
            if (hasPrefix(prefixedCommandment, prefix))
            {
                faultCommandment = prefixedCommandment.substring(
                        prefix.length());
                break;
            }
        }

        return faultCommandment;
    }

    /**
     * Examine the given 'prefixedCommandment' String to see if it begins with
     * the given 'prefix' String (This check is NOT case sensitive).
     */
    private boolean hasPrefix(String prefixedCommandment, String prefix)
    {
        boolean hasPrefix = false;

        if ((prefixedCommandment != null)
            && (prefixedCommandment.length() >= prefix.length())
            && prefixedCommandment.substring(0, prefix.length())
            .equalsIgnoreCase(prefix))
        {
            hasPrefix = true;
        }

        return hasPrefix;
    }
}
