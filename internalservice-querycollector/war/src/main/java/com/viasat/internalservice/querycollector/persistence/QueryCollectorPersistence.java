package com.viasat.internalservice.querycollector.persistence;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import com.viasat.internalservice.fault.InternalServiceFault;



public class QueryCollectorPersistence
{
	private static final Logger LOGGER = LoggerFactory.getLogger(QueryCollectorPersistence.class);


	@Value("#{environment['COLLECTOR_ID']}")
	private String collectorId;

	public List<QueryResponse> getQueryInfo(String dbQuery) throws InternalServiceFault {
		List<QueryResponse> queryResponses = new ArrayList<>();
		return queryResponses;


    }
}
