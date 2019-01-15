package com.sbryan.internalservice.fault.utils;

import com.sbryan.internalservice.fault.InternalServiceFault;
import com.sbryan.internalservice.fault.ValidationFault;
import com.sbryan.internalservice.fault.data.FaultDetail;
import java.net.InetAddress;
import java.net.UnknownHostException;
import org.apache.cxf.jaxrs.ext.MessageContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FaultUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(FaultUtil.class);

    public FaultUtil() {
    }

    public static String getTrackingKey() {
        return Long.toString(System.currentTimeMillis());
    }

    public static String getNodeFromInetAddress() {
        String node = null;

        try {
            node = InetAddress.getLocalHost().getHostName();
            if (node == null) {
                node = InetAddress.getLocalHost().getHostAddress();
            }
        } catch (UnknownHostException var2) {
            LOGGER.warn("Unable to get host name", var2);
        }

        return node;
    }

    public static String getMethod(Throwable throwable) {
        if (throwable == null) {
            return null;
        } else {
            StackTraceElement ste = throwable.getStackTrace()[0];
            return ste.getClassName() + "." + ste.getMethodName() + "(" + ste.getLineNumber() + ")";
        }
    }

    public static String messageContextToContextPath(MessageContext messageContext) {
        return messageContext == null ? null : messageContext.getUriInfo().getAbsolutePath().getPath();
    }

    public static FaultDetail internalServiceFaultToFaultDetail(InternalServiceFault internalServiceFault) {
        FaultDetail faultDetail = new FaultDetail();
        faultDetail.setNode(internalServiceFault.getNode());
        faultDetail.setTimestamp(internalServiceFault.getTimestamp());
        faultDetail.setTrackingKey(internalServiceFault.getTrackingKey());
        faultDetail.setMethod(internalServiceFault.getMethod());
        faultDetail.setContextPath(internalServiceFault.getContextPath());
        faultDetail.setMessage(internalServiceFault.getMessage());
        faultDetail.setFaultDetail(internalServiceFault.getFaultDetail());
        if (internalServiceFault instanceof ValidationFault) {
            faultDetail.setValidationError(((ValidationFault)internalServiceFault).getValidationErrorList());
        }

        return faultDetail;
    }

    public static <T extends InternalServiceFault> T faultDetailToInternalServiceFault(FaultDetail faultDetail, Class<T> type) {
        InternalServiceFault fault;
        fault = new InternalServiceFault();


        fault.setNode(faultDetail.getNode());
        fault.setTimestamp(faultDetail.getTimestamp());
        fault.setTrackingKey(faultDetail.getTrackingKey());
        fault.setMethod(faultDetail.getMethod());
        fault.setContextPath(faultDetail.getContextPath());
        fault.setMessage(faultDetail.getMessage());
        fault.setFaultDetail(faultDetail.getFaultDetail());
        if (fault instanceof ValidationFault) {
            ((ValidationFault)fault).setValidationErrorList(faultDetail.getValidationError());
        }

        return fault;
    }
}
