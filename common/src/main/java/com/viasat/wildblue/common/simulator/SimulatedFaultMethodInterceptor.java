package com.viasat.wildblue.common.simulator;

import com.viasat.wildblue.common.commondata.ValidationResult;
import com.viasat.wildblue.common.intercept.BaseMethodInterceptor;

import java.lang.reflect.Method;


/**
 * Abstract common superclass to support standardized simulator fault behavior
 * across the Web Services in the Private API. This Interceptor will use
 * concrete instances of a FaultSimulator class to generate System Faults
 * (WebServiceException), ValidationErrors, and ValidationWarnings based on a
 * string fault command found in any String argument for the given method being
 * called. Each service will specify the specific "service prefixes" (ie "ALL",
 * "BILLING") which will elicit this behavior (these prefixes, and the fault
 * command itself are NOT case sensitive).
 */
public abstract class SimulatedFaultMethodInterceptor
    extends BaseMethodInterceptor
{
    @Override public Object before(Method method, Object[] args, Object target)
        throws Throwable
    {
        FaultSimulator faultSim = getFaultSimulator();

        String faultCommand = faultSim.getFaultCommand(args);
        ValidationResult validationResult = null;

        if (faultCommand != null)
        {
            // is it a validation method?
            if (ValidationResult.class.isAssignableFrom(method.getReturnType()))
            {
                validationResult = faultSim.getSimulatedValidationFaults(
                        faultCommand);
            }
            else
            {
                faultSim.getSimulatedActionFaults(faultCommand);
            }
        }

        return validationResult;
    }

    protected abstract FaultSimulator getFaultSimulator();
}
