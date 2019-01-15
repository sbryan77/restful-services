package com.sbryan.internalservice.fault.providers;

import com.sbryan.internalservice.fault.InternalServiceFault;
import com.sbryan.internalservice.fault.NotFoundFault;
import com.sbryan.internalservice.fault.ValidationFault;
import com.sbryan.internalservice.fault.data.FaultDetail;
import com.sbryan.internalservice.fault.utils.FaultUtil;
import javax.ws.rs.core.Response;
import org.apache.cxf.jaxrs.client.ResponseExceptionMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CustomResponseExceptionMapper implements ResponseExceptionMapper<InternalServiceFault> {
    private static final Logger LOGGER = LoggerFactory.getLogger(CustomResponseExceptionMapper.class);

    public CustomResponseExceptionMapper() {
    }

    public InternalServiceFault fromResponse(Response r) {
        LOGGER.debug("Converting Response to InternalServiceFault");
        FaultDetail fd = (FaultDetail)r.readEntity(FaultDetail.class);
        InternalServiceFault fault;
        if (r.getStatus() == 400) {
            fault = FaultUtil.faultDetailToInternalServiceFault(fd, ValidationFault.class);
        } else if (r.getStatus() == 404) {
            fault = FaultUtil.faultDetailToInternalServiceFault(fd, NotFoundFault.class);
        } else {
            fault = FaultUtil.faultDetailToInternalServiceFault(fd, InternalServiceFault.class);
        }

        return fault;
    }
}